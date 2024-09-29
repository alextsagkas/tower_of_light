package items;

import characters.player.Player;
import interfaces.*;
import items.equipables.EquipableItem;
import items.equipables.weapons.Weapon;
import items.usables.UsableItem;
import main.GamePanel;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * Aggregate the item management in one class.
 */
public final class ItemManager implements Drawable, Updatable, Resettable, LogSubject {
    private final GamePanel gamePanel;
    private final Map<DiscreteMapPosition, LinkedList<UsableItem>> usableItems;
    private final Map<DiscreteMapPosition, LinkedList<EquipableItem>> equipableItems;
    private LogObserver logObserver;

    public ItemManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.usableItems = new HashMap<>();
        this.equipableItems = new HashMap<>();
    }

    /**
     * Add a usable item on the map.
     *
     * @param item the item to add.
     */
    public void add(@NotNull UsableItem item) {
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

    /**
     * Add am equipable item on the map.
     *
     * @param item the equipable item to add.
     */
    public void add(@NotNull EquipableItem item) {
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

    /**
     * Get the equipable item from a certain position on the map.
     *
     * @param position the position on where to search for an item.
     * @return the equipable item found on the position.
     */
    public @Nullable EquipableItem getEquipableItem(DiscreteMapPosition position) {
        LinkedList<EquipableItem> items = equipableItems.get(position);

        if (items == null || items.isEmpty()) {
            return null;
        } else {
            return items.remove();
        }
    }

    /**
     * Get the weapon from a certain position on the map.
     *
     * @param position the position on where to search for a weapon.
     * @return the weapon found on that position.
     */
    public @Nullable EquipableItem getWeapon(DiscreteMapPosition position) {
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

    /**
     * Get a list with all the items on the map
     *
     * @return the item's list.
     */
    private List<Item> itemsAggregate() {
        Stream<UsableItem> usableItemStream = usableItems.values()
                                                         .stream()  // Stream<PriorityQueue<EquipableItem>>
                                                         .flatMap(Collection::stream);  // Stream<EquipableItem>
        Stream<EquipableItem> equipableItemsStream = equipableItems.values()
                                                                   .stream()  // Stream<PriorityQueue<EquipableItem>>
                                                                   .flatMap(Collection::stream);  // Stream<EquipableItem>
        return Stream.concat(usableItemStream, equipableItemsStream).toList();
    }

    public void attachLogObserver(LogObserver logObserver) {this.logObserver = logObserver;}

    public void notifyLogObserver(String log) {logObserver.updateLog(log);}

    /**
     * Convert all items to light be marking them as discovered. The purpose of this method
     * is to be called by the one that converts the map to light.
     */
    public void convertToLight() {
        itemsAggregate().stream()
                        .filter(item -> !item.isDiscovered())
                        .forEach(item -> item.setDiscovered(true));
        notifyLogObserver("Items have been revealed.");
    }

    /**
     * Update the visibility of the items on the map based on their relative position to player.
     */
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

    /**
     * Whenever player steps on a usable item it gets added to his inventory and removed from the map.
     */
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

    /**
     * Notify player about the equipable items located on the tile he steps on.
     */
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

    /**
     * Update the TileManager state by updating visibility of items on the map,
     * putting usable items the player steps on in his inventory and informing him
     * about the equipable items that he steps on.
     */
    public void update() {
        updateVisibility();
        updateInventory();
        updateEquipment();
    }

    /**
     * Reset the state of the ItemManager by clearing all the items from the map.
     */
    public void reset() {
        usableItems.clear();
        equipableItems.clear();
    }

    public void draw(Graphics2D g2d) {
        for (Item item : itemsAggregate()) {
            item.draw(g2d);
        }
    }
}
