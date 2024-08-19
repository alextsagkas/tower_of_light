package tile;

import main.GamePanel;

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
        this.mapTiles = new Tile[this.gamePanel.getMaxScreenRow()][this.gamePanel.getMaxScreenCol()];
    }

    private void loadMap() throws RuntimeException {
        String levelPath = "/map/level" + gamePanel.getGameLevel() + ".txt";

        try (InputStream is = getClass().getResourceAsStream(levelPath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            for (int row = 0; row < this.gamePanel.getMaxScreenRow(); row++) {
                String[] tileTypes = reader.readLine().split(" ");

                for (int col = 0; col < this.gamePanel.getMaxScreenCol(); col++) {
                    TileType tileType = TileType.fromInteger(Integer.parseInt(tileTypes[col]));
                    mapTiles[row][col] = Tile.createTile(tileType, col, row, gamePanel.getTileSize());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2d) {
        loadMap();

        for (Tile[] rowOfTiles : this.mapTiles) {
            for (Tile tile : rowOfTiles) {
                Objects.requireNonNull(tile).drawVisible(g2d);
            }
        }
    }
}
