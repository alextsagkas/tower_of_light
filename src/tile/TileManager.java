package tile;

import characters.Player;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[][] mapTiles;
    List<Tile> spellTiles;

    final int spellTileSeparation = 20;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mapTiles = new Tile[DiscreteMap.maxScreenRow][DiscreteMap.maxScreenCol];
        loadMap();
        this.spellTiles = new ArrayList<>();
    }

    public Tile getTile(DiscreteMapPosition position) {
        return mapTiles[position.getY()][position.getX()];
    }

    public void setTile(DiscreteMapPosition position, Tile tile) {
        mapTiles[position.getY()][position.getX()] = tile;
    }

    private void loadMap() throws RuntimeException {
        String levelPath = "/map/levels/level" + gamePanel.getGameLevel() + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            for (int row = 0; row < DiscreteMap.maxScreenRow; row++) {
                String[] tileTypes = reader.readLine().split(" ");

                for (int col = 0; col < DiscreteMap.maxScreenCol; col++) {
                    TileType tileType = TileType.fromInteger(Integer.parseInt(tileTypes[col]));
                    mapTiles[row][col] = Tile.createTile(
                            tileType,
                            DiscreteMap.getMapPosition(col, row));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void reset() {
        spellTiles.clear();
        loadMap();
    }

    public void update() {
        Player player = gamePanel.player;
        DiscreteMapPosition playerPosition = player.getPosition();

        // Visibility
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                int distanceToTile = tile.getPosition().distanceTo(playerPosition);
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

        // Spells
        final int maxSpellTiles = 3;

        if (gamePanel.keyHandler.castSpell) {
            Tile spellTile = getTile(playerPosition);
            boolean separateTiles = spellTiles.
                    stream().
                    allMatch(
                            tile -> tile.
                                    getPosition().
                                    distanceTo(playerPosition) > spellTileSeparation
                    );

            if (separateTiles && spellTiles.size() < maxSpellTiles) {
                setTile(playerPosition, new SpellTileDecorator(spellTile));
                spellTiles.add(spellTile);
            }

            if (spellTiles.size() == maxSpellTiles) {
                Tile exitTile = getTile(DiscreteMap.northEast);
                setTile(exitTile.getPosition(), new ExitTileDecorator(exitTile));

                for (Tile[] rowOfTiles : this.mapTiles) {
                    for (Tile tile : rowOfTiles) {
                        tile.setDiscovered(true);
                        setTile(tile.getPosition(), new LightTileDecorator(tile));
                    }
                }
            }
        }

        // Level update
        if (playerPosition.equals(DiscreteMap.northEast) && spellTiles.size() == maxSpellTiles) {
            gamePanel.setGameLevel(gamePanel.getGameLevel() + 1);
            gamePanel.player.setPosition(DiscreteMap.southWest);
            reset();
        }
    }

    public void draw(Graphics2D g2d) {
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.draw(g2d);
            }
        }
    }
}
