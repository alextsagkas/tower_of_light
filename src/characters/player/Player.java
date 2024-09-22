package characters.player;

import characters.Direction;
import interfaces.*;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.TileManager;
import ui.Colors;
import ui.Render;

import java.awt.*;

public final class Player implements Drawable, Updatable, Resettable, LogSubject, StatSubject {
    private final GamePanel gamePanel;
    private DiscreteMapPosition playerPos;
    private Direction direction;
    private boolean collision;
    public final int visibilityRadius;
    private int actionsNumber;

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

    private LogObserver logObserver;
    private StatObserver statObserver;

    public Player(GamePanel gamePanel, Race race, Warrior warrior) {
        this.gamePanel = gamePanel;
        this.playerPos = DiscreteMap.southWest;
        this.direction = Direction.NONE;
        this.collision = false;
        this.visibilityRadius = 6;
        this.actionsNumber = 0;

        this.race = race;
        this.warrior = warrior;

        this.name = this.race.getRace() + " " + this.warrior.getWarrior();

        this.level = 1;

        this.race.initializeStats(this);
        this.warrior.updateStats(this);

        this.hitPoints = getMaxHitPoints();
        this.manaPoints = getMaxManaPoints();
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
                double restReplenishFactor = 0.05;
                if (getHitPoints() < getMaxHitPoints()) {
                    int hpReplenish = statReplenish(getHitPoints(), maxHP, restReplenishFactor);
                    setHitPoints(hpReplenish);
                }
                if (getManaPoints() < getMaxManaPoints()) {
                    int mpReplenish = statReplenish(getManaPoints(), maxMP, restReplenishFactor);
                    setManaPoints(mpReplenish);
                }
                notifyStatObserver();
                notifyLogObserver("Resting...");
            } else {
                notifyLogObserver("Rest makes no effect, since HP and MP are full.");
            }
        }
    }

    public void update() {
        move();
        executeBeacon();
        updateLevel();
        executeRest();
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
