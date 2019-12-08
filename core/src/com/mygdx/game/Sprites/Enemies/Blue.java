package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SKGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Blue extends Enemies{
	private float stateTime;
	public enum State { LEFT, RIGHT }
	public State currentState;
	public State previousState;
	private static Animation walkAnimation;
	private Array<TextureRegion> frames;

	
	public Blue(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		
		frames = new Array<TextureRegion>();
		for(int i = 0; i < 2; i ++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("bluehorn"), i * 16, 0, 16, 16));
		walkAnimation = new Animation(0.2f, frames);
		
		currentState = previousState = State.RIGHT;
		
		stateTime = 0;
		setBounds(getX(), getY(), 16 / SKGame.getPpm(), 16 / SKGame.getPpm());
		
		
	}

	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / SKGame.getPpm()); 
		
		fdef.filter.categoryBits = SKGame.ENEMYKILL_BIT;
		fdef.filter.maskBits = (short) ((short) (SKGame.getBorderBit() |
				SKGame.getGroundBit() | SKGame.getPlayerBit() | SKGame.getBorderJump()) | SKGame.FIRESHOOT_BIT | SKGame.FIRESHOOT2_BIT);

		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this); 
	}

	@Override
	public void update(float dt) {
		setRegion(getFrame(dt));
		stateTime += dt;	
		b2body.setLinearVelocity(velocity);
		setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
		setRegion(walkAnimation.getKeyFrame(stateTime, true));
	}
	
	public void draw(Batch batch) {		
		super.draw(batch);
	}
	

	private TextureRegion getFrame(float dt) {
		TextureRegion region = null;
		
		switch (currentState) {
		case RIGHT:
			region = walkAnimation.getKeyFrame(stateTime, true);
		case LEFT:
			region = walkAnimation.getKeyFrame(stateTime, true);
			break;
		}
		
		if(velocity.x > 0 && region.isFlipX() == true) {
			region.flip(true, false);
		}
		if(velocity.x < 0 && region.isFlipX() == false) {
			region.flip(true, false);
		}

        previousState = currentState;

        return region;
	}


	public void hit(Player player) {
	}
	
}
