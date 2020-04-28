package com.example.activity;

import java.util.ArrayList;
import java.util.Random;

class Board {
    static ArrayList<Player> Players = new ArrayList<Player>();
    static ArrayList<Group> Groups = new  ArrayList<Group>();
    private Carts Carts= new Carts();

    private Board() {

    }
    public void AddPlayer(String Name) {
        Player player = new Player(Name);
        Players.add(player);
    }

    public boolean CreateRandomGroups(int nrGroups) {
        int size = Players.size(), index;
        int nrPlayers = size/nrGroups;
        if(nrPlayers < 2)
            return false;
        Random rand = new Random();
        Player player;
        ArrayList<Player> aux = new ArrayList<>(Players);

        for (int i = 0; i < nrGroups; i++) {
            index = rand.nextInt(nrPlayers);
            Group naux = new Group(index);
            for(int j = 0; j < nrPlayers; j++) {
                index = rand.nextInt(size);
                player = aux.get(index);
                aux.remove(index);
                size--;
                naux.AddPlayer(player);
            }
            this.Groups.add(naux);
        }
        return true;
    }

    public void Get(){

    }

    public boolean addGroup(String name, ArrayList<String> Players) {

        return true;
    }

}
