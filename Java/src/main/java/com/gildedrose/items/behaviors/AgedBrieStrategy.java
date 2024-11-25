package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public class AgedBrieStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {
        item.sellIn--;

        int increaseAmount = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(50, item.quality + increaseAmount);
    }
}
