package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Door extends Item {
	private static TiledMapTileSet tileSet;
	private final int OPEN_DOOR = 55;
	private static boolean lvlUp = false;
	private static boolean win = false;

	public Door(PlayScreen screen, MapObject object) {
		super(screen, object);
		fixture.setUserData(this); // Referenz auf eigene Userdata
		setCategoryFilter(SKGame.DOOR_BIT); // Filter wird auf Door gesetzt
		tileSet = map.getTileSets().getTileSet("simples_pimples");
	}

	public void use(Player player) {
		if (Hud.getScore() == 200) {
			setCategoryFilter(SKGame.getDestroyedBit());
			getCell().setTile(tileSet.getTile(OPEN_DOOR));
			SKGame.getMusic().stop();
			SKGame.manager.get("Sounds/win.wav", Sound.class).play();
			lvlUp = true;

		}
	}

	public static boolean isLvlUp() {
		return lvlUp;
	}

	public static void setLvlUp(boolean lvlUp) {
		Door.lvlUp = lvlUp;
	}

	public static boolean isWin() {
		return win;
	}

}
