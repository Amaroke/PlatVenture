package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;

public class PlatVenture extends Game {
	SpriteBatch listeAff;
	ShapeRenderer shapeRenderer;
	Texture font;
	OrthographicCamera camera;
	Niveau niveau;
	
	@Override
	public void create () {
		listeAff = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setScreen(new EcranPresentation(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		listeAff.begin();
		listeAff.draw(font, 0, 0);
		listeAff.end();
	}
	
	@Override
	public void dispose () {
		listeAff.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}

	public void setFont(Texture font) {
		this.font = font;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
}
