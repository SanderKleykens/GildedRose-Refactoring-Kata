package com.gildedrose;

import static org.assertj.core.api.Assertions.assertThat;

import com.gildedrose.items.Item;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class GildedRoseTest {

    @Test
    void normalItem_DecreasesQualityAndSellInBy1() {
        Item item = new Item("Normal Item", 10, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 9, 19));
    }

    @Test
    void normalItem_QualityDegradesTwiceAfterSellInDate() {
        Item item = new Item("Normal Item", 0, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, -1, 18));
    }

    @Test
    void quality_NeverNegative() {
        Item item = new Item("Normal Item", 5, 0);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 4, 0));
    }

    @Test
    void agedBrie_IncreasesInQuality() {
        Item item = new Item("Aged Brie", 2, 0);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 1, 1));
    }

    @Test
    void sulfuras_QualityAndSellInUnchanged() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 0, 80));
    }

    @Test
    void backstagePasses_IncreaseBy1_MoreThan10Days() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 14, 21));
    }

    @Test
    void backstagePasses_IncreaseBy2_10DaysOrLess() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 9, 22));
    }

    @Test
    void backstagePasses_IncreaseBy3_5DaysOrLess() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 4, 23));
    }

    @Test
    void backstagePasses_QualityDropsToZero_AfterConcert() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, -1, 0));
    }

    @Test
    void agedBrie_QualityNeverExceeds50() {
        Item item = new Item("Aged Brie", 1, 49);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality(); // Day 1
        gildedRose.updateQuality(); // Day 2

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, -1, 50));
    }

    @Test
    void backstagePasses_QualityNeverExceeds50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 4, 50));
    }

    @Test
    void sulfuras_QualityStaysAt80() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        GildedRose gildedRose = new GildedRose(item);

        IntStream.range(0, 10).forEach(i -> gildedRose.updateQuality());

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 0, 80));
    }

    @Test
    void normalItem_QualityDegradesBy2_AfterSellInDate() {
        Item item = new Item("Normal Item", 0, 10);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, -1, 8));
    }

    @Test
    void agedBrie_IncreasesQualityBy2_AfterSellInDate() {
        Item item = new Item("Aged Brie", 0, 10);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, -1, 12));
    }

    @Test
    void conjuredItem_QualityNeverNegative() {
        Item item = new Item("Conjured Mana Cake", 5, 1);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 4, 0));
    }

    @Test
    void normalItem_QualityNeverMoreThan50() {
        Item item = new Item("Normal Item", 5, 50);
        GildedRose gildedRose = new GildedRose(item);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactly(itemAssertion(item.name, 4, 49));
    }

    @Test
    void multipleItems_AreUpdatedUsingCorrectStrategy() {
        Item item = new Item("Normal Item", 10, 20);
        Item agedBrie = new Item("Aged Brie", 2, 10);
        GildedRose gildedRose = new GildedRose(item, agedBrie);

        gildedRose.updateQuality();

        assertThat(gildedRose.getItems()).satisfiesExactlyInAnyOrder(
            itemAssertion(item.name, 9, 19),
            itemAssertion(agedBrie.name, 1, 11)
        );
    }

    private Consumer<Item> itemAssertion(String expectedName, int expectedSellIn, int expectedQuality) {
        return item -> {
            assertThat(item.name).isEqualTo(expectedName);
            assertThat(item.quality).isEqualTo(expectedQuality);
            assertThat(item.sellIn).isEqualTo(expectedSellIn);
        };
    }
}
