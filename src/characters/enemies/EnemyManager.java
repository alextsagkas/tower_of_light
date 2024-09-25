package characters.enemies;

import characters.Entity;
import interfaces.*;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class EnemyManager implements Updatable, Resettable, Restartable, LogSubject, StatSubject {
    private final GamePanel gamePanel;
    private final int startingNumberOfEnemies;
    private final ArrayList<Enemy> allEnemiesList;
    private final ArrayList<Enemy> generatedEnemiesList;
    private LogObserver logObserver;
    private StatObserver statObserver;

    public EnemyManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        startingNumberOfEnemies = 3;

        this.allEnemiesList = new ArrayList<>();
        this.allEnemiesList.add(new Archangel(gamePanel));
        this.allEnemiesList.add(new Bishop(gamePanel));
        this.allEnemiesList.add(new ChaosKnight(gamePanel));
        this.allEnemiesList.add(new FallenHero(gamePanel));
        this.allEnemiesList.add(new Friend(gamePanel));
        this.allEnemiesList.add(new HeraldOfChaos(gamePanel));
        this.allEnemiesList.add(new HeraldOfLight(gamePanel));
        this.allEnemiesList.add(new Knight(gamePanel));
        this.allEnemiesList.add(new Paladin(gamePanel));
        this.allEnemiesList.add(new Priest(gamePanel));
        this.allEnemiesList.add(new Summoner(gamePanel));
        this.allEnemiesList.add(new Vampire(gamePanel));

        this.generatedEnemiesList = new ArrayList<>();

        reset();
    }

    public ArrayList<Enemy> getGeneratedEnemies() {
        return generatedEnemiesList;
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
        for (var enemy : generatedEnemiesList) {
            enemy.attachLogObserver(logObserver);
        }
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    public void attachStatObserver(StatObserver statObserver) {
        this.statObserver = statObserver;
        for (var enemy : generatedEnemiesList) {
            enemy.attachStatObserver(statObserver);
        }
    }

    public void notifyStatObserver() {
        this.statObserver.updateStats();
    }

    public List<Entity> getEnemies(int proximity) {
        ArrayList<Entity> proximalEnemies = new ArrayList<>();
        for (var enemy : generatedEnemiesList) {
            if (gamePanel.player.getPosition().distanceTo(enemy.getPosition()) <= proximity) {
                proximalEnemies.add(enemy);
            }
        }
        return proximalEnemies;
    }

    public void buryEnemy(Enemy enemy) {
        generatedEnemiesList.remove(enemy);
        notifyStatObserver();
    }

    public void update() {
        for (Enemy enemy : getGeneratedEnemies()) {
            enemy.update();
        }
    }

    public String humanReadable() {
        StringBuilder enemiesString = new StringBuilder("<html>");
        for (Enemy enemy : generatedEnemiesList) {
            if (enemy.isDiscovered()) {
                {enemiesString.append(String.format("%s: ", enemy.getName()));}
                enemiesString.append(String.format("%d/%d", enemy.getHitPoints(), enemy.getMaxHitPoints()));
                enemiesString.append("<br>");
            }
        }
        return enemiesString.toString();
    }

    private void addRandomEnemy() {
        Random rand = new Random();
        while (true) {
            Enemy enemy = allEnemiesList.get(rand.nextInt(allEnemiesList.size())).deepCopy();
            if (enemy.getAppearOnLevel()
                     .stream()
                     .anyMatch(level -> level.equals(gamePanel.getGameLevel()))) {
                enemy.setRandomPosition();
                enemy.attachLogObserver(logObserver);
                enemy.attachStatObserver(statObserver);
                generatedEnemiesList.add(enemy);
                return;
            }
        }
    }

    private void makeEnemiesStrongerOnBeaconCreation() {
        for (var enemy : generatedEnemiesList) {
            enemy.makeStrongerOnBeaconCreation();
        }
    }

    private void increaseEnemiesAtBeaconCreation() {
        int increaseFactor = 2;
        for (int i = 0; i < increaseFactor; i++) {
            addRandomEnemy();
            notifyStatObserver();
        }
    }

    public void updateOnBeaconCreation() {
        makeEnemiesStrongerOnBeaconCreation();
        increaseEnemiesAtBeaconCreation();
    }

    public void reset() {
        generatedEnemiesList.clear();
        for (int i = 0; i < startingNumberOfEnemies; i++) {
            addRandomEnemy();
        }
    }

    public void draw(Graphics2D g2d) {
        for (Enemy enemy : generatedEnemiesList) {
            enemy.draw(g2d);
        }
    }
}
