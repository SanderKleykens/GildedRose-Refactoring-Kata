package com.gildedrose;

import com.gildedrose.items.Item;
import com.gildedrose.items.behaviors.AgedBrieStrategy;
import com.gildedrose.items.behaviors.BackstagePassStrategy;
import com.gildedrose.items.behaviors.ItemUpdateStrategy;
import com.gildedrose.items.behaviors.LegendaryItemStrategy;
import com.gildedrose.items.behaviors.NormalItemStrategy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class GildedRose {
    private final Collection<Item> items;

    public GildedRose(Item... items) {
        this.items = Arrays.asList(items);
    }

    public void updateQuality() {
        items.forEach(item -> getStrategy(item).update(item));
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    private ItemUpdateStrategy getStrategy(Item item) {
        if ("Aged Brie".equals(item.name)) {
            return new AgedBrieStrategy();
        } else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.name)) {
            return new BackstagePassStrategy();
        } else if ("Sulfuras, Hand of Ragnaros".equals(item.name)) {
            return new LegendaryItemStrategy();
        } else {
            return new NormalItemStrategy();
        }
    }
}
