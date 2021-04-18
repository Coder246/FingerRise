package de.ft.fingerrise;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FingerRise extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	FingerPoint f1;
	FingerPoint f2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		f1 = new FingerPoint(Gdx.graphics.getWidth()/6f*2,Gdx.graphics.getHeight()/10f*2,new Color(1,0,0,1));
		f2 = new FingerPoint(Gdx.graphics.getWidth()/6f*4,Gdx.graphics.getHeight()/10f*2,new Color(0,1,0,1));

		LevelManager.loadLevel(Gdx.files.internal("1-1.json"));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		f1.update();
		f2.update();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		LevelManager.drawLevel(shapeRenderer);

		f1.draw(shapeRenderer);
		f2.draw(shapeRenderer);

		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
