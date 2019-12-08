package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StartScreen;

public class SKGame extends Game {
	public SpriteBatch batch;
	private PlayScreen playscreen;
	//private StartScreen startscreen;
	public static AssetManager manager;

	public static final short DESTROYED_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short SKULL_BIT = 4;
	public static final short KEY_BIT = 8;
	public static final short DOOR_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short BORDER_BIT = 64;
	public static final short BORDER_JUMP = 128;
	public static final short ENEMYKILL_BIT = 256;
	public static final short CAKE_BIT = 512;
	public static final short FIRESHOOT_BIT = 1024;
	public static final short FIRESHOOT2_BIT = 2048;
	public static final float PPM = 100;
	private static final int V_WIDTH = 320;
	private static final int V_HEIGHT = 190;

	private static Music music;

	public void create() {
		batch = new SpriteBatch();
		playscreen = new PlayScreen(this);
		//setScreen(playscreen);
		setScreen(new StartScreen(this));
		

		manager = new AssetManager();
		manager.load("Sounds/punkte.wav", Sound.class);
		manager.load("Sounds/hit.wav", Sound.class);
		manager.load("Sounds/hurt.wav", Sound.class);
		manager.load("Sounds/key.wav", Sound.class);
		manager.load("Sounds/live.wav", Sound.class);
		manager.load("Sounds/dead.wav", Sound.class);
		manager.load("Sounds/win.wav", Sound.class);
		manager.load("Sounds/enemyhit.ogg", Sound.class);
		manager.load("Sounds/bomb.wav", Sound.class);
		manager.load("Sounds/wingame.mp3", Music.class);
		manager.load("Sounds/music.mp3", Music.class);
		manager.finishLoading();
		
		music = manager.get("Sounds/music.mp3", Music.class);
		music.setVolume(0.1f);
		music.setLooping(true);

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	public static int getvWidth() {
		return V_WIDTH;
	}

	public static int getvHeight() {
		return V_HEIGHT;
	}

	public static short getGroundBit() {
		return GROUND_BIT;
	}

	public static short getPlayerBit() {
		return PLAYER_BIT;
	}

	public static short getSkullBit() {
		return SKULL_BIT;
	}

	public static short getKeyBit() {
		return KEY_BIT;
	}

	public static short getDoorBit() {
		return DOOR_BIT;
	}

	public static short getEnemyBit() {
		return ENEMY_BIT;
	}

	public static float getPpm() {
		return PPM;
	}

	public static short getDestroyedBit() {
		return DESTROYED_BIT;
	}

	public static short getBorderBit() {
		return BORDER_BIT;
	}

	public PlayScreen getPlayscreen() {
		return playscreen;
	}

	public static short getBorderJump() {
		return BORDER_JUMP;
	}

	public static short getEnemykillBit() {
		return ENEMYKILL_BIT;
	}

	public static short getCakeBit() {
		return CAKE_BIT;
	}

	public static Music getMusic() {
		return music;
	}


}
