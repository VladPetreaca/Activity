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
    int score;
    int nextPlayer;
    int order;

    Group(int next){
        score = 0;
        nextPlayer = next;
        Players = new ArrayList<>();
    }
    Group(String name, ArrayList<String> players ){
        Random rand = new Random();
        score = 0;
        this.name = name;
        order = -1;
        Players = new ArrayList<>();
        for(int i =0;i < players.size(); i++) {
            System.out.println(players.get(i));
            Player aux = new Player(players.get(i));
            Players.add(aux);
        }
        nextPlayer = rand.nextInt(players.size());
    }

    public void AddPlayer(Player player) {
        Players.add(player);
    }
    public List GetPlayers() {
        return Players;
    }

    public Player getNextPlayer() {
        int next = this.nextPlayer;
        this.nextPlayer = (this.nextPlayer + 1) % Players.size();
        return Players.get(next);
    }
}
