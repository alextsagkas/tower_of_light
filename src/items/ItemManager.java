package items;

import characters.player.Player;
import interfaces.LogObserver;
import interfaces.LogSubject;
import interfaces.Resettable;
import interfaces.Updatable;
import items.equipables.EquipableItem;
import items.equipables.weapons.Weapon;
import items.usables.UsableItem;
import main.GamePanel;
import map.DiscreteMapPosition;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public final class ItemManager implements Updatable, Resettable, LogSubject {
    private final GamePanel gamePanel;
    private final HashMap<DiscreteMapPosition, LinkedList<UsableItem>> usableItems;
    private final HashMap<DiscreteMapPosition, LinkedList<EquipableItem>> equipableItems;
    private LogObserver logObserver;

    public ItemManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.usableItems = new HashMap<>();
        this.equipableItems = new HashMap<>();
    }

    public void add(UsableItem item) {
        DiscreteMapPosition position = item.getPosition();

        LinkedList<UsableItem> items;
        if (usableItems.containsKey(position)) {
            items = usableItems.get(position);
        } else {
            items = new LinkedList<>();
        }
        items.add(item);

        usableItems.put(position, items);
    }

    public void add(EquipableItem item) {
        DiscreteMapPosition position = item.getPosition();

        LinkedList<EquipableItem> items;
        if (equipableItems.containsKey(position)) {
            items = equipableItems.get(position);
        } else {
            items = new LinkedList<>();
        }
        items.add(item);

        equipableItems.put(position, items);
    }

    public EquipableItem getEquipableItem(DiscreteMapPosition position) {
        LinkedList<EquipableItem> items = equipableItems.get(position);

        if (items == null || items.isEmpty()) {
            return null;
        } else {
            return items.remove();
        }
    }

    public EquipableItem getWeapon(DiscreteMapPosition position) {
        LinkedList<EquipableItem> items = equipableItems.get(position);

        // No items in the position
        if (items == null || items.isEmpty()) {return null;}

        Optional<EquipableItem> weaponOpt = items.stream().filter(item -> item instanceof Weapon).findFirst();
        if (weaponOpt.isPresent()) {
            EquipableItem weapon = weaponOpt.get();
            items.remove(weapon);
            return weapon;
        }

        // No weapon in the list
        return null;
    }

    private List<Item> itemsAggregate() {
        Stream<UsableItem> usableItemStream = usableItems.values()
                                                         .stream()  // Stream<PriorityQueue<EquipableItem>>
                                                         .flatMap(Collection::stream);  // Stream<EquipableItem>
        Stream<EquipableItem> equipableItemsStream = equipableItems.values()
                                                                   .stream()  // Stream<PriorityQueue<EquipableItem>>
                                                                   .flatMap(Collection::stream);  // Stream<EquipableItem>
        return Stream.concat(usableItemStream, equipableItemsStream).toList();
    }

    public void reset() {
        usableItems.clear();
        equipableItems.clear();
    }

    public void attachLogObserver(LogObserver logObserver) {this.logObserver = logObserver;}

    public void notifyLogObserver(String log) {logObserver.updateLog(log);}

    public void convertToLight() {
        itemsAggregate().stream()
                        .filter(item -> !item.isDiscovered())
                        .forEach(item -> item.setDiscovered(true));
        notifyLogObserver("Items have been revealed.");
    }

    private void updateVisibility() {
        Player player = gamePanel.player;
        for (Item item : itemsAggregate()) {
            int distanceToItem = item.getPosition().distanceTo(player.getPosition());
            if (distanceToItem <= player.visibilityRadius) {
                item.setVisible(true);
                if (!(item.isDiscovered())) {
                    item.setDiscovered(true);
                }
            } else {
                item.setVisible(false);
            }
        }
    }

    private void updateInventory() {
        Player player = gamePanel.player;

        LinkedList<UsableItem> usableItemsOnPosition = usableItems.get(player.getPosition());
        if (usableItemsOnPosition == null) {return;}

        notifyLogObserver("Gather usables from the tile:");
        Iterator<UsableItem> iterator = usableItemsOnPosition.iterator();
        while (iterator.hasNext()) {
            UsableItem item = iterator.next();
            if (item.getPosition().equals(player.getPosition())) {
                player.itemInventory.addItem(item);
                iterator.remove();
                notifyLogObserver(String.format("- %s", item.getItemName()));
            }
        }
    }

    private void updateEquipment() {
        if (equipableItems.isEmpty()) {return;}

        Player player = gamePanel.player;
        LinkedList<EquipableItem> equipableItemsOnPosition = equipableItems.get(player.getPosition());

        if (equipableItemsOnPosition == null) {return;}

        notifyLogObserver("Equipable items on the tile:");
        for (EquipableItem item : equipableItemsOnPosition) {
            if (item.getPosition().equals(player.getPosition())) {
                String className = item.getClass().toString().replaceAll(".*\\.", "");
                notifyLogObserver(String.format(
                        "- %s %s",
                        item.getItemName(),
                        "(" + className + ")"
                ));
            }
        }
    }

    public void update() {
        updateVisibility();
        updateInventory();
        updateEquipment();
    }

    public void draw(Graphics2D g2d) {
        for (Item item : itemsAggregate()) {
            item.draw(g2d);
        }
    }
}
