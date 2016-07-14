package com.example.ousatov.carousel.data;


import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;

public class Item implements Comparable<Item>{

    private String data;
    private ColorDrawable color;
    private int number;

    public Item(String s, int n, ColorDrawable c) {
        data = s;
        color = c;
        number = n;
    }

    public String getData() {
        return data;
    }
    public ColorDrawable getColor() {
        return color;
    }
    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NonNull Item item) {
        return this.number - item.getNumber();
    }
}
