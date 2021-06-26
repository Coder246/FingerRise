package de.ft.fingerrise;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class FingerRise extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	public static FingerPoint f1;
	public static FingerPoint f2;
	public static Texture back;
	public static Sprite arrow_up;
	public static BitmapFont titleFont;
	public static BitmapFont font;
	public static BitmapFont smallFont;
	public static GlyphLayout glyphLayout;
	public static Sound click;
	public static Preferences settings;

	@Override
	public void create () {
		back = new Texture("back.png");
		arrow_up = new Sprite( new Texture("arrow_up.png"));
		batch = new SpriteBatch();
		click = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
		shapeRenderer = new ShapeRenderer();
		glyphLayout = new GlyphLayout();

		settings = Gdx.app.getPreferences("settings");
		Settings.init();

		f1 = new FingerPoint(Gdx.graphics.getWidth()/6f*2,Gdx.graphics.getHeight()*4f/12f,new Color(1,0,0,1));
		f2 = new FingerPoint(Gdx.graphics.getWidth()/6f*4,Gdx.graphics.getHeight()*4f/12f,new Color(0,1,0,1));

		titleFont = buildFont("titlefont.ttf",150,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");
		font = buildFont("font.ttf",110,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");
		smallFont = buildFont("font.ttf",85,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*");
		LevelConfig.init();

		System.out.println(LevelConfig.currentLevel);
		LevelManager.loadLevel(LevelConfig.getCurrentLevel());



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		Game.render(Gdx.graphics.getDeltaTime(),shapeRenderer,batch);


		batch.begin();
		if(Game.inMenu) {
			glyphLayout.setText(titleFont, "FingerRise");
			titleFont.draw(batch, "FingerRise", Gdx.graphics.getWidth() / 2f - glyphLayout.width / 2, Gdx.graphics.getHeight() / 10f * 9f);
			glyphLayout.setText(font, "Catch Circles to Start!");
			font.draw(batch, "Catch Circles to Start!", Gdx.graphics.getWidth() / 2f - glyphLayout.width / 2, Gdx.graphics.getHeight() / 10f * 8f);
		}
		batch.end();




	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}

	private static BitmapFont buildFont(String filename, int size, String characters) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		parameter.characters = characters;
		parameter.kerning = true;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		BitmapFont font = generator.generateFont(parameter);
		font.getData().markupEnabled = true;
		generator.dispose();
		return font;
	}
}
