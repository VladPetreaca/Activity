package com.example.activity;

public class Player  implements Comparable{
    String Name;
    Player(String Name) {
        this.Name = Name;
    }
    public String getName() { return this.Name ;}

    @Override
    public int compareTo(Object o) {
        Player aux = (Player)o;
        return Name.compareTo(aux.Name);
    }
}
