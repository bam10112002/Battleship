package org.example;

import org.example.map.Coord;
import org.example.map.Coord_Char;
import org.example.map.Map;
import org.example.web.Web;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Scanner;


interface FunctionPointer {
    boolean method(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc);
}

public class Player {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Web web;
        System.out.print("Input: ");
        int ind = sc.nextInt();
        if (ind == 0)
            web = new Web(Web.Type.SERVER ,2344, "localhost");
        else
            web = new Web(Web.Type.CLIENT ,2344, "localhost");


        Map map = new Map("src/main/resources/test.json");
        Some some = new Some();

        FunctionPointer[] functionPointersArray = new FunctionPointer[4];
        functionPointersArray[0] = some::MyStep;
        functionPointersArray[1] = some::Print;
        functionPointersArray[2] = some::OtherStep;
        functionPointersArray[3] = some::Print;

        functionPointersArray[1].method(map,web,sc);
        while (functionPointersArray[ind].method(map,web,sc)) {
            ind = (ind + 1) % 4;
        }
    }

    public static boolean MyStep(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
        // отправление моего хода
        System.out.println("Input coord: x, y");
        int x = sc.nextInt();
        int y = sc.nextInt();
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
    public static boolean OtherStep(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
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
    public static boolean Print(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
        // вывод результата
        System.out.println("\n\n\n\n\n");
        map.PrintInConsole();
        System.out.println("\n\n");
        return true;
    }
}

class Some
{
    public boolean MyStep(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
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
    public boolean OtherStep(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
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
    public boolean Print(@NotNull Map map, @NotNull Web web, @NotNull Scanner sc) {
        // вывод результата
        System.out.println("\n\n\n\n\n");
        map.PrintInConsole();
        System.out.println("\n\n");
        return true;
    }
}
