package map.tiles;

import characters.player.Player;
import interfaces.*;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.decorators.BeaconTileDecorator;
import map.tiles.decorators.ExitTileDecorator;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

/**
 * Aggregate the Tile management logic in a single class.
 */
public final class TileManager implements Drawable, Updatable, Resettable, LogSubject {
    public final int maxBeaconTiles;
    public final int beaconTileSeparation;

    private final GamePanel gamePanel;
    private final Tile[][] mapTiles;
    private final List<Tile> beaconTiles;

    private LogObserver logObserver;

    public TileManager(GamePanel gamePanel) {
        this.maxBeaconTiles = 3;
        this.beaconTileSeparation = 10;

        this.gamePanel = gamePanel;
        this.mapTiles = new Tile[DiscreteMap.maxScreenRow][DiscreteMap.maxScreenCol];
        this.beaconTiles = new ArrayList<>();

        loadMap();
    }

    /**
     * Get the tile from the position.
     *
     * @param position the position to get the tile from.
     * @return the tile residing on the position specified.
     */
    public Tile getTile(@NotNull DiscreteMapPosition position) {
        return mapTiles[position.getY()][position.getX()];
    }

    /**
     * Set tile on position give nas argument as the second parameter of the method.
     *
     * @param position the position on which the old tile resided.
     * @param tile     the tile that replaces the old one.
     */
    public void setTile(@NotNull DiscreteMapPosition position, Tile tile) {
        mapTiles[position.getY()][position.getX()] = tile;
    }

    public int getBeaconTilesSize() {
        return beaconTiles.size();
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    /**
     * Load the map from the {@code .txt} files nested on the {@code levels/} subdirectory.
     * The file picked is related to the level of the game.
     *
     * @throws RuntimeException rethrow the exception that might be thrown with try-with-resources.
     */
    private void loadMap() throws RuntimeException {
        String levelPath = "/map/levels/level" + gamePanel.getGameLevel() + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            for (int row = 0; row < DiscreteMap.maxScreenRow; row++) {
                String[] tileTypes = reader.readLine().split(" ");

                for (int col = 0; col < DiscreteMap.maxScreenCol; col++) {
                    Tile.TileType tileType = Tile.TileType.fromInteger(Integer.parseInt(tileTypes[col]));
                    mapTiles[row][col] = Tile.createTile(tileType, DiscreteMapPosition.of(col, row));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculate the minimum distance between one of the beacon tiles and the position given
     * as argument.
     *
     * @param position the position of reference to calculate distances.
     * @return the minimum distance between the position and the beacon tiles.
     */
    public int beaconTileMinSeparation(DiscreteMapPosition position) {
        OptionalInt minSeparation = beaconTiles.stream()
                                               .mapToInt(tile -> position.distanceTo(tile.getPosition()))
                                               .min();
        return minSeparation.orElse(Integer.MAX_VALUE);
    }

    /**
     * Update visibility of the map respective to player's position on it.
     */
    private void updateVisibility() {
        Player player = gamePanel.player;
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                int distanceToTile = tile.getPosition().distanceTo(player.getPosition());
                if (distanceToTile <= player.visibilityRadius) {
                    tile.setVisible(true);
                    if (!(tile.isDiscovered())) {
                        tile.setDiscovered(true);
                    }
                } else {
                    tile.setVisible(false);
                }
            }
        }
    }

    /**
     * Color the beacon specific color and note its existence. If the created beacons
     * are of enough quantity, convert map to light. This method is intended to be used
     * by the player when we can create a beacon.
     *
     * @param position the position on which to create the beacon.
     */
    public void drawBeacon(DiscreteMapPosition position) {
        Tile beaconTile = new BeaconTileDecorator(getTile(position));
        setTile(beaconTile.getPosition(), beaconTile);
        beaconTiles.add(beaconTile);

        notifyLogObserver(String.format("Create %d/%d beacons of light.", getBeaconTilesSize(), maxBeaconTiles));
        gamePanel.enemyManager.updateOnBeaconCreation();

        if (beaconTiles.size() == maxBeaconTiles) {
            convertToLight();
        }
    }

    /**
     * Convert map to light when {@code maxBeaconTiles} beacons have been created by
     * the player. Open the portal to the next level, if it exists. Also, reveal all items
     * on the map.
     */
    private void convertToLight() {
        Tile exitTile = getTile(DiscreteMap.northEast);
        setTile(exitTile.getPosition(), new ExitTileDecorator(exitTile));

        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.setDiscovered(true);
                tile.toLight();
            }
        }

        notifyLogObserver(String.format("Level %d is converted from chaos to light.", gamePanel.getGameLevel()));
        if (gamePanel.getGameLevel() < gamePanel.maxGameLevel) {
            notifyLogObserver("The door to the next level has opened!");
        } else {
            notifyLogObserver("The door to the light has opened!");
        }

        gamePanel.itemManager.convertToLight();
        gamePanel.enemyManager.convertToLight();
    }

    /**
     * Update the TileManager state in every action of the player.
     */
    public void update() {
        updateVisibility();
    }

    /**
     * Reset the TileManager state to start fresh on a new level.
     */
    public void reset() {
        beaconTiles.clear();
        loadMap();
        updateVisibility();
    }

    /**
     * Draw all the map tiles on the game panel.
     *
     * @param g2d the graphic to draw the tiles on.
     */
    public void draw(Graphics2D g2d) {
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.draw(g2d);
            }
        }
    }
}
