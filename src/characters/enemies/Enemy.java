package characters.enemies;

import characters.Direction;
import characters.Entity;
import items.ItemsGenerator;
import items.equipables.weapons.Weapon;
import items.usables.UsableItem;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.Tile;
import ui.Colors;
import ui.Render;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class Enemy extends Entity {
    private final String name;
    private boolean discovered;
    private boolean visible;

    private final ArrayList<Integer> appearOnLevel;

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        this.discovered = false;
        this.visible = false;
        this.name = initializeName();

        setMaxHitPoints(initializeMaxHitPoints());
        setHitPoints(getMaxHitPoints());

        setMainHand(initializeWeapon());

        setSwingDefence(initializeSwingDefense());
        setThrustDefence(initializeThrustDefense());
        setMagicalDefence(initializeMagicalDefense());

        setExperiencePoints(initializeExperiencePoints());

        this.appearOnLevel = initializeAppearOnLevel();
    }

    protected abstract String initializeName();

    protected abstract Integer initializeMaxHitPoints();

    protected abstract Weapon initializeWeapon();

    protected abstract Integer initializeSwingDefense();

    protected abstract Integer initializeThrustDefense();

    protected abstract Integer initializeMagicalDefense();

    protected abstract Integer initializeExperiencePoints();

    abstract ArrayList<Integer> initializeAppearOnLevel();

    abstract protected Enemy deepCopy();

    public String getName() {
        return name;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ArrayList<Integer> getAppearOnLevel() {
        return appearOnLevel;
    }

    protected void die() {
        // Remove enemy from the game
        gamePanel.enemyManager.buryEnemy(this);

        // Throw usable item on the game map
        UsableItem randomUsableItem = ItemsGenerator.randomUsableItem(getPosition());
        gamePanel.itemManager.add(randomUsableItem);

        // Throw weapon on the game map
        Weapon enemyWeapon = getMainHand();
        getMainHand().setPosition(getPosition());
        gamePanel.itemManager.add(enemyWeapon);

        // Give player experience points for the kill
        gamePanel.player.setExperiencePoints(gamePanel.player.getExperiencePoints() + getExperiencePoints());
        gamePanel.player.notifyStatObserver();
    }

    public void setRandomPosition() {
        Random rand = new Random();
        DiscreteMapPosition randomPosition;

        do {
            int randomX = rand.nextInt(DiscreteMap.maxScreenCol);
            int randomY = rand.nextInt(DiscreteMap.maxScreenRow);
            randomPosition = DiscreteMapPosition.of(randomX, randomY);
        } while (
                !gamePanel.collisionChecker.checkTileAllowed(randomPosition) ||
                (randomPosition.distanceTo(gamePanel.player.getPosition()) <= gamePanel.player.visibilityRadius)
        );

        setPosition(randomPosition);
    }

    private void updateHitPointsOnBeacon(double updateFactor) {
        // Update max Hit Points
        int updatedMaxStat = (int) Math.round(updateFactor * getMaxHitPoints());
        setMaxHitPoints(updatedMaxStat);

        // Update Hit Points
        int updateStat = (int) Math.round(updateFactor * getHitPoints());
        if (updateStat >= updatedMaxStat) {
            setHitPoints(getMaxHitPoints());
        } else {
            setHitPoints(updateStat);
        }
    }

    private void updateDamageOnBeacon(double updateFactor) {
        int updatedStat;
        for (var damage : getMainHand().getDamageList()) {
            updatedStat = (int) Math.round(updateFactor * damage.getDamageAmount());
            damage.enhance(updatedStat);
        }
    }

    private void updateExperiencePointsOnBeacon(double updateFactor) {
        int updatedStat = (int) Math.round(updateFactor * getExperiencePoints());
        setExperiencePoints(updatedStat);
    }

    public void makeStrongerOnBeaconCreation() {
        final double updateFactor = 1.25;
        updateHitPointsOnBeacon(updateFactor);
        updateDamageOnBeacon(updateFactor);
        updateExperiencePointsOnBeacon(updateFactor);
    }

    @Override
    protected void setDirectionBeforeMoving() {
        Direction closestDirection = getPosition().closestDirectionTo(gamePanel.player.getPosition());
        setDirection(closestDirection);
    }

    private void updateVisibility() {
        int distanceToPlayer = getPosition().distanceTo(gamePanel.player.getPosition());
        if (distanceToPlayer <= gamePanel.player.visibilityRadius) {
            setVisible(true);
            if (!(isDiscovered())) {
                setDiscovered(true);
                notifyStatObserver();
            }
        } else {
            setVisible(false);
        }
    }

    protected void attack() {
        int attackProximity = 1;
        if (gamePanel.player.getPosition().distanceTo(this.getPosition()) != attackProximity) {return;}

        genericAttack(
                Collections.singletonList(gamePanel.player),
                getMainHand().getDamageList()
        );
        gamePanel.player.notifyStatObserver();
        notifyLogObserver(String.format("Player attacked by: %s", getName()));
    }

    public void update() {
        move();
        updateVisibility();
        attack();
    }

    private void drawInvisible(Graphics2D g2d) {
        // Level is converted to light
        if (gamePanel.tileManager.getBeaconTilesSize() == gamePanel.tileManager.maxBeaconTiles) {
            Render.drawRectangle(g2d, getPosition(), Colors.enemyInvisibleColor);
            return;
        }

        // Level is still in chaos
        Tile tileEnemy = gamePanel.tileManager.getTile(getPosition());
        tileEnemy.draw(g2d);
    }

    private void drawVisible(Graphics2D g2d) {
        Render.drawRectangle(g2d, getPosition(), Colors.enemyVisibleColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isVisible()) {
            drawVisible(g2d);
        } else {
            drawInvisible(g2d);
        }
    }
}
