package de.ft.fingerrise;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game {

    public static void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {


        FingerRise.f1.update();
        FingerRise.f2.update();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        try {
            LevelManager.drawLevel(shapeRenderer);
        }catch (Exception e) {
            e.printStackTrace();
        }

        FingerRise.f1.draw(shapeRenderer);
        FingerRise.f2.draw(shapeRenderer);

        shapeRenderer.end();
    }

}
