package map.tiles;

import characters.Player;
import interfaces.Resettable;
import interfaces.Updatable;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.decorators.ExitTileDecorator;
import map.tiles.decorators.SpellTileDecorator;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TileManager implements Updatable, Resettable {
    GamePanel gamePanel;
    Tile[][] mapTiles;
    List<Tile> spellTiles;
    public final int maxSpellTiles;
    final int spellTileSeparation;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mapTiles = new Tile[DiscreteMap.maxScreenRow][DiscreteMap.maxScreenCol];
        this.spellTiles = new ArrayList<>();
        this.maxSpellTiles = 3;
        this.spellTileSeparation = 20;

        loadMap();
    }

    public Tile getTile(@NotNull DiscreteMapPosition position) {
        return mapTiles[position.getY()][position.getX()];
    }

    public void setTile(@NotNull DiscreteMapPosition position, Tile tile) {
        mapTiles[position.getY()][position.getX()] = tile;
    }

    public int getSpellTilesSize() {
        return spellTiles.size();
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

    public void reset() {
        spellTiles.clear();
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

    private void executeSpell() {
        DiscreteMapPosition playerPosition = gamePanel.player.getPosition();
        boolean separateTiles = spellTiles.stream().allMatch(tile -> tile.getPosition().distanceTo(playerPosition) > spellTileSeparation);

        if (separateTiles && spellTiles.size() < maxSpellTiles) {
            Tile spellTile = new SpellTileDecorator(getTile(playerPosition));
            setTile(spellTile.getPosition(), spellTile);
            spellTiles.add(spellTile);
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
    }

    private void updateSpells() {
        if (gamePanel.keyHandler.isCastSpell()) {
            executeSpell();
            if (spellTiles.size() == maxSpellTiles) {
                convertToLight();
            }
        }
    }

    public void update() {
        updateVisibility();
        updateSpells();
    }

    public void draw(Graphics2D g2d) {
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                tile.draw(g2d);
            }
        }
    }
}
