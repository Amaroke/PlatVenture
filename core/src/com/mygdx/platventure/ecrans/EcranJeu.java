package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.Niveau;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.controles.ControleJoueur;

public class EcranJeu extends ScreenAdapter {

    final private PlatVenture game;
    //final private Texture font;

    private final OrthographicCamera camera;
    private final FitViewport vp;

    private final Box2DDebugRenderer debugRenderer;
    private final ControleJoueur controleJoueur = new ControleJoueur();


    public EcranJeu(PlatVenture game) {
        this.game = game;
        //this.font = new Texture("images/Back.png");
        //On charge le niveau 1
        this.game.setNiveau(new Niveau("levels/level_001.txt"));
        this.game.setMonde(new Monde(this.game.getNiveau()));

        //On définit la caméra
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth(), camera);
        vp.apply();
        camera.update();

        //On définit l'écran de debug
        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(this.controleJoueur);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.controleJoueur);
    }

    @Override
    public void render(float delta) {
        //On nettoie l'écran
        ScreenUtils.clear(0, 0, 0, 1);
        this.game.getMonde().getJoueur().setMouvevement(this.controleJoueur.getDeplacement());
        this.game.getMonde().update();
        game.getMonde().getMonde().step(Gdx.graphics.getDeltaTime(), 6, 2);
        //On update la caméra
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getListeAff().setProjectionMatrix(camera.combined);
        game.getListeAff().begin();
        //On affiche le mode de debug
        //game.getListeAff().draw(this.font, 0, 0, game.getNiveauLargeur(), game.getNiveauHauteur());
        if (this.controleJoueur.isDebugActif()) {
            debugRenderer.render(game.getMonde().getMonde(), camera.combined);
        }

        game.getListeAff().end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        game.getListeAff().setProjectionMatrix(camera.combined);
    }

}
