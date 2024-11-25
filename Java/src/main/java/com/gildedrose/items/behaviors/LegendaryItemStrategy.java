package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public class LegendaryItemStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {
        // legendary items do not change
    }
}
