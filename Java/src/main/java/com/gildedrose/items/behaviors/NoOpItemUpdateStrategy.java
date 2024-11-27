package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public class NoOpItemUpdateStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {}
}
