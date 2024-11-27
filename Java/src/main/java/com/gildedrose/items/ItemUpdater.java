package com.gildedrose.items;

import com.gildedrose.items.behaviors.ItemUpdateStrategy;
import java.util.function.Predicate;

public final class ItemUpdater {
    private final Predicate<Item> supports;
    private final ItemUpdateStrategy strategy;

    public ItemUpdater(Predicate<Item> supports, ItemUpdateStrategy strategy) {
        this.supports = supports;
        this.strategy = strategy;
    }

    public boolean supports(Item item) {
        return supports.test(item);
    }

    public void update(Item item) {
        strategy.update(item);
    }
}
