package tile;

import characters.Player;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[][] mapTiles;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mapTiles = new Tile[DiscreteMap.maxScreenRow][DiscreteMap.maxScreenCol];
        loadMap();
    }

    public Tile getTile(DiscreteMapPosition position) {
        return mapTiles[position.getY()][position.getX()];
    }

    private void loadMap() throws RuntimeException {
        System.out.println("Loading Map");
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

    public void draw(Graphics2D g2d) {
        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                Player player = gamePanel.player;
                DiscreteMapPosition playerPosition = player.getPosition();

                int distanceToTile = tile.getPosition().distanceTo(playerPosition);

                if (distanceToTile <= player.visibilityRadius) {
                    tile.setVisible(true);
                    if (!(tile.isDiscovered())) {
                        tile.setDiscovered(true);
                    }
                } else {
                    tile.setVisible(false);
                }

                tile.draw(g2d);
            }
        }
    }
}
