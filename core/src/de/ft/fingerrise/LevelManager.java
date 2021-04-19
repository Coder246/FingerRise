package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class LevelManager {

    private static JSONArray obstacles;
    private static float levelProgress = 0;
    private static final int delay = 4;
    private static Timer timer = null;
    private static boolean levelStarted = false;

    public static void loadLevel(FileHandle fh) {

        JSONObject level = new JSONObject(fh.readString());
        obstacles = level.getJSONArray("obstacles");

        levelProgress = 0;

        if (timer != null) {
            timer.cancel();
        }

        levelStarted = false;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, delay);


    }

    private static void update() {
        if(levelStarted) {
            levelProgress += 2;
        }else if(FingerRise.f1.ready()&&FingerRise.f2.ready()){
            levelStarted = true;
        }
    }

    public static void drawLevel(ShapeRenderer shapeRenderer) {

        for (int i = 0; i < obstacles.length(); i++) {


            float x = obstacles.getJSONObject(i).getInt("x");
            float y = obstacles.getJSONObject(i).getInt("y");
            float w = obstacles.getJSONObject(i).getInt("w");
            float h = obstacles.getJSONObject(i).getInt("h");

            x = Gdx.graphics.getWidth() / 100f * x;
            y = Gdx.graphics.getHeight() / 1000f * y;
            w = Gdx.graphics.getWidth() / 100f * w;
            h = Gdx.graphics.getHeight() / 1000f * h;

            float degrees = 0;
            if(obstacles.getJSONObject(i).has("degrees")) degrees = obstacles.getJSONObject(i).getInt("degrees");

            if (Collision.object(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
                shapeRenderer.setColor(Color.valueOf(obstacles.getJSONObject(i).getString("color")));

                shapeRenderer.rect(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w/2, h/2,w,h,1f,1f,degrees);

                checkPlayerCollision(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h, FingerRise.f1);
                checkPlayerCollision(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h, FingerRise.f2);
            }

        }
    }

    private static void checkPlayerCollision(float x, float y, float w, float h, FingerPoint f) {
        if (Collision.object(x, y, w, h, f.getX() - FingerPoint.getRadius(), f.getY() - FingerPoint.getRadius(), FingerPoint.getRadius() * 2, FingerPoint.getRadius() * 2)) {
            resetGame();
            loadLevel(Gdx.files.internal("1-1.json"));

        }

    }

    public static void resetGame() {

        FingerRise.f1.resetPosition();
        FingerRise.f2.resetPosition();
        Arrays.fill(FingerPoint.globalUsedFingers, false);

    }


}
