package com.example.activity;

import java.util.ArrayList;

public class Box {
    String name;
    ArrayList<Integer> specified_boxes;

    public Box(String name, ArrayList<Integer> l) {
        this.name = name;
        specified_boxes = new ArrayList<>(l);
    }
}
