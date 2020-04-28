package Game;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    ArrayList<Player> Players;
    ArrayList<Group> Groups;
    private Carts Carts;
    public Board() {
        this.Carts = new Carts();
        Players = new ArrayList<Player>();
        Groups = new  ArrayList<Group>();
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

    public boolean addGroup() {
        return true;
    }

}
