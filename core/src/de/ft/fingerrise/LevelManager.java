package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.json.JSONArray;
import org.json.JSONObject;


public class LevelManager {

    private static JSONArray obstacles;
    private static float levelProgress = 0;

    public static void loadLevel(FileHandle fh) {

        JSONObject level = new JSONObject(fh.readString());
        obstacles = level.getJSONArray("obstacles");

        levelProgress = 0;


    }

    public static void drawLevel(ShapeRenderer shapeRenderer) {
        for(int i=0;i<obstacles.length();i++) {
            float x = obstacles.getJSONObject(i).getInt("x");
            float y = obstacles.getJSONObject(i).getInt("y");
            float w = obstacles.getJSONObject(i).getInt("w");
            float h = obstacles.getJSONObject(i).getInt("h");

            if(Collision.object(x,y,w,h,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight())) {
                shapeRenderer.rect(x,y+Gdx.graphics.getHeight()/2f+levelProgress,w,h);
            }

        }
    }




}
