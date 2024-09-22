package characters.player;

import characters.Direction;
import interfaces.*;
import items.effects.ItemEffect;
import items.equipables.EquipableItem;
import items.equipables.Weapon;
import items.usables.UsableItem;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.TileManager;
import org.jetbrains.annotations.NotNull;
import ui.Colors;
import ui.Render;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Player implements Drawable, Updatable, Resettable, LogSubject, StatSubject {
    private final GamePanel gamePanel;
    private DiscreteMapPosition playerPos;
    private Direction direction;
    private boolean collision;
    public final int visibilityRadius;
    private int actionsNumber;

    public final ItemInventory itemInventory;

    private final Warrior warrior;
    private final Race race;

    private final String name;

    private int level;

    private int hitPoints;
    private int maxHitPoints;

    private int manaPoints;
    private int maxManaPoints;

    private int strength;
    private int intellect;

    private int swingDefence;
    private int thrustDefence;
    private int magicalDefence;

    private int experiencePoints;

    private Weapon mainHand;
    private Weapon offHand;
    private EquipableItem trinket;

    private LogObserver logObserver;
    private StatObserver statObserver;

    public Player(GamePanel gamePanel, Race race, Warrior warrior) {
        this.gamePanel = gamePanel;
        this.visibilityRadius = 6;
        this.itemInventory = new ItemInventory();
        this.race = race;
        this.warrior = warrior;

        this.name = this.race.getRace() + " " + this.warrior.getWarrior();

        restart();
    }

    public enum SlotType {
        MAIN_HAND,
        OFF_HAND,
        TRINKET;
    }

    public void setPosition(DiscreteMapPosition pos) {
        this.playerPos = pos;
    }

    public DiscreteMapPosition getPosition() {
        return playerPos;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setActionsNumber(int actionsNumber) {
        this.actionsNumber = actionsNumber;
    }

    public int getActionsNumber() {
        return actionsNumber;
    }

    public String getName() {
        return name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
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

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setMaxManaPoints(int maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public int getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public int getIntellect() {
        return intellect;
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

    public void setOffHand(EquipableItem offHand) {
        this.offHand = (Weapon) offHand;
    }

    public Weapon getOffHand() {
        return offHand;
    }

    public EquipableItem getTrinket() {
        return trinket;
    }

    public void setTrinket(EquipableItem trinket) {
        this.trinket = trinket;
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    public void attachStatObserver(StatObserver statObserver) {
        this.statObserver = statObserver;
        this.itemInventory.attachStatObserver(statObserver);
    }

    public void notifyStatObserver() {
        statObserver.updateStats();
    }

    private void moveUp() {
        setPosition(getPosition().above());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveDown() {
        setPosition(getPosition().below());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveRight() {
        setPosition(getPosition().right());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveLeft() {
        setPosition(getPosition().left());
        setActionsNumber(getActionsNumber() + 1);
    }

    public void reset() {
        setPosition(DiscreteMap.southWest);
        setDirection(Direction.NONE);
        setCollision(false);
        setActionsNumber(0);
    }

    @Override
    public void restart() {
        reset();
        setMaxHitPoints(0);
        setMaxManaPoints(0);
        setLevel(1);
        setExperiencePoints(0);
        this.race.initializeStats(this);
        this.warrior.updateStats(this);
        setHitPoints(getMaxHitPoints());
        setManaPoints(getMaxManaPoints());
        this.itemInventory.restart();
        setMainHand(null);
        setOffHand(null);
        setTrinket(null);
    }

    private void move() {
        if (gamePanel.keyHandler.isUpPressed()) {
            setDirection(Direction.UP);
        } else if (gamePanel.keyHandler.isDownPressed()) {
            setDirection(Direction.DOWN);
        } else if (gamePanel.keyHandler.isLeftPressed()) {
            setDirection(Direction.LEFT);
        } else if (gamePanel.keyHandler.isRightPressed()) {
            setDirection(Direction.RIGHT);
        } else {
            setDirection(Direction.NONE);
        }

        gamePanel.collisionChecker.checkTile(this);

        if (!collision) {
            switch (direction) {
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

    private void executeBeacon() {
        if (gamePanel.keyHandler.isCastBeacon()) {
            TileManager tileManager = gamePanel.tileManager;

            int actionCooldownNumber = 15;
            int minSeparation = tileManager.beaconTileMinSeparation(playerPos);
            int beaconTilesSize = gamePanel.tileManager.getBeaconTilesSize();
            int maxBeaconTiles = gamePanel.tileManager.maxBeaconTiles;

            if (
                    minSeparation >= tileManager.beaconTileSeparation &&
                    getActionsNumber() >= actionCooldownNumber &&
                    beaconTilesSize < maxBeaconTiles
            ) {
                tileManager.drawBeacon(getPosition());
                setActionsNumber(0);
            } else if (beaconTilesSize < maxBeaconTiles) {
                notifyLogObserver("Beacon could not be created, because:");
                if (minSeparation < tileManager.beaconTileSeparation) {
                    notifyLogObserver(String.format(
                            "- Shortest distance = %d < %d.",
                            minSeparation,
                            tileManager.beaconTileSeparation
                    ));
                }
                if (getActionsNumber() < actionCooldownNumber) {
                    notifyLogObserver(String.format(
                            "- Action cooldown = %d < %d.",
                            getActionsNumber(),
                            actionCooldownNumber
                    ));
                }
            }
        }
    }

    private void updateLevel() {
        int level;
        int xp = getExperiencePoints();

        if (xp < 300) {
            level = 1;
        } else if (xp < 900) {
            level = 2;
        } else if (xp < 2700) {
            level = 3;
        } else if (xp < 6500) {
            level = 4;
        } else if (xp < 14000) {
            level = 5;
        } else {
            level = 6;
        }

        if (level != getLevel()) {
            setLevel(level);
            notifyLogObserver(String.format("Player's level is %d.", getLevel()));
            notifyStatObserver();
            warrior.updateStats(this);
            notifyLogObserver("Player's stats have been updated.");
        }
    }

    private int statReplenish(int currentStat, int maxStat, double replenishFactor) {
        int statReplenish = (int) Math.round(maxStat * replenishFactor);
        if (statReplenish == 0) {
            statReplenish = 1;
        }

        int replenishedStat = currentStat + statReplenish;
        if (replenishedStat > maxStat) {
            replenishedStat = maxStat;
        }

        return replenishedStat;
    }

    private void executeRest() {
        if (gamePanel.keyHandler.isExecuteRest()) {
            int maxHP = getMaxHitPoints();
            int maxMP = getMaxManaPoints();
            if (getHitPoints() < maxHP || getManaPoints() < maxMP) {
                notifyLogObserver("Resting...");
                double restReplenishFactor = 0.05;
                if (getHitPoints() < getMaxHitPoints()) {
                    int hpReplenish = statReplenish(getHitPoints(), maxHP, restReplenishFactor);
                    notifyLogObserver(String.format("- Gained %s health points.", hpReplenish - getHitPoints()));
                    setHitPoints(hpReplenish);
                }
                if (getManaPoints() < getMaxManaPoints()) {
                    int mpReplenish = statReplenish(getManaPoints(), maxMP, restReplenishFactor);
                    notifyLogObserver(String.format("- Gained %s mana points.", mpReplenish - getManaPoints()));
                    setManaPoints(mpReplenish);
                }
                notifyStatObserver();
            } else {
                notifyLogObserver("Rest makes no effect, since HP and MP are full.");
            }
        }
    }

    private void itemEffectOnPlayer(ItemEffect itemEffect) {
        int updatedStat;
        switch (itemEffect.getItemEffectType()) {
            case HP_REPLENISH:
                updatedStat = getHitPoints() + itemEffect.getStatEnhancement();
                setHitPoints(Math.min(updatedStat, getMaxHitPoints()));
                break;
            case MP_REPLENISH:
                updatedStat = getManaPoints() + itemEffect.getStatEnhancement();
                setManaPoints(Math.min(updatedStat, getMaxManaPoints()));
                break;
            case HP_BOOST:
                setMaxHitPoints(getMaxHitPoints() + itemEffect.getStatEnhancement());
                break;
            case MP_BOOST:
                setMaxManaPoints(getMaxManaPoints() + itemEffect.getStatEnhancement());
                break;
            case STR_BOOST:
                setStrength(getStrength() + itemEffect.getStatEnhancement());
                break;
            case INT_BOOST:
                setIntellect(getIntellect() + itemEffect.getStatEnhancement());
                break;
            default:
                notifyLogObserver("Item effect is non-existent.");
        }
    }

    private void consumeUsableItem(@NotNull UsableItem usableItem) {
        notifyLogObserver(String.format("Use %s.", usableItem.getItemName()));
        for (ItemEffect itemEffect : usableItem.use()) {
            itemEffectOnPlayer(itemEffect);
        }
        notifyStatObserver();
    }

    private void consumeHPReplenish() {
        if (!gamePanel.keyHandler.isUseHPReplenish()) {return;}

        UsableItem usableItem = itemInventory.getItemType(ItemEffect.ItemEffectType.HP_REPLENISH);

        if (usableItem == null) {
            notifyLogObserver(String.format(
                    "There is no item with %s to use.",
                    ItemEffect.ItemEffectType.HP_REPLENISH.getHumanReadableName()
            ));
            return;
        }

        if (getHitPoints() == getMaxHitPoints()) {
            notifyLogObserver(String.format(
                    "You cannot use %s, hp are full.",
                    ItemEffect.ItemEffectType.HP_REPLENISH.getHumanReadableName()
            ));
            return;
        }

        consumeUsableItem(usableItem);
    }

    private void consumeMPReplenish() {
        if (!gamePanel.keyHandler.isUseMPReplenish()) {return;}

        if (getMaxManaPoints() == 0) {
            notifyLogObserver(String.format(
                    "You cannot use %s, since mp max = 0.",
                    ItemEffect.ItemEffectType.MP_REPLENISH.getHumanReadableName()
            ));
            return;
        }

        UsableItem usableItem = itemInventory.getItemType(ItemEffect.ItemEffectType.MP_REPLENISH);

        if (usableItem == null) {
            notifyLogObserver(String.format(
                    "There is no item with %s to use.",
                    ItemEffect.ItemEffectType.MP_REPLENISH.getHumanReadableName()
            ));
            return;
        }

        if (getManaPoints() == getMaxManaPoints()) {
            notifyLogObserver(String.format(
                    "You cannot use %s, mp are full.",
                    ItemEffect.ItemEffectType.MP_REPLENISH.getHumanReadableName()
            ));
            return;
        }

        consumeUsableItem(usableItem);
    }

    private void consumeUsableItems() {
        consumeHPReplenish();
        consumeMPReplenish();
        itemInventory.update();
    }

    private void applyEquipableItemEffects(EquipableItem equipableItem) {
        for (ItemEffect itemEffect : equipableItem.getItemEffects()) {
            itemEffectOnPlayer(itemEffect);
        }

        // Ensure that stats do not surpass the maximum capacity
        // Note: This results in a miss in capacity when you drop and
        //       equip again an item that boosts one of the following
        //       stats, when before you drop it you had capacity above the
        //       maximum you get yen you equip the second one.

        if (getHitPoints() > getMaxHitPoints()) {
            setHitPoints(getMaxHitPoints());
        }

        if (getManaPoints() > getMaxManaPoints()) {
            setManaPoints(getMaxManaPoints());
        }
    }

    private void undoEquipableItemEffects(EquipableItem equipableItem) {
        for (ItemEffect itemEffect : equipableItem.getUndoItemEffects()) {
            itemEffectOnPlayer(itemEffect);
        }
    }

    private void changeEquipableItem(
            EquipableItem equipable,
            Consumer<EquipableItem> equipableItemSetter,
            Function<DiscreteMapPosition, EquipableItem> getEquipableItem
    ) {
        EquipableItem equipableItem = getEquipableItem.apply(getPosition());
        if (equipableItem == null) {
            notifyLogObserver("No item to equip.");
            return;
        }

        if (equipable != null) {
            // TODO: Think about what makes more sense from the below points:
            // 1. Make item's that are on the player change their position as the player
            //    changes his (e.g. with a pattern like observer/subject), or
            // 2. Change the position of items to the correct one (player's position) only
            //    when it is needed to be changed (like in the following code)?
            equipable.setPosition(getPosition());
            undoEquipableItemEffects(equipable);
            gamePanel.itemManager.add(equipable);
            notifyLogObserver(String.format("Dropped %s.", equipable.getItemName()));
        }
        equipableItemSetter.accept(equipableItem);
        applyEquipableItemEffects(equipableItem);

        notifyLogObserver(String.format("Equipped %s.", equipableItem.getItemName()));
        notifyStatObserver();
    }

    private void changeWeapon() {
        if (!gamePanel.keyHandler.isChangeWeapon()) {return;}
        changeEquipableItem(mainHand, this::setMainHand, gamePanel.itemManager::getWeapon);
    }

    private void changeSecondaryWeapon() {
        if (!gamePanel.keyHandler.isChangeSecondaryWeapon()) {return;}
        changeEquipableItem(offHand, this::setOffHand, gamePanel.itemManager::getWeapon);
    }

    private void changeTrinket() {
        if (!gamePanel.keyHandler.isChangeTrinket()) {return;}
        changeEquipableItem(trinket, this::setTrinket, gamePanel.itemManager::getEquipableItem);
    }

    public void update() {
        move();
        executeBeacon();
        updateLevel();
        executeRest();
        consumeUsableItems();
    }

    public void freeActions() {
        changeWeapon();
        changeSecondaryWeapon();
        changeTrinket();
    }

    public void draw(Graphics2D g2d) {
        Render.drawRectangle(g2d, getPosition(), Colors.playerColor);
    }

    @Override
    public String toString() {
        return "Player{" +
               "collision=" + collision +
               ", playerPos=" + playerPos +
               ", direction=" + direction +
               '}';
    }
}
