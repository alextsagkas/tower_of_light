package characters;

import interfaces.*;
import items.equipables.EquipableItem;
import items.equipables.weapons.Damage;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract class that implements all the common logic of all entities (player and enemies) in the game.
 */
public abstract class Entity implements Drawable, Updatable, LogSubject, StatSubject {
    protected final GamePanel gamePanel;
    private DiscreteMapPosition position;
    private Direction direction;
    private boolean collision;

    private int hitPoints;
    private int maxHitPoints;

    private int swingDefence;
    private int thrustDefence;
    private int magicalDefence;

    // Main hand is the only way an entity can cause attack damage to another one.
    private Weapon mainHand;

    private int experiencePoints;

    private LogObserver logObserver;
    private StatObserver statObserver;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setPosition(DiscreteMapPosition pos) {
        this.position = pos;
    }

    public DiscreteMapPosition getPosition() {
        return position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setSwingDefence(int swingDefence) {
        this.swingDefence = swingDefence;
    }

    public int getSwingDefence() {
        return swingDefence;
    }

    public void setThrustDefence(int thrustDefence) {
        this.thrustDefence = thrustDefence;
    }

    public int getThrustDefence() {
        return thrustDefence;
    }

    public void setMagicalDefence(int magicalDefence) {
        this.magicalDefence = magicalDefence;
    }

    public int getMagicalDefence() {
        return magicalDefence;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setMainHand(EquipableItem mainHand) {
        this.mainHand = (Weapon) mainHand;
    }

    public Weapon getMainHand() {
        return mainHand;
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    public void attachStatObserver(StatObserver statObserver) {
        this.statObserver = statObserver;
    }

    public void notifyStatObserver() {
        statObserver.updateStats();
    }

    protected void moveUp() {
        setPosition(getPosition().above());
    }

    protected void moveDown() {
        setPosition(getPosition().below());
    }

    protected void moveRight() {
        setPosition(getPosition().right());
    }

    protected void moveLeft() {
        setPosition(getPosition().left());
    }

    protected abstract void setDirectionBeforeMoving();

    /**
     * Move by setting the direction through {@link characters.Entity#setDirectionBeforeMoving()} method,
     * which is implemented by subclasses. Before actually moving the {@link characters.CollisionChecker#checkPosition(Entity)}
     * method is utilized for ensuring that the new position of the entity is allowed. If it is not, the entity stays at the old
     * position.
     */
    protected void move() {
        setDirectionBeforeMoving();

        gamePanel.collisionChecker.checkPosition(this);

        if (!getCollision()) {
            switch (getDirection()) {
                case Direction.UP:
                    moveUp();
                    break;
                case Direction.DOWN:
                    moveDown();
                    break;
                case Direction.LEFT:
                    moveLeft();
                    break;
                case Direction.RIGHT:
                    moveRight();
                    break;
            }
        }

    }

    /**
     * Receive damage and soothe it with defense.
     *
     * @param damageAmount    the damage amount dealt by the attacker.
     * @param defenseSupplier the getter for the corresponding defense type of the defender.
     */
    protected void receiveDamage(
            int damageAmount,
            @NotNull Supplier<Integer> defenseSupplier
    ) {
        int defenseAmount = defenseSupplier.get();
        if (defenseAmount == Integer.MAX_VALUE) {
            notifyLogObserver("Immune to magical damage.");
            return;
        }

        int damageDealt = damageAmount - defenseAmount;
        if (damageDealt <= 0) {return;}

        if (getHitPoints() <= damageDealt) {
            setHitPoints(0);
            return;
        }

        setHitPoints(getHitPoints() - damageDealt);
    }

    /**
     * Receive damage based on damage type.
     *
     * @param damageType   the damage type.
     * @param damageAmount the amount of damage dealt.
     */
    protected void receiveDamageType(Damage.@NotNull DamageType damageType, int damageAmount) {
        switch (damageType) {
            case Damage.DamageType.SWING:
                receiveDamage(damageAmount, this::getSwingDefence);
                break;
            case Damage.DamageType.THRUST:
                receiveDamage(damageAmount, this::getThrustDefence);
                break;
            case Damage.DamageType.MAGICAL:
                receiveDamage(damageAmount, this::getMagicalDefence);
                break;
            default:
                notifyLogObserver(String.format("There is not damage with type: %s", damageType));
        }
    }

    /**
     * Attack an entity located in unary distance (as specified by the {@link map.DiscreteMapPosition#distanceTo(DiscreteMapPosition)} method)
     * with damage from the equipped weapon.
     *
     * @param damageList the damage list returned by the weapon of attack.
     */
    public void receiveAttack(@NotNull List<Damage> damageList) {
        for (var damage : damageList) {
            receiveDamageType(damage.getDamageType(), damage.getDamageAmount());
            notifyStatObserver();
        }

        if (getHitPoints() == 0) {
            die();
        }
    }

    protected abstract void die();

    /**
     * Attack all entities listed in the first argument with the damage specified by the second one.
     *
     * @param targetList attack the entities on this list.
     * @param damageList deal the same damage to each one.
     */
    protected void genericAttack(
            @NotNull List<Entity> targetList,
            List<Damage> damageList
    ) {
        if (targetList.isEmpty()) {return;}

        notifyLogObserver(String.format("Attacking %s enemies.", targetList.size()));
        for (var target : targetList) {
            target.receiveAttack(damageList);
        }
    }

    protected abstract void attack();

    public abstract void draw(Graphics2D g2d);
}
