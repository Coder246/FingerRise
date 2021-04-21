package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.ft.fingerrise.JSON.JSONArray;
import de.ft.fingerrise.JSON.JSONException;
import de.ft.fingerrise.JSON.JSONObject;
import java.util.Arrays;



public class LevelManager {

    private static JSONArray obstacles;
    private static JSONObject levelJSON;
    private static float levelProgress = 0;
    public static boolean levelStarted = false;
    private static final Color tempColor = new Color();

    public static void loadLevel(FileHandle fh) throws JSONException {

        JSONObject level = new JSONObject(fh.readString());
        levelJSON = level;
        obstacles = level.getJSONArray("obstacles");
        for (int i = 0; i < obstacles.length(); i++) {
            obstacles.getJSONObject(i).put("current_rotation", obstacles.getJSONObject(i).has("degrees") ? obstacles.getJSONObject(i).getInt("degrees") : 0);
        }

        levelProgress = 0;

        levelStarted = false;


    }


    public static void update(float DeltaTime) throws JSONException {


        if (levelStarted) {
            levelProgress += 984*DeltaTime;
        } else if (FingerRise.f1.ready() && FingerRise.f2.ready()) {
            levelStarted = true;
        }

        updateDynamicPositions(DeltaTime);
    }



    public static void drawLevel(ShapeRenderer shapeRenderer) throws JSONException {

        boolean noObstaclesLeft = true;
        for (int i = 0; i < obstacles.length(); i++) {

            float x = obstacles.getJSONObject(i).getInt("x");
            float y = obstacles.getJSONObject(i).getInt("y");
            float w = obstacles.getJSONObject(i).getInt("w");
            float h = obstacles.getJSONObject(i).getInt("h");

            x = Gdx.graphics.getWidth() / 100f * x;
            y = Gdx.graphics.getHeight() / 1000f * y;
            w = Gdx.graphics.getWidth() / 100f * w;
            h = Gdx.graphics.getHeight() / 1000f * h;


            float degrees = obstacles.getJSONObject(i).getInt("current_rotation");

            if (Collision.objectwithrotation(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h, degrees, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0)) {
                shapeRenderer.setColor(Color.valueOf(obstacles.getJSONObject(i).getString("color"), tempColor));

                shapeRenderer.rect(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w / 2, h / 2, w, h, 1f, 1f, degrees);

                checkPlayerCollision(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h, degrees, FingerRise.f1);
                checkPlayerCollision(x, y + Gdx.graphics.getHeight() / 2f - levelProgress, w, h, degrees, FingerRise.f2);
                noObstaclesLeft = false;
            }
        }

        if (noObstaclesLeft) {
            nextLevel();
        }

    }

    private static void checkPlayerCollision(float x, float y, float w, float h, float rotation, FingerPoint f) {
        if (Collision.objectwithrotation(x, y, w, h, rotation, f.getX() - FingerPoint.getRadius(), f.getY() - FingerPoint.getRadius(), FingerPoint.getRadius() * 2, FingerPoint.getRadius() * 2, 0)) {
            resetGame();
            try {
                loadLevel(LevelConfig.getCurrentLevel());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static void resetGame() {

        FingerRise.f1.resetPosition();
        FingerRise.f2.resetPosition();
        Arrays.fill(FingerPoint.globalUsedFingers, false);

    }

    public static void nextLevel() {
        LevelConfig.nextLevel();
        try {
            loadLevel(LevelConfig.getCurrentLevel());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        levelProgress = -1200;

        levelStarted = true;

    }

    private static void updateDynamicPositions(float DeltaTime) throws JSONException {
        for (int i = 0; i < obstacles.length(); i++) {
            JSONObject currentObstacle = obstacles.getJSONObject(i);
            if (currentObstacle.has("degrees") && currentObstacle.has("rotating") && currentObstacle.getBoolean("rotating")) {


                currentObstacle.put("current_rotation", currentObstacle.getInt("current_rotation") + 622f/(float)currentObstacle.getInt("speed")*DeltaTime);


                if (currentObstacle.getInt("current_rotation") - currentObstacle.getInt("degrees") > 360) {
                        currentObstacle.put("current_rotation", currentObstacle.getInt("degrees"));
                    }



            }


            if (currentObstacle.has("reactOnPosition") && currentObstacle.getInt("reactOnPosition") < levelProgress) {

                if (currentObstacle.has("positionXAfterReact")) {
                    int x_diff = currentObstacle.getInt("x") - currentObstacle.getInt("positionXAfterReact");
                    if (Math.abs(x_diff) > 5) {
                        if (x_diff > 0) {
                            currentObstacle.put("x", currentObstacle.getInt("x") - 331.33f*DeltaTime);
                        } else {
                            currentObstacle.put("x", currentObstacle.getInt("x") + 331.33f*DeltaTime);

                        }
                    }

                }

                if (currentObstacle.has("positionYAfterReact")) {
                    int y_diff = currentObstacle.getInt("y") - currentObstacle.getInt("positionYAfterReact");
                    if (Math.abs(y_diff) > 5) {
                        if (y_diff > 0) {
                            currentObstacle.put("y", currentObstacle.getInt("y") - 331.33f*DeltaTime);
                        } else {
                            currentObstacle.put("y", currentObstacle.getInt("y") + 331.33f*DeltaTime);

                        }
                    }

                }


            }


        }

    }

    public static void setLevelProgress(float levelProgress) {
        LevelManager.levelProgress = levelProgress;
    }
}
