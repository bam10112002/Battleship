package org.example.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeMap;

class JsonMap {
    public Integer id;
    public HashSet<Ship> ships = new HashSet<Ship>();
}
public class Map {
    private Integer id;
    private TreeMap<Integer, Ship> ships = new TreeMap<Integer, Ship>();
    private Character[][] myMap    = new Character[10][10];
    private Character[][] otherMap = new Character[10][10];

    public Map() {
        ships = new TreeMap<Integer, Ship>();
        id = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                myMap[i][j] = otherMap[i][j] = ' ';
            }
        }
    }
    public Map(String fileName) {
        this();
        Load(fileName);
    }

    // вывод полей в терминал
    public void PrintInConsole() {
        System.out.print(" ");
        for (int j = 0; j < 10; j++) {
            System.out.print("  " + j + "  │");
        }
        System.out.print("\t\t\t");
        for (int j = 0; j < 10; j++) {
            System.out.print("  " + j + "  │");
        }
        System.out.println();

        for (int j =0; j < 10; j ++) {
            System.out.print("──────");
        }
        System.out.print("\t\t\t");
        for (int j =0; j < 10; j ++) {
            System.out.print("──────");
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(i + "│ ");
            for (int j = 0; j < 10; j++) {
                System.out.print(myMap[i][j] + "  │  ");
            }
            System.out.print("\t\t\t" + i + "│ ");
            for (int j = 0; j < 10; j++) {
                System.out.print(otherMap[i][j] + "  │  ");
            }
            System.out.println();

            for (int j =0; j < 10; j ++) {
                System.out.print("──────");
            }
            System.out.print("\t\t\t");
            for (int j =0; j < 10; j ++) {
                System.out.print("──────");
            }

            System.out.println();
        }
    }

    // полученние массива координат пораженных при выстреле по моему полю
    public HashSet<Coord_Char> Hit(@NonNull Coord coord) {
        HashSet<Coord_Char> res = new HashSet<Coord_Char>();
        if (myMap[coord.y][coord.x] >= '0' && myMap[coord.y][coord.x] <= '9') {
            res.add(new Coord_Char(coord.x, coord.y, 'X'));
            Ship ship = ships.get((int)(myMap[coord.y][coord.x] - '0'));
            if (ship == null)
                System.err.println("ERROR read ship, id = " + ((int)(myMap[coord.y][coord.x] - '0')));

            assert ship != null;
            ship.size--;

            // Если корабль уничтожен
            if (ship.size == 0) {
                for (Coord currentCoord : ship.coords)
                {
                    for (int i = -1; i <= 1; i++)
                        for (int j = -1;j <=1; j++)
                            if (currentCoord.x + i >= 0 && currentCoord.y + j >= 0 &&
                                    currentCoord.x + i <= 9 && currentCoord.y + j <= 9) {
                                if (j == 0 && i == 0)
                                    res.add(new Coord_Char(currentCoord.x + i, currentCoord.y + j, 'X'));
                                else
                                    res.add(new Coord_Char(currentCoord.x + i, currentCoord.y + j, '*'));
                            }
                }
                ships.remove((int)(myMap[coord.y][coord.x] - '0'));
                if (ships.size() == 0) {

                }
            }
        }
        else {
            res.add(new Coord_Char(coord.x, coord.y, '*'));
        }

        //нанесение резульатата на курту
        for (Coord_Char ch_coord : res) {
            myMap[ch_coord.y][ch_coord.x] = ch_coord.ch;
        }

        return res;
    }

    // отображение урона нанесенного мной по противнику
    public void Damage(@NonNull HashSet<Coord_Char> coords) {
        for (Coord_Char coord : coords)
        {
            otherMap[coord.y][coord.x] = coord.ch;
        }
    }

    // проверка проигграл ли я
    public Boolean isLose() {
        return ships.size() == 0;
    }
    public void Load(@NonNull String file_name) {
        String json = readFile(file_name);
        JsonMap jmap;
        if ((jmap = Deserialize(json)) == null) {
            System.err.println("ERROR in read json file");
            return;
        }

        id = jmap.id;
        for (Ship ship : jmap.ships) {
            ship.size = ship.coords.size();
            ships.put(ship.id, ship);
            for (Coord coord : ship.coords) {
                myMap[coord.y][coord.x] = (char)(ship.id + '0');
            }
        }
    }
    private static String readFile(@NonNull String fileName) {
        StringBuilder json = new StringBuilder();
        try {
            FileReader fr = new FileReader(fileName);
            int c;
            while ((c = fr.read()) != -1)
            {
                json.append((char) c);
            }
        }
        catch (IOException ex) {
            System.err.println(ex.toString());
            return null;
        }
        return json.toString();
    }
    private static JsonMap Deserialize(@NonNull String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonMap map = null;
            map = mapper.readValue(jsonString, JsonMap.class);
            return map;
        }
        catch (JsonProcessingException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
}