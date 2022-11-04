package org.example.game;

import lombok.NonNull;
import org.example.map.Map;
import org.example.web.Web;

import java.util.Scanner;

public interface Step {
    boolean execute(@NonNull Map map, @NonNull Web web, @NonNull Scanner sc);
}
