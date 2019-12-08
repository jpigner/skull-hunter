package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SKGame;

public class Hud implements Disposable{
		
	private Stage stage;

	private Viewport viewport;
	private static Integer score;
	private static Integer lives;
	private static Integer stopBombs;
	private static int slideBullets;
	private static int worldTimer;
	private float timeCount;
	
	private Label scoreLabel;
	private Label liveLabel;
	private Label timeLabel;
	private Label fpsLabel;
	private Label stopBombsLabel;
	private Label slideBulletsLabel;
	private static Label stopBombsLabel_;
	private static Label slideBulletsLabel_;
	private static Label scoreLabel_;
	private static  Label liveLabel_;
	private static Label fpsLabel_;
	private Label timeLabel_;
	
	private float time;
	private static int frames, fps;
	
	public Hud(SpriteBatch sb) {
		worldTimer = 120;
		timeCount = 0;
		lives = 3;
		score = 0;
		stopBombs = 299;
		slideBullets = 299;

		viewport = new FitViewport(SKGame.getvWidth(), SKGame.getvHeight(), new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table(); 
		table.top();
		table.setFillParent(true);
		
		fpsLabel_ = new Label(String.format("%02d", fps), new Label.LabelStyle(new BitmapFont(), Color.WHITE)); 
		fpsLabel_.setFontScale(0.5f);
		timeLabel_ = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel_.setFontScale(0.5f);
		scoreLabel_ = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel_.setFontScale(0.5f);
		liveLabel_ = new Label(String.format("%01d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		liveLabel_.setFontScale(0.5f);
		stopBombsLabel_ = new Label(String.format("%02d", stopBombs), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		stopBombsLabel_.setFontScale(0.5f);
		slideBulletsLabel_ = new Label(String.format("%01d", slideBullets), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		slideBulletsLabel_.setFontScale(0.5f);
		
		fpsLabel = new Label("FPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE)); 
		fpsLabel.setFontScale(0.5f);
		timeLabel = new Label("ZEIT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel.setFontScale(0.5f);
		scoreLabel = new Label("PUNKTE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel.setFontScale(0.5f);
		liveLabel = new Label("LEBEN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		liveLabel.setFontScale(0.5f);
		slideBulletsLabel = new Label("SLIDES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		slideBulletsLabel.setFontScale(0.5f);
		stopBombsLabel = new Label("STOPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		stopBombsLabel.setFontScale(0.5f);
		
		table.add(scoreLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.add(fpsLabel).expandX().padTop(10);
		table.add(liveLabel).expandX().padTop(10);
		table.add(stopBombsLabel).expandX().padTop(10);
		table.add(slideBulletsLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel_);
		table.add(timeLabel_);
		table.add(fpsLabel_);
		table.add(liveLabel_);
		table.add(stopBombsLabel_);
		table.add(slideBulletsLabel_);
		table.row();
		stage.addActor(table);	
	}
	
	public void update(float dt) {
		frames++;
		time += dt;
		if(time > 1) {
			fps = frames;
			fpsLabel_.setText(String.format("%02d", fps));
			frames = 0;
			time = 0;
		}
				
		timeCount += dt;
		if(timeCount >= 1) 
			if(worldTimer > 0) {
				worldTimer--;
				timeLabel_.setText(String.format("%03d", worldTimer));
				timeCount = 0;	
			
			}		
		}
	
	public static Integer getStopBombs() {
		return stopBombs;
	}

	public static void setStopBombs(Integer stopBombs) {
		Hud.stopBombs = stopBombs;
	}

	public static Integer getSlideBullets() {
		return slideBullets;
	}

	public static void setSlideBullets(Integer slideBullets) {
		Hud.slideBullets = slideBullets;
	}

	public static int getWorldTimer() {
		return worldTimer;
	}
	
	public void dispose() {
		stage.dispose();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public static void addScore(int punkte) {
		score += punkte;
		scoreLabel_.setText(String.format("%03d", score));
	}
	
	public static int getScore() {
		return score;
	}
	
	public static int getLives() {
		return lives;
	}
	
	public static void subLives(int wert) {
		if (lives > 0)
		lives -= wert;
		liveLabel_.setText(String.format("%01d", lives));
	}
	
	public static void addLives(int wert) {
		lives += wert;
		liveLabel_.setText(String.format("%01d", lives));
	}
	
	public static void subSlides(int wert) {
		if (slideBullets > 0)
		slideBullets-= wert;
		slideBulletsLabel_.setText(String.format("%01d", slideBullets));
	}
	
	public static void subStops(int wert) {
		if (stopBombs > 0) {
			stopBombs -= wert;
			stopBombsLabel_.setText(String.format("%02d", stopBombs));
		}		
	}

	public static void setScore(Integer score) {
		Hud.score = score;
	}
	
	public static void setWorldTimer(int worldTimer) {
		Hud.worldTimer = worldTimer;
	}

}
