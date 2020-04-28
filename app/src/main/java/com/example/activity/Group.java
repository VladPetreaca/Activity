package com.example.activity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    ArrayList<Player> Players;
    int score;
    int nextPlayer;
    Group(int next){
        score = 0;
        nextPlayer = next;
        Players = new ArrayList<>();
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
