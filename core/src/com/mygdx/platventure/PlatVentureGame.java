package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.ecrans.EcranPresentation;

public class PlatVentureGame extends Game {
	private SpriteBatch listeAff;
	private Niveau niveau;
	private World world;

	@Override
	public void create() {
		listeAff = new SpriteBatch();
		setScreen(new EcranPresentation(this));
		world = new World(new Vector2(0f, -10f), true);
	}

	@Override
	public void dispose() {
		listeAff.dispose();
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public SpriteBatch getListeAff() {
		return listeAff;
	}

	public int getNiveauLargeur() {
		return niveau.getLargeur();
	}

	public int getNiveauHauteur() {
		return niveau.getHauteur();
	}

	public World getWorld() {
		return world;
	}
}
