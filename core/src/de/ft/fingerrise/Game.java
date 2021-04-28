package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;


public class Game {

    public static boolean inMenu = true;

    static boolean f1Up = true;
    static boolean f2Up = false;
    static boolean disabledCurrentMovementF1 = false;
    static boolean disabledCurrentMovementF2 = false;


    public static void render(float DeltaTime, ShapeRenderer shapeRenderer, SpriteBatch batch) {


        FingerRise.f1.update();
        FingerRise.f2.update();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setAutoShapeType(true);

        if (!inMenu) {
            try {
                LevelManager.drawLevel(shapeRenderer,DeltaTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LevelManager.update(DeltaTime);

        } else {
            drawFingerPointMovingPath(shapeRenderer);
            fingerPointInMenuMovement(DeltaTime);
            //start Game
            if (FingerRise.f1.ready() && FingerRise.f2.ready() && disabledCurrentMovementF2 && disabledCurrentMovementF1 && inMenu) {
                LevelManager.loadLevel(LevelConfig.getCurrentLevel());
                LevelManager.setLevelProgress(-416);
                inMenu = false;
                LevelManager.levelStarted = true;
                disabledCurrentMovementF2 = false;
                disabledCurrentMovementF1 = false;

            }

        }



        FingerRise.f1.draw(shapeRenderer);
        FingerRise.f2.draw(shapeRenderer);

        shapeRenderer.end();

        if (!inMenu) {
            drawBackButton(batch);
        }

    }

    private static void drawBackButton(SpriteBatch batch) {

        batch.begin();
        batch.draw(FingerRise.back, 32, Gdx.graphics.getHeight() - (128 + 32), 128, 128);
        batch.end();

        if (Gdx.input.justTouched() && Collision.object(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 1, 1, 32, Gdx.graphics.getHeight() - (128 + 32), 128, 128)) {
            inMenu = true;
            disabledCurrentMovementF2 = false;
            disabledCurrentMovementF1 = false;
            f1Up = true;
            f2Up = false;

            Gdx.input.vibrate(50);


            FingerRise.f1.resetPosition();
            FingerRise.f2.resetPosition();

            FingerRise.f1.setAllowFingerDrag(true);
            FingerRise.f2.setAllowFingerDrag(true);

        }


    }

    private static void drawFingerPointMovingPath(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(FingerRise.f1.getInitX() - FingerPoint.getRadius() / 4 / 2, Gdx.graphics.getHeight() * 2f / 12f, FingerPoint.getRadius() / 4, Gdx.graphics.getHeight() * 6f / 12f - Gdx.graphics.getHeight() * 2f / 12f);
        shapeRenderer.rect(FingerRise.f2.getInitX() - FingerPoint.getRadius() / 4 / 2, Gdx.graphics.getHeight() * 2f / 12f, FingerPoint.getRadius() / 4, Gdx.graphics.getHeight() * 6f / 12f - Gdx.graphics.getHeight() * 2f / 12f);

    }

    private static void fingerPointInMenuMovement(float DeltaTime) {


        if (!FingerRise.f1.ready() && !disabledCurrentMovementF1) {

            if (FingerRise.f1.getY() > Gdx.graphics.getHeight() * 6f / 12f) {
                f1Up = false;
            }

            if (FingerRise.f1.getY() < Gdx.graphics.getHeight() * 2f / 12f) {
                f1Up = true;
            }

            if (!f1Up) {
                FingerRise.f1.setY(FingerRise.f1.getY() - 400 * DeltaTime);
            } else {
                FingerRise.f1.setY(FingerRise.f1.getY() + 400 * DeltaTime);
            }


        } else {
            disabledCurrentMovementF1 = true;
        }

        if (!FingerRise.f2.ready() && !disabledCurrentMovementF2) {

            if (FingerRise.f2.getY() > Gdx.graphics.getHeight() * 6f / 12f) {
                f2Up = false;
            }

            if (FingerRise.f2.getY() < Gdx.graphics.getHeight() * 2f / 12f) {
                f2Up = true;
            }

            if (!f2Up) {
                FingerRise.f2.setY(FingerRise.f2.getY() - 400 * DeltaTime);
            } else {
                FingerRise.f2.setY(FingerRise.f2.getY() + 400 * DeltaTime);
            }

        } else {
            disabledCurrentMovementF2 = true;
        }
    }


}
