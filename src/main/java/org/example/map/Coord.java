package org.example.map;

import java.io.Serializable;

public class Coord implements Serializable {
    public Integer x;
    public Integer y;
    public Coord(){}
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
