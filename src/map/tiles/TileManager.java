package map.tiles;

import characters.Player;
import interfaces.LogObserver;
import interfaces.LogSubject;
import interfaces.Resettable;
import interfaces.Updatable;
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

public final class TileManager implements Updatable, Resettable, LogSubject {
    GamePanel gamePanel;
    Tile[][] mapTiles;
    List<Tile> beaconTiles;
    public final int maxBeaconTiles;
    public final int beaconTileSeparation;
    private LogObserver logObserver;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mapTiles = new Tile[DiscreteMap.maxScreenRow][DiscreteMap.maxScreenCol];
        this.beaconTiles = new ArrayList<>();
        this.maxBeaconTiles = 3;
        this.beaconTileSeparation = 10;

        loadMap();
    }

    public Tile getTile(@NotNull DiscreteMapPosition position) {
        return mapTiles[position.getY()][position.getX()];
    }

    public void setTile(@NotNull DiscreteMapPosition position, Tile tile) {
        mapTiles[position.getY()][position.getX()] = tile;
    }

    public int getBeaconTilesSize() {
        return beaconTiles.size();
    }

    public void attach(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyObserver(String log) {
        logObserver.update(log);
    }

    private void loadMap() throws RuntimeException {
        String levelPath = "/map/levels/level" + gamePanel.getGameLevel() + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            for (int row = 0; row < DiscreteMap.maxScreenRow; row++) {
                String[] tileTypes = reader.readLine().split(" ");

                for (int col = 0; col < DiscreteMap.maxScreenCol; col++) {
                    TileType tileType = TileType.fromInteger(Integer.parseInt(tileTypes[col]));
                    mapTiles[row][col] = Tile.createTile(tileType, DiscreteMapPosition.of(col, row));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int beaconTileMinSeparation(DiscreteMapPosition position) {
        OptionalInt minSeparation = beaconTiles.stream()
                                               .mapToInt(tile -> position.distanceTo(tile.getPosition()))
                                               .min();
        return minSeparation.orElse(Integer.MAX_VALUE);
    }

    public void reset() {
        beaconTiles.clear();
        loadMap();
        updateVisibility();
    }

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

    public void drawBeacon(DiscreteMapPosition position) {
        Tile beaconTile = new BeaconTileDecorator(getTile(position));
        setTile(beaconTile.getPosition(), beaconTile);
        beaconTiles.add(beaconTile);

        notifyObserver(String.format("Create %d/%d beacons of light.", getBeaconTilesSize(), maxBeaconTiles));

        if (beaconTiles.size() == maxBeaconTiles) {
            convertToLight();
        }
    }

    private void convertToLight() {
        Tile exitTile = getTile(DiscreteMap.northEast);
        setTile(exitTile.getPosition(), new ExitTileDecorator(exitTile));

        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.setDiscovered(true);
                tile.toLight();
            }
        }

        notifyObserver(String.format("Level %d is converted from chaos to light.", gamePanel.getGameLevel()));
        if (gamePanel.getGameLevel() < gamePanel.maxGameLevel) {
            notifyObserver("The door to the next level has opened!");
        } else {
            notifyObserver("The door to the light has opened!");
        }
    }

    public void update() {
        updateVisibility();
    }

    public void draw(Graphics2D g2d) {
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.draw(g2d);
            }
        }
    }
}
