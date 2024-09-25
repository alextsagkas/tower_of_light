package characters;

import interfaces.*;
import items.equipables.EquipableItem;
import items.equipables.weapons.Damage;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import map.DiscreteMapPosition;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

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

    protected void receiveDamage(
            int damageAmount,
            Supplier<Integer> defenseSupplier
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

    protected void receiveDamageType(Damage.DamageType damageType, int damageAmount) {
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

    public void receiveAttack(List<Damage> damageList) {
        for (var damage : damageList) {
            receiveDamageType(damage.getDamageType(), damage.getDamageAmount());
            notifyStatObserver();
        }

        if (getHitPoints() == 0) {
            die();
        }
    }

    protected abstract void die();

    protected void genericAttack(
            List<Entity> targetList,
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
