package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public class DegradationItemUpdateStrategy implements ItemUpdateStrategy {
    private final int degradation;

    public DegradationItemUpdateStrategy(int degradation) {
        this.degradation = degradation;
    }

    @Override
    public void update(Item item) {
        item.sellIn--;

        int degradeAmount = item.sellIn < 0 ? degradation * 2 : degradation;
        item.quality = Math.min(MAXIMUM_ITEM_QUALITY, Math.max(0, item.quality + degradeAmount));
    }
}
