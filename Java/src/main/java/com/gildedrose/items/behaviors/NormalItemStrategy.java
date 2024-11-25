package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public class NormalItemStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {
        item.sellIn--;

        int degradeAmount = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(0, item.quality - degradeAmount);
    }
}
