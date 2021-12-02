package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecouteurs.EcouteurJoueur;
import com.mygdx.platventure.elements.Element;

public class EcranJeu extends ScreenAdapter {

    final private PlatVenture platVenture;
    final private Texture font;

    private final OrthographicCamera camera;
    private final FitViewport vp;

    private final Box2DDebugRenderer debugRenderer;
    private final EcouteurJoueur ecouteurJoueur = new EcouteurJoueur();


    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.font = new Texture("images/Back.png");
        this.platVenture.setMonde(new Monde());

        // On définit la caméra
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth(), camera);
        vp.apply();
        camera.update();

        // On définit l'écran de debug
        debugRenderer = new Box2DDebugRenderer();
        // On définit les controles du joueurs
        Gdx.input.setInputProcessor(this.ecouteurJoueur);
    }

    @Override
    public void show() {
        // On est obligé de faire un appel ici, autrement ils ne sont pas pris en compte
        Gdx.input.setInputProcessor(this.ecouteurJoueur);
    }

    @Override
    public void render(float delta) {
        // On nettoie l'écran
        ScreenUtils.clear(0, 0, 0, 1);
        // On attribue le déplacement voulu au joueur
        this.platVenture.getMonde().getJoueur().setMouvevement(this.ecouteurJoueur.getDeplacement());
        // On update le monde
        this.platVenture.getMonde().update();
        // On définit le step du monde
        platVenture.getMonde().getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
        // On update la caméra
        this.centrerCameraJoueur();
        platVenture.getListeAff().setProjectionMatrix(camera.combined);
        platVenture.getListeAff().begin();
        //On affiche le mode de debug
        if (this.ecouteurJoueur.isDebugActif()) {
            debugRenderer.render(platVenture.getMonde().getWorld(), camera.combined);
        } else {
            platVenture.getListeAff().draw(this.font, 0, 0, platVenture.getMonde().getNiveauLargeur(), platVenture.getMonde().getNiveauHauteur());
            for (Element e : this.platVenture.getMonde().getElements()) {
                if (e.getTexture() != null) {
                    if (e.estJoueur()) {
                        platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x + 0.25f, e.getPosition().y, e.getLargeur(), e.getHauteur());
                    } else {
                        platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x, e.getPosition().y, e.getLargeur(), e.getHauteur());
                    }

                }
            }
        }
        platVenture.getListeAff().end();
    }

    @Override
    public void dispose() {
        platVenture.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        platVenture.getListeAff().setProjectionMatrix(camera.combined);
    }

    public void centrerCameraJoueur() {
        float positionJoueurX = this.platVenture.getMonde().getJoueur().getPosition().x;
        float positionJoueurY = this.platVenture.getMonde().getJoueur().getPosition().y;
        // Si le joueur est trop dans le haut de la caméra et qu'on dépasse pas la hauteur du niveau
        if (positionJoueurY > camera.position.y && (camera.position.y + camera.viewportHeight / 2) < this.platVenture.getMonde().getNiveauHauteur()) {
            // On déplace de 0.06 pour que ce soit fluide, comme dans la vidéo
            camera.position.set(new Vector2(camera.position.x, camera.position.y + 0.06f), 0);
        }
        // Si le joueur est trop dans le bas de la caméra et qu'on dépasse pas la largeur du niveau
        if (positionJoueurY < camera.position.y && camera.position.y > camera.viewportHeight / 2f) {
            camera.position.set(new Vector2(camera.position.x, camera.position.y - 0.06f), 0);
        }
        // L'inverse des deux précédents
        // On utilsie -2 et +2 pour que même au centre de l'écran le joueur ait une zone de manoeuvre horizontale
        if (positionJoueurX > camera.position.x + 2 && (camera.position.x + camera.viewportWidth / 2) < this.platVenture.getMonde().getNiveauLargeur()) {
            camera.position.set(new Vector2(camera.position.x + 0.06f, camera.position.y), 0);
        }
        if (positionJoueurX < camera.position.x - 2 && camera.position.x > 16 / 2f) {
            camera.position.set(new Vector2(camera.position.x - 0.06f, camera.position.y), 0);
        }
        camera.update();
    }

}
