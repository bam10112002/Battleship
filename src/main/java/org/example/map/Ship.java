package org.example.map;

import java.util.ArrayList;

class Ship {
    public Integer id;
    public Integer size = 1;
    public ArrayList<Coord> coords = new ArrayList<Coord>();
    boolean validate() {
        if (coords.size() == 1)
            return true;

        int dx = coords.get(1).x - coords.get(0).x;
        int dy = coords.get(1).y - coords.get(0).y;

        if (dx == dy || dx > 0 && dy > 0 || coords.get(0).x < 0 || coords.get(0).y < 0)
            return false;

        for (int i = 1; i < coords.size()-1; i++) {
            if (coords.get(i).x - coords.get(i - 1).x != dx || coords.get(i).y - coords.get(i - 1).y != dy ||
                    coords.get(i).x < 0 || coords.get(i).y < 0) {
                return false;
            }
        }

        return true;
    }
}