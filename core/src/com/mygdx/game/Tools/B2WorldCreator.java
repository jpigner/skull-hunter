package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SKGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Blue;
import com.mygdx.game.Sprites.Enemies.Fireball;
import com.mygdx.game.Sprites.Enemies.Red;
import com.mygdx.game.Sprites.Items.Cakes;
import com.mygdx.game.Sprites.Items.Door;
import com.mygdx.game.Sprites.Items.Key;
import com.mygdx.game.Sprites.Items.Skull;

public class B2WorldCreator {
	private static Array<Red> reds;
	private static Array<Blue> blues;
	private static Array<Fireball> fires;
	
	private static Body body;
	
	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		// Border wegen Jumpboost
		for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// EIGENSCHAFTEN DES BODYS
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / SKGame.getPpm(),
					(rect.getY() + rect.getHeight() / 2) / SKGame.getPpm());

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / SKGame.getPpm(), (rect.getHeight() / 2) / SKGame.getPpm());
			fdef.shape = shape;
			fdef.filter.categoryBits = SKGame.getBorderJump();
			body.createFixture(fdef).setUserData("borderboden");
		}

		// Bordergegner
		for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// EIGENSCHAFTEN DES BODYS
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / SKGame.getPpm(),
					(rect.getY() + rect.getHeight() / 2) / SKGame.getPpm());

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / SKGame.getPpm(), (rect.getHeight() / 2) / SKGame.getPpm());
			fdef.shape = shape;
			fdef.filter.categoryBits = SKGame.getBorderBit();
			body.createFixture(fdef).setUserData("border");
		}

		// Boden
		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// EIGENSCHAFTEN DES BODYS
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / SKGame.getPpm(),
					(rect.getY() + rect.getHeight() / 2) / SKGame.getPpm());

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / SKGame.getPpm(), (rect.getHeight() / 2) / SKGame.getPpm());
			fdef.shape = shape;
			fdef.filter.categoryBits = SKGame.getGroundBit();
			body.createFixture(fdef).setUserData("boden");
		}

		// Skulls
		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			new Skull(screen, object);
		}

		// Keys
		for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			new Key(screen, object);
		}

		// Doors
		for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			new Door(screen, object);
		}

		// Kuchen
		for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
			new Cakes(screen, object);
		}

		// Gegner Reds
		reds = new Array<Red>();
		for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			reds.add(new Red(screen, rect.getX() / SKGame.getPpm(), rect.getY() / SKGame.getPpm()));
		}

		// Gegner Blues
		blues = new Array<Blue>();
		for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			blues.add(new Blue(screen, rect.getX() / SKGame.getPpm(), rect.getY() / SKGame.getPpm()));
		}

		// Gegner Fires
		fires = new Array<Fireball>();
		for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			fires.add(new Fireball(screen, rect.getX() / SKGame.getPpm(), rect.getY() / SKGame.getPpm()));
		}

	}

	public static Body getBody() {
		return body;
	}

	public Array<Red> getReds() {
		return reds;
	}

	public Array<Blue> getBlues() {
		return blues;
	}

	public Array<Fireball> getFireballs() {
		return fires;
	}

}
