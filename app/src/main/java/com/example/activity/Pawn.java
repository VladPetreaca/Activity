package com.example.activity;

import android.widget.FrameLayout;
import android.widget.ImageView;

public class Pawn {

    float x_min;
    float x_max;
    float y_min;
    float y_max;
    float over_start;
    int count_lines;
    int count_columns;
    boolean start;
    FrameLayout pawn_xy;
    ImageView img_pawn;

    public Pawn(FrameLayout pawn_xy, ImageView img_pawn, float over_start) {
        x_min = 0;
        x_max = 0;
        y_min = 0;
        y_max = 0;
        count_columns = 0;
        count_lines = 0;
        start = false;
        this.over_start = over_start;
        this.img_pawn = img_pawn;
        this.pawn_xy = pawn_xy;
    }
}
