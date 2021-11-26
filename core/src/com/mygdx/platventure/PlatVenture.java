package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatVenture extends Game {
	private SpriteBatch listeAff;
	private Niveau niveau;

	@Override
	public void create() {
		listeAff = new SpriteBatch();
		setScreen(new EcranPresentation(this));
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
}
