package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class LevelConfig {
    public static final String LEVEL_1_1 = "1-1.json";
    public static final String LEVEL_1_2 = "1-2.json";
    public static final String LEVEL_1_3 = "1-3.json";
    public static int currentLevel = 0;
    public static int highestLevel = 0;
    public static ArrayList<String> level = new ArrayList<>();

    public static void init() {
        level.add(LEVEL_1_1);
        level.add(LEVEL_1_2);
        level.add(LEVEL_1_3);
    }

    public static FileHandle getCurrentLevel() {
        return Gdx.files.internal(level.get(currentLevel));
    }

    public static void nextLevel() {
        if (currentLevel < level.size() - 1) {
            currentLevel++;
            if (currentLevel > highestLevel) {
                highestLevel = currentLevel;
                Settings.save();
            }
        }
    }


}
