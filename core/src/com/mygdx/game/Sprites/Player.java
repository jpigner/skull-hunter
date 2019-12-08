package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.Shoot;
import com.mygdx.game.Sprites.Items.Shoot2;

public class Player extends Sprite {
	public static enum State {
		JUMPING, STANDING, RUNNING, DEAD, FALLING, SHOOTING
	}

	private State currentState;
	private State previousState;

	private World world;
	private Body b2body;
	private static BodyDef bdef;

	private float stateTimer;
	private boolean runningRight;

	private TextureRegion playerStand;
	private TextureRegion playerDead;
	private TextureRegion playerShoot;
	private Animation playerJump;
	private Animation playerRun;

	private Array<Shoot> shoots;
	private Array<Shoot2> shoots2;
	private PlayScreen screen;
	
	private static boolean shooting = false;
	
	public Player(PlayScreen screen) {
		this.screen = screen;
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		// ANIMATION FÜRS RENNEN
		for (int i = 0; i < 2; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), i * 16, 0, 16, 16));
		}
		playerRun = new Animation(0.3f, frames);
		frames.clear();

		// ANIMATION FÜRS SPRINGEN
		for (int i = 2; i < 4; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), i * 16, 0, 16, 16));
		}
		playerJump = new Animation(0.3f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("player"), 0, 0, 16, 16);
		playerDead = new TextureRegion(screen.getAtlas().findRegion("player"), 80, 0, 16, 16);
		playerShoot = new TextureRegion(screen.getAtlas().findRegion("player"), 64, 0, 15, 16);

		definePlayer();
		setBounds(0, 0, 16 / SKGame.getPpm(), 16 / SKGame.getPpm());
		setRegion(playerStand);

		shoots2 = new Array<Shoot2>();
		shoots = new Array<Shoot>();
	}

	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
		case SHOOTING:
			region = playerShoot;
			break;
		case DEAD:
			region = playerDead;
			break;
		case JUMPING:
			region = playerJump.getKeyFrame(stateTimer, true);
			break;
		case RUNNING:
			region = playerRun.getKeyFrame(stateTimer, true);
			break;
		case FALLING:
			region = playerStand;
			break;
		case STANDING:
		default:
			region = playerStand;
			break;
		}

		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}

		else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;

	}

	private State getState() {
		if (Hud.getWorldTimer() == 0 | Hud.getLives() == 0) {
			return State.DEAD;
		} else if (b2body.getLinearVelocity().y > 0
				|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			return State.JUMPING;
		} else if (b2body.getLinearVelocity().y < 0) {
			return State.FALLING;
		} else if (b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		} else if (shooting) {
			return State.SHOOTING;
		}

		else {
			return State.STANDING;
		}
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	private void definePlayer() {
		bdef = new BodyDef();
		bdef.position.set(16 / SKGame.getPpm(), 212 / SKGame.getPpm());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 / SKGame.getPpm(), 5 / SKGame.getPpm());

		fdef.filter.categoryBits = SKGame.getPlayerBit();
		fdef.filter.maskBits = ((short) (SKGame.getEnemyBit() | SKGame.getGroundBit() | SKGame.getSkullBit()
				| SKGame.getDoorBit() | SKGame.getKeyBit() | SKGame.getBorderJump() | SKGame.getEnemykillBit()
				| SKGame.getCakeBit()));

		fdef.shape = shape;
		b2body.setLinearDamping(1f);
		b2body.createFixture(fdef).setUserData(this);
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2,
				b2body.getPosition().y - getHeight() / 2 + 2 / SKGame.getPpm());
		setRegion(getFrame(dt));

		for (Shoot2 bullet2 : shoots2) {
			bullet2.update(dt);
			if (bullet2.isDestroyed()) {
				shoots2.removeValue(bullet2, true);
			}
		}

		for (Shoot bullet : shoots) {
			bullet.update(dt);
			shooting = true;
			if (bullet.isDestroyed()) {
				shoots.removeValue(bullet, true);
				shooting = false;
			}
				
		}
	}

	public Body getB2body() {
		return b2body;
	}

	public State getCurrentState() {
		return currentState;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	public void fireTwo() {
		shoots2.add(new Shoot2(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
		Hud.subStops(1);
	}

	public void fireOne() {
		shoots.add(new Shoot(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
		Hud.subSlides(1);
	}

	public void draw(Batch batch) {
		super.draw(batch);
		for (Shoot bullet : shoots) {
			bullet.draw(batch);
		}
		for (Shoot2 bullet2 : shoots2) {
			bullet2.draw(batch);
		}
	}
	

}
