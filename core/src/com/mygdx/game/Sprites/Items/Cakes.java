package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Cakes extends Item {

	public Cakes(PlayScreen screen, MapObject object) {
		super(screen, object);
		fixture.setUserData(this);
		setCategoryFilter(SKGame.CAKE_BIT);
	}

	public void use(Player player) {
		setCategoryFilter(SKGame.getDestroyedBit());
		getCell().setTile(null);
		Hud.addLives(1);
		SKGame.manager.get("Sounds/live.wav", Sound.class).play();

	}

}
