package com.example.ousatov.carousel.data;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Item implements Comparable<Item> {

    private String data;
    private int color;
    private int number;

    public Item(String s, int n, int c) {
        data = s;
        color = c;
        number = n;
    }

    public String getData() {
        return data;
    }

    public int getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NonNull Item item) {
        return this.number - item.getNumber();
    }

    public String serializeItem() {
        Gson gs = new Gson();
        return gs.toJson(this);
    }

    public static Item deserializeItem(String json) {
        Gson gs = new Gson();
        Type type = new TypeToken<Item>() {
        }.getType();
        Item item = gs.fromJson(json, type);
        String data = item.getData();
        int number = item.getNumber();
        int color = item.getColor();
        return new Item(data, number, color);
    }
}
