package org.example;

import org.example.map.Coord;
import org.example.map.Coord_Char;
import org.example.map.Map;
import org.example.web.Web;

import java.util.HashSet;
import java.util.Scanner;

public class Player2 {
    public static void main(String[] args) {
        Map map = new Map("src/main/resources/test.json");
        Web web = new Web(Web.Type.CLIENT ,2344, "localhost");
        Scanner sc = new Scanner(System.in);
        int x,y;
        System.out.print("\033[H\033[J");

        while (true) {
            //получение хода противника
            System.out.println("Wait other player...");
            Coord coord = (Coord) web.ReadObject();
            HashSet<Coord_Char> data = map.Hit(coord);

            //отправляю результаты хода противника
            web.SendObject(data);
            web.SendObject(map.isLose());
            if (map.isLose()) {
                System.out.println("YOU LOSE((((");
                break;
            }

            // отправление моего хода
            System.out.println("Input coord: x, y");
            x = sc.nextInt();
            y = sc.nextInt();
            web.SendObject(new Coord(x, y));

            // получение результата хода
            HashSet<Coord_Char> damage = (HashSet<Coord_Char>) web.ReadObject();
            if ((Boolean)web.ReadObject()) {
                System.out.println("YOU WIN!!!");
                break;
            }
            map.Damage(damage);

            // вывод результата
            map.PrintInConsole();
            System.out.println("\n\n");
        }
    }
}
