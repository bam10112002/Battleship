package org.example.game;

import lombok.NonNull;
import org.example.map.Coord;
import org.example.map.Coord_Char;
import org.example.map.Map;
import org.example.web.Web;

import java.util.HashSet;
import java.util.Scanner;

public class Game
{
    public boolean MyStep(@NonNull Map map, @NonNull Web web, @NonNull Scanner sc) {
        // отправление моего хода
        int x, y;
        while (true) {
            System.out.println("Input coord: x, y");
            x = sc.nextInt();
            y = sc.nextInt();
            if (x >= 0 && x <= 9 && y >=0 && y <= 9)
                break;
        }
        web.SendObject(new Coord(x, y));

        // получение результата хода
        HashSet<Coord_Char> damage = (HashSet<Coord_Char>) web.ReadObject();
        if ((Boolean) web.ReadObject()) {
            System.out.println("YOU WIN!!!");
            return false;
        }
        map.Damage(damage);
        return true;
    }
    public boolean OtherStep(@NonNull Map map, @NonNull Web web, @NonNull Scanner sc) {
        //получение хода противника
        System.out.println("Wait other player...");
        Coord coord = (Coord) web.ReadObject();
        HashSet<Coord_Char> data = map.Hit(coord);

        //отправлние результаты хода противника
        web.SendObject(data);
        web.SendObject(map.isLose());
        if (map.isLose()) {
            System.out.println("YOU LOSE((((");
            return false;
        }
        return true;
    }
    public boolean Print(@NonNull Map map, @NonNull Web web, @NonNull Scanner sc) {
        // вывод результата
        System.out.println("\n\n\n\n\n");
        map.PrintInConsole();
        System.out.println("\n\n");
        return true;
    }
}
