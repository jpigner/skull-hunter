package com.mygdx.game.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.SKGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Key extends Item {

	public Key(PlayScreen screen, MapObject object) {
		super(screen, object);
		fixture.setUserData(this);
		setCategoryFilter(SKGame.getKeyBit());
	}

	public void use(Player player) {
		if (Hud.getScore() == 100) {
			setCategoryFilter(SKGame.getDestroyedBit());
			getCell().setTile(null);
			Hud.addScore(100);
			SKGame.manager.get("Sounds/key.wav", Sound.class).play();

		}

	}
}