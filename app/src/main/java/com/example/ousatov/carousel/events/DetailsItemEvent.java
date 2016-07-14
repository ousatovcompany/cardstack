package com.example.ousatov.carousel.events;

import com.example.ousatov.carousel.data.Item;

public class DetailsItemEvent {
    Item item;

    public DetailsItemEvent(Item i) {
        item = i;
    }

    public Item getItem() {
        return item;
    }
}
