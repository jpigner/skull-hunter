package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public abstract class Enemies extends Sprite{
	protected World world;
	protected PlayScreen screen;
	public Body b2body;

	public Vector2 velocity;
	
	public Enemies(PlayScreen screen, float x, float y) {
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);
		defineEnemy();
		velocity = new Vector2(1, 0);
		b2body.setActive(true); 
	}

	protected abstract void defineEnemy();
	
	public abstract void update(float dt);
	
	public abstract void hit(Player player);
	
	public void reverseVelocity(boolean x, boolean y) {
		if(x)
			velocity.x = -velocity.x;
			velocity.y = -velocity.y;
	}
	
	public Body getB2body() {
		return b2body;
	}

	public void setB2body(Body b2body) {
		this.b2body = b2body;
	}
	
}
