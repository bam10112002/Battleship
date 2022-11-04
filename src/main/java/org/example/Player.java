package org.example;

import lombok.NonNull;
import org.example.game.Game;
import org.example.game.Step;
import org.example.map.Coord;
import org.example.map.Coord_Char;
import org.example.map.Map;
import org.example.web.Web;

import java.util.HashSet;
import java.util.Scanner;

public class Player {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Web web;
        System.out.print("Input your step 1/2: ");
        int ind = scanner.nextInt();

        if (ind == 1) {
            System.out.println("Wait....");
            web = new Web(Web.Type.SERVER, 2344, "localhost");
        }
        else {
            web = new Web(Web.Type.CLIENT, 2344, "localhost");
        }

        Map map = new Map("src/main/resources/maps/test.json");
        Game game = new Game();
        Step[] steps = new Step[4];
        steps[0] = game::MyStep;
        steps[1] = game::Print;
        steps[2] = game::OtherStep;
        steps[3] = game::Print;


        ind = (ind * 2 + 3) % 4;
        while (steps[ind].execute(map,web, scanner)) {
            ind = (ind + 1) % 4;
        }
    }
}
