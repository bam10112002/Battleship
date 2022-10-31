package org.example;

import org.example.map.Map;
import org.example.map.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map map1 = new Map("src/main/resources/test.json");
        Map map2 = new Map("src/main/resources/test.json");
        Boolean lose = false;

        HashSet<Coord_Char> coord_char = map1.Hit(new Coord(4,4));
        map2.Damage(coord_char);

        System.out.println("Player 1");
        map1.PrintInConsole();

        System.out.println("\n\nPlayer 2");
        map2.PrintInConsole();
    }
}

