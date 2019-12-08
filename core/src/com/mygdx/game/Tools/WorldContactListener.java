package com.mygdx.game.Tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Enemies.Enemies;
import com.mygdx.game.Sprites.Items.Item;
public class WorldContactListener implements ContactListener{
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
	
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		switch (cDef) {
		case SKGame.PLAYER_BIT | SKGame.GROUND_BIT:
			PlayScreen.setJump(false);
			break;
		case SKGame.PLAYER_BIT | SKGame.KEY_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.PLAYER_BIT) {
				((Item)fixB.getUserData()).use((Player) fixA.getUserData());
			} else {
				((Item)fixA.getUserData()).use((Player) fixB.getUserData());
			}
		case SKGame.PLAYER_BIT | SKGame.SKULL_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.PLAYER_BIT) {
				((Item)fixB.getUserData()).use((Player) fixA.getUserData());
			} else {
				((Item)fixA.getUserData()).use((Player) fixB.getUserData());
			}
		break;
		case SKGame.PLAYER_BIT | SKGame.CAKE_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.PLAYER_BIT) {
				((Item)fixB.getUserData()).use((Player) fixA.getUserData());
			} else {
				((Item)fixA.getUserData()).use((Player) fixB.getUserData());
			}
		break;
		case SKGame.PLAYER_BIT | SKGame.DOOR_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.PLAYER_BIT) {
				((Item)fixB.getUserData()).use((Player) fixA.getUserData());
			} else {
				((Item)fixA.getUserData()).use((Player) fixB.getUserData());
			}
		break;
		case SKGame.ENEMY_BIT | SKGame.BORDER_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.ENEMY_BIT) {
				((Enemies)fixA.getUserData()).reverseVelocity(true, false);
				
			} else {
				((Enemies)fixB.getUserData()).reverseVelocity(true, false);
			}
		break;
		case SKGame.ENEMYKILL_BIT | SKGame.BORDER_BIT:
			if(fixA.getFilterData().categoryBits == SKGame.ENEMYKILL_BIT) {
				((Enemies)fixA.getUserData()).reverseVelocity(true, false);
				
			} else {
				((Enemies)fixB.getUserData()).reverseVelocity(true, false);
			}
		break;
		case SKGame.ENEMYKILL_BIT | SKGame.BORDER_JUMP:
			if(fixA.getFilterData().categoryBits == SKGame.ENEMY_BIT) {
				((Enemies)fixA.getUserData()).reverseVelocity(true, false);
				
			} else {
				((Enemies)fixB.getUserData()).reverseVelocity(true, false);
			}
		break;
		case SKGame.ENEMYKILL_BIT | SKGame.PLAYER_BIT:
			Hud.subLives(1);
			SKGame.manager.get("Sounds/hurt.wav", Sound.class).play();
			if(Hud.getLives() == 0) {
				SKGame.manager.get("Sounds/dead.wav", Sound.class).play();
			}
		break;
		case SKGame.PLAYER_BIT | SKGame.ENEMY_BIT:
			Hud.subLives(1);
			SKGame.manager.get("Sounds/hurt.wav", Sound.class).play();
			if(Hud.getLives() == 0) {
				SKGame.manager.get("Sounds/dead.wav", Sound.class).play();
			}
		break;
		case SKGame.FIRESHOOT_BIT | SKGame.ENEMY_BIT:
			SKGame.manager.get("Sounds/enemyhit.ogg", Sound.class).play();
		break;
		case SKGame.FIRESHOOT2_BIT | SKGame.ENEMY_BIT:
			SKGame.manager.get("Sounds/bomb.wav", Sound.class).play();
		break;
		case SKGame.FIRESHOOT_BIT | SKGame.ENEMYKILL_BIT:
			SKGame.manager.get("Sounds/hit.wav", Sound.class).play();
		break;
		case SKGame.FIRESHOOT2_BIT | SKGame.ENEMYKILL_BIT:
			SKGame.manager.get("Sounds/bomb.wav", Sound.class).play();
		break;
		}
		
	}

	@Override
	public void endContact(Contact arg0) {
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
	}

}
