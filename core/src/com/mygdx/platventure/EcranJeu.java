package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EcranJeu extends ScreenAdapter {

    final PlatVenture game;
    final Texture font;
    private final OrthographicCamera camera;
    private final FitViewport vp;

    public EcranJeu(PlatVenture game) {
        this.game = game;
        this.font = new Texture("images/Back.png");
        this.game.setNiveau(new Niveau("levels/level_001.txt"));
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth(), camera);
        vp.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getListeAff().setProjectionMatrix(camera.combined);
        game.getListeAff().begin();
        game.getListeAff().draw(this.font, 0, 0, game.getNiveauLargeur(), game.getNiveauHauteur());
        game.getListeAff().end();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getListeAff().setProjectionMatrix(camera.combined);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
