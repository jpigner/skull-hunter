package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SKGame;
import com.mygdx.game.Screens.PlayScreen;

public class Shoot2 extends Sprite{
	PlayScreen screen;
	World world;
	Array<TextureRegion> frames;
	Animation fireAnimation;
	float stateTime;
	boolean destroyed;
	boolean setToDestroy;
	boolean fireRight;
	Body b2body;

	public Shoot2(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for(int i = 2; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bullet"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 10 / SKGame.getPpm(), 10 / SKGame.getPpm());
        defineWeapon();
    }

	public void defineWeapon() {
		 BodyDef bdef = new BodyDef();
	        bdef.position.set(fireRight ? getX() + 6 / SKGame.getPpm() : getX() - 6 / SKGame.getPpm(), getY());
	        bdef.type = BodyDef.BodyType.StaticBody;
	        if(!world.isLocked())
	        b2body = world.createBody(bdef);

	        FixtureDef fdef = new FixtureDef();
	        CircleShape shape = new CircleShape();
	        shape.setRadius(3 / SKGame.getPpm());
	        fdef.filter.categoryBits = SKGame.FIRESHOOT2_BIT;
	        fdef.filter.maskBits = SKGame.GROUND_BIT |
	        		SKGame.ENEMY_BIT |
	        		SKGame.ENEMYKILL_BIT;

	        fdef.shape = shape;
	        fdef.restitution = 0;
	        fdef.friction = 0;
	        b2body.createFixture(fdef).setUserData(this);
	        b2body.setLinearVelocity(new Vector2(fireRight ? 3 : -3, 0));
	}
	
	 public void update(float dt){
	        stateTime += dt;
	        setRegion(fireAnimation.getKeyFrame(stateTime, true));
	        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
	        if((stateTime > 3 || setToDestroy) && !destroyed) {
	            world.destroyBody(b2body);
	            destroyed = true;
	        }
	        if(b2body.getLinearVelocity().y > 2f)
	            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
	        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
	            setToDestroy();
	    }
	 
	 public void setToDestroy(){
	        setToDestroy = true;
	    }

	    public boolean isDestroyed(){
	        return destroyed;
	    }

}

