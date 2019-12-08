package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SKGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public abstract class Item {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	protected PlayScreen screen;
	protected MapObject object;
	
	public Item(PlayScreen screen, MapObject object) {
		this.object = object;
		this.screen = screen;
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.bounds = ((RectangleMapObject)object).getRectangle();
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth()/2) / SKGame.getPpm(), (bounds.getY() + bounds.getHeight()/2) / SKGame.getPpm());
		
		body = world.createBody(bdef);
		
		shape.setAsBox((bounds.getWidth() / 2) / SKGame.getPpm(), (bounds.getHeight() / 2) / SKGame.getPpm());
		fdef.shape = shape;
		fixture = body.createFixture(fdef);	
	}
	
	public abstract void use(Player player);
	
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
		return layer.getCell((int)(body.getPosition().x * SKGame.getPpm() / 16), (int)(body.getPosition().y * SKGame.getPpm() / 16));
	}
}
