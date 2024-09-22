package characters.player;

import interfaces.Restartable;
import interfaces.StatObserver;
import interfaces.StatSubject;
import interfaces.Updatable;
import items.effects.ItemEffect;
import items.usables.UsableItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ItemInventory implements Updatable, Restartable, StatSubject {
    final List<UsableItem> usableItemsList;
    private StatObserver statObserver;

    public ItemInventory() {
        usableItemsList = new ArrayList<>();
    }

    public void attachStatObserver(StatObserver statObserver) {
        this.statObserver = statObserver;
    }

    public void notifyStatObserver() {
        statObserver.updateStats();
    }

    public void addItem(UsableItem item) {
        usableItemsList.add(item);
        notifyStatObserver();
    }

    public UsableItem getItemType(ItemEffect.ItemEffectType itemEffectType) {
        for (UsableItem usableItem : usableItemsList) {
            for (ItemEffect.ItemEffectType effectItemEffectType : usableItem.discreteItemEffectTypes()) {
                if (effectItemEffectType.equals(itemEffectType)) {
                    return usableItem;
                }
            }
        }
        return null;
    }

    private long numberOfItemsWithItemEffectType(ItemEffect.ItemEffectType itemEffectType) {
        return usableItemsList.stream()
                              .filter(item -> item.discreteItemEffectTypes().contains(itemEffectType))
                              .count();
    }

    private long numberOfUsesForItemsWithItemEffectType(ItemEffect.ItemEffectType itemEffectType) {
        return usableItemsList.stream()
                              .filter(item -> item.discreteItemEffectTypes().contains(itemEffectType))
                              .mapToLong(UsableItem::getUsesRemaining)
                              .sum();
    }

    public String contentsToString() {
        final StringBuilder inventoryContents = new StringBuilder();

        for (ItemEffect.ItemEffectType itemEffectType : ItemEffect.ItemEffectType.values()) {
            long count = numberOfItemsWithItemEffectType(itemEffectType);
            long totalUsesRemaining = numberOfUsesForItemsWithItemEffectType(itemEffectType);
            inventoryContents.append(
                    String.format(
                            "%s: %d (uses %d) <br>",
                            itemEffectType.getHumanReadableName(),
                            count,
                            totalUsesRemaining
                    )
            );
        }

        return inventoryContents.toString();
    }

    private void removeUsedItems() {
        Iterator<UsableItem> iterator = usableItemsList.iterator();
        while (iterator.hasNext()) {
            UsableItem usableItem = iterator.next();
            if (usableItem.getUsesRemaining() == 0) {
                iterator.remove();
                notifyStatObserver();
            }
        }
    }

    public void update() {
        removeUsedItems();
    }

    public void restart() {
        usableItemsList.clear();
    }
}
