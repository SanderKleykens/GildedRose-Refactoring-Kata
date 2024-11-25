package com.gildedrose.items.behaviors;

import com.gildedrose.items.Item;

public interface ItemUpdateStrategy {
    int MAXIMUM_ITEM_QUALITY = 50;

    void update(Item item);
}
