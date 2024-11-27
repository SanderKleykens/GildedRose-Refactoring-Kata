package com.gildedrose;

import com.gildedrose.items.Item;
import com.gildedrose.items.ItemUpdater;
import com.gildedrose.items.behaviors.BackstagePassItemUpdateStrategy;
import com.gildedrose.items.behaviors.DegradationItemUpdateStrategy;
import com.gildedrose.items.behaviors.NoOpItemUpdateStrategy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GildedRose {
    private final Collection<Item> items;

    private static final int NORMAL_ITEM_DEGRADATION = -1;
    private static final int CONJURED_ITEM_DEGRADATION_MULTIPLIER = 2;
    private static final int AGED_BRIE_DEGRADATION = 1;
    private static final List<ItemUpdater> UPDATERS = Arrays.asList(
        new ItemUpdater(item -> "Aged Brie".equals(item.name), new DegradationItemUpdateStrategy(AGED_BRIE_DEGRADATION)),
        new ItemUpdater(item -> "Backstage passes to a TAFKAL80ETC concert".equals(item.name), new BackstagePassItemUpdateStrategy()),
        new ItemUpdater(item -> "Sulfuras, Hand of Ragnaros".equals(item.name), new NoOpItemUpdateStrategy()),
        new ItemUpdater(item -> item.name.startsWith("Conjured"), new DegradationItemUpdateStrategy(NORMAL_ITEM_DEGRADATION * CONJURED_ITEM_DEGRADATION_MULTIPLIER)),
        new ItemUpdater(item -> true, new DegradationItemUpdateStrategy(NORMAL_ITEM_DEGRADATION))
    );

    public GildedRose(Item... items) {
        this.items = Arrays.asList(items);
    }

    public void updateQuality() {
        items.forEach(item -> getItemUpdater(item).ifPresent(updater -> updater.update(item)));
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    private Optional<ItemUpdater> getItemUpdater(Item item) {
        return UPDATERS.stream()
            .filter(updater -> updater.supports(item))
            .findFirst();
    }
}
