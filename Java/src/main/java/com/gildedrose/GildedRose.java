package com.gildedrose;

import com.gildedrose.items.Item;
import com.gildedrose.items.behaviors.BackstagePassItemUpdateStrategy;
import com.gildedrose.items.behaviors.DegradationItemUpdateStrategy;
import com.gildedrose.items.behaviors.ItemUpdateStrategy;
import com.gildedrose.items.behaviors.NoOpItemUpdateStrategy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class GildedRose {
    private final Collection<Item> items;

    private static final int NORMAL_ITEM_DEGRADATION = -1;
    private static final int CONJURED_ITEM_DEGRADATION_MULTIPLIER = 2;
    private static final int AGED_BRIE_DEGRADATION = 1;

    public GildedRose(Item... items) {
        this.items = Arrays.asList(items);
    }

    public void updateQuality() {
        items.forEach(item -> getItemUpdateStrategy(item).update(item));
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    private ItemUpdateStrategy getItemUpdateStrategy(Item item) {
        if ("Aged Brie".equals(item.name)) {
            return new DegradationItemUpdateStrategy(AGED_BRIE_DEGRADATION);
        } else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.name)) {
            return new BackstagePassItemUpdateStrategy();
        } else if ("Sulfuras, Hand of Ragnaros".equals(item.name)) {
            return new NoOpItemUpdateStrategy();
        } else if (item.name.startsWith("Conjured")) {
            return new DegradationItemUpdateStrategy(NORMAL_ITEM_DEGRADATION * CONJURED_ITEM_DEGRADATION_MULTIPLIER);
        } else {
            return new DegradationItemUpdateStrategy(NORMAL_ITEM_DEGRADATION);
        }
    }
}
