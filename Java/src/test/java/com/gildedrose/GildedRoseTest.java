package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void normalItem_DecreasesQualityAndSellInBy1() {
        Item[] items = new Item[] { new Item("Normal Item", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(19, app.items[0].quality, "Quality should decrease by 1");
    }

    @Test
    void normalItem_QualityDegradesTwiceAfterSellInDate() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(18, app.items[0].quality, "Quality should decrease by 2 after sell-by date");
    }

    @Test
    void quality_NeverNegative() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(0, app.items[0].quality, "Quality should not go below 0");
    }

    @Test
    void agedBrie_IncreasesInQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(1, app.items[0].quality, "Quality should increase by 1");
    }

    @Test
    void quality_NeverMoreThan50() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 50),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(50, app.items[0].quality, "Quality should not exceed 50");
        assertEquals(9, app.items[1].sellIn, "SellIn should decrease by 1");
        assertEquals(50, app.items[1].quality, "Quality should not exceed 50");
    }

    @Test
    void sulfuras_QualityAndSellInUnchanged() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn, "SellIn should remain unchanged for Sulfuras");
        assertEquals(80, app.items[0].quality, "Quality should remain unchanged at 80");
    }

    @Test
    void backstagePasses_IncreaseBy1_MoreThan10Days() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(14, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(21, app.items[0].quality, "Quality should increase by 1");
    }

    @Test
    void backstagePasses_IncreaseBy2_10DaysOrLess() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(22, app.items[0].quality, "Quality should increase by 2");
    }

    @Test
    void backstagePasses_IncreaseBy3_5DaysOrLess() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(23, app.items[0].quality, "Quality should increase by 3");
    }

    @Test
    void backstagePasses_QualityDropsToZero_AfterConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(0, app.items[0].quality, "Quality should drop to 0 after concert");
    }

    @Test
    void agedBrie_QualityNeverExceeds50() {
        Item[] items = new Item[] { new Item("Aged Brie", 1, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality(); // Day 1
        app.updateQuality(); // Day 2
        assertEquals(-1, app.items[0].sellIn, "SellIn should decrease by 2 after two days");
        assertEquals(50, app.items[0].quality, "Quality should not exceed 50");
    }

    @Test
    void backstagePasses_QualityNeverExceeds50_BeforeConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality(); // Day 1
        assertEquals(4, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(50, app.items[0].quality, "Quality should not exceed 50");
    }

    @Test
    void sulfuras_QualityStaysAt80_AfterManyDays() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        for (int day = 0; day < 10; day++) {
            app.updateQuality();
        }
        assertEquals(0, app.items[0].sellIn, "SellIn should remain unchanged");
        assertEquals(80, app.items[0].quality, "Quality should remain at 80");
    }

    @Test
    void normalItem_QualityDegradesBy2_AfterSellInDate() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(8, app.items[0].quality, "Quality should decrease by 2 after sell-by date");
    }

    @Test
    void agedBrie_IncreasesQualityBy2_AfterSellInDate() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(12, app.items[0].quality, "Quality should increase by 2 after sell-by date");
    }

    @Test
    void normalItem_QualityNeverMoreThan50() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn, "SellIn should decrease by 1");
        assertEquals(49, app.items[0].quality, "Quality should decrease by 1 and not exceed 50");
    }
}
