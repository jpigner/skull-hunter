package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Skull extends Item {

	
	public Skull(PlayScreen screen, MapObject object) {
		super(screen, object);
		fixture.setUserData(this);
		setCategoryFilter(SKGame.getSkullBit());
			
	}

	public void use(Player player) {
		setCategoryFilter(SKGame.DESTROYED_BIT);
		getCell().setTile(null);
		SKGame.manager.get("Sounds/punkte.wav", Sound.class).play();
		Hud.addScore(10);
		
	}

}
