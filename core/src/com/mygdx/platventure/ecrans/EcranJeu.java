package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Niveau;
import com.mygdx.platventure.PlatVentureGame;

public class EcranJeu extends ScreenAdapter {

    final private PlatVentureGame game;
    final private Texture font;

    private final OrthographicCamera camera;
    private final FitViewport vp;

    private final Box2DDebugRenderer debugRenderer;

    private final World world;

    public EcranJeu(PlatVentureGame game) {
        this.game = game;
        this.font = new Texture("images/Back.png");
        this.game.setNiveau(new Niveau("levels/level_001.txt"));

        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth(), camera);
        vp.apply();

        debugRenderer = new Box2DDebugRenderer(true, true, false, true, true, true);

        world = game.getWorld();
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
