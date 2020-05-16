package com.example.activity;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Group {
    ArrayList<Player> Players;
    String name;
    Pawn pawn;

    Group(int next){
        Players = new ArrayList<>();
    }
    Group(String name, ArrayList<String> players ){
        Random rand = new Random();
        this.name = name;
        Players = new ArrayList<>();
        for(int i =0;i < players.size(); i++) {
            System.out.println(players.get(i));
            Player aux = new Player(players.get(i));
            Players.add(aux);
        }
    }

    public void AddPlayer(Player player) {
        Players.add(player);
    }
    public List GetPlayers() {
        return Players;
    }

}
