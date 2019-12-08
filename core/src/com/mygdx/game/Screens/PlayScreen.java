package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Player.State;
import com.mygdx.game.Sprites.Enemies.Enemies;
import com.mygdx.game.Sprites.Items.Door;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

public class PlayScreen implements Screen {
	private TextureAtlas atlas;

	private SKGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;
	private static World world;

	private static Player player;

	private static boolean pause = false;
	private static boolean jump = false;
	private int lvl = 1;
	
	public PlayScreen(SKGame game) {
		this.game = game;

		maploader = new TmxMapLoader();
		InitLevel(lvl);
		atlas = new TextureAtlas("player_enemies.pack");
		world = new World(new Vector2(0, -10f), true);

		creator = new B2WorldCreator(this);
		b2dr = new Box2DDebugRenderer();

		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(SKGame.getvWidth() / SKGame.getPpm(), SKGame.getvHeight() / SKGame.getPpm(),
				gamecam);
		hud = new Hud(game.batch);
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		player = new Player(this);
		world.setContactListener(new WorldContactListener());
				
	}

	public void InitLevel(int level) {
		map = maploader.load("neueMap" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / SKGame.getPpm());

	}

	public void clearWorld() {
		if (world != null) {
			world.dispose();
		}
		world = new World(new Vector2(0, -10f), true);
		creator = new B2WorldCreator(this);
	}

	public void resetMap() {
		map.dispose();
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		PlayScreen.player = player;
	}

	public static void setJump(Boolean jump) {
		PlayScreen.jump = jump;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	private void handleInput(float dt) {

		if (player.getCurrentState() != Player.State.DEAD) {

			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				pause = true;
			}

			if (!jump) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
					player.getB2body().applyLinearImpulse(new Vector2(0, 6f), player.getB2body().getWorldCenter(),
							true);
					jump = true;
				}

			}

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				player.getB2body().applyLinearImpulse(new Vector2(0.1f / 2, 0), player.getB2body().getWorldCenter(),
						true);
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				player.getB2body().applyLinearImpulse(new Vector2(-0.1f / 2, 0), player.getB2body().getWorldCenter(),
						true);
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (Hud.getSlideBullets() > 0)
					player.fireOne();
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				if (Hud.getStopBombs() > 0)
					player.fireTwo();
			}

		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput(delta);

		update(delta);
		renderer.render();

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		for (Enemies red : creator.getReds()) {
			red.draw(game.batch);
		}
		for (Enemies blues : creator.getBlues()) {
			blues.draw(game.batch);
		}
		for (Enemies fires : creator.getFireballs()) {
			fires.draw(game.batch);
		}
		game.batch.end();

		game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
		hud.getStage().draw();

		if (player.getCurrentState() != Player.State.DEAD) {
			gamecam.position.y = player.getB2body().getPosition().y;
		}

		if (gameOver()) {
			game.setScreen(new GameOverScreen(game));
			SKGame.manager.get("Sounds/music.mp3", Music.class).stop();
			dispose();
		}

		if (lvl == 7) {
			game.setScreen(new GameSucces(game));
			Door.setLvlUp(false);
			SKGame.manager.get("Sounds/music.mp3", Music.class).stop();
			SKGame.manager.get("Sounds/wingame.mp3", Music.class).play();
			map.dispose();
		}

		if (Door.isLvlUp() && player.getStateTimer() > 1f) {
			lvl++;
			Door.setLvlUp(false);
			Hud.setWorldTimer(120);
			Hud.setScore(0);
			game.setScreen(this);
			clearWorld();
			
			resetMap();
			
			SKGame.manager.get("Sounds/music.mp3", Music.class).play();
			SKGame.manager.get("Sounds/music.mp3", Music.class).setLooping(true);
			
			InitLevel(lvl);
			player = new Player(this);
			world.setContactListener(new WorldContactListener());
			
		}

		if (pause) {
			SKGame.manager.get("Sounds/music.mp3", Music.class).stop();
			game.setScreen(new PauseScreen(game));
		}

	}

	public boolean gameOver() {
		if (player.getCurrentState() == State.DEAD && player.getStateTimer() > 2) {
			return true;
		}
		return false;
	}

	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
	
	public void update(float dt) {
		handleInput(dt);
		world.step(1 / 60f, 6, 2);
		player.update(dt);
		for (Enemies red : creator.getReds()) {
			red.update(dt);
		}
		for (Enemies blues : creator.getBlues()) {
			blues.update(dt);
		}
		for (Enemies fires : creator.getFireballs()) {
			fires.update(dt);
		}
		hud.update(dt);
		gamecam.update();
		renderer.setView(gamecam);
		
	}
	
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public TiledMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public static boolean isPause() {
		return pause;
	}

	public static void setPause(boolean pause) {
		PlayScreen.pause = pause;
	}
	
}
