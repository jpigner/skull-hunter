package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SKGame;

public class StartScreen implements Screen{
	private Viewport viewport;
	private Stage stage;
	private Game game;

	
	public StartScreen(Game game) {
		this.game = game;
		viewport = new FitViewport(SKGame.getvWidth(), SKGame.getvWidth(), new OrthographicCamera());
		stage = new Stage(viewport, ((SKGame) game).batch);
		
		Texture texture = new Texture(Gdx.files.internal("start.png"));
		
		Image screenBild = new Image(texture);
		screenBild.setPosition(0,0);
		stage.addActor(screenBild);
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float arg0) {
		if(Gdx.input.justTouched()) {
			PlayScreen.setPause(false);
			game.setScreen(( (SKGame) Gdx.app.getApplicationListener() ) .getPlayscreen());
			SKGame.manager.get("Sounds/music.mp3", Music.class).play();
					
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	public void resize(int arg0, int arg1) {
	}

	public void resume() {
	}

	public void show() {
	}

}