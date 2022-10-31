package org.example.map;

public class Coord_Char extends Coord {
    public Character ch;
    Coord_Char(int x, int y, Character ch) {
        super(x,y);
        this.ch = ch;
    }
}