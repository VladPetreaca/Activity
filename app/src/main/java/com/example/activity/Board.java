package com.example.activity;

import java.util.ArrayList;
import java.util.Random;

class Board {
    static ArrayList<Player> Players = new ArrayList<Player>();
    static ArrayList<Group> Groups = new  ArrayList<Group>();
    private Carts Carts= new Carts();

    private Board() {

    }
    public static void AddPlayer(String Name) {
        Player player = new Player(Name);
        Players.add(player);
    }

    public static boolean CreateRandomGroups(int nrGroups) {
        int size = Players.size() - 1, index;
        int nrPlayers = size/nrGroups;
        if(nrPlayers < 2)
            return false;
        Random rand = new Random();
        Player player;

        Groups = new ArrayList<Group>();

        ArrayList<Player> aux = new ArrayList<>(Players);
        aux.remove(0);
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

            naux.name = String.valueOf(i);
            Groups.add(naux);
        }
        return true;
    }

    public void Get(){

    }

    public boolean addGroup(String name, ArrayList<String> Players) {

        return true;
    }

    public static String show_group() {

        if(Groups.isEmpty()) {
            return "Nu avem nici o echipa!\n";
        }
        else {
            String result = "";

            for(int i=0;i<Groups.size();i++) {
                result += "Echipa " + Groups.get(i).name + " are jucatorii: ";

                for(int j=0;j<Groups.get(i).GetPlayers().size();j++) {
                    if(j != Groups.get(i).GetPlayers().size() - 1) {
                        result += ((Player)(Groups.get(i).GetPlayers().get(j))).getName();
                        result += ", ";
                    }
                    else {
                        result += ((Player)(Groups.get(i).GetPlayers().get(j))).getName();
                        result += "\n";
                    }
                }
            }

            return result;
        }
    }

    public static ArrayList<String> getPlayers() {

        ArrayList<String> res = new ArrayList<>();

        for(int i=0;i<Players.size();i++) {
            res.add(Players.get(i).Name);
        }

        return res;
    }
}
