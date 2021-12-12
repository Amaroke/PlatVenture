package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecouteurs.EcouteurJoueur;
import com.mygdx.platventure.ecouteurs.UserData;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.JoueurP;
import com.mygdx.platventure.elements.gemmes.Gemme;

public class EcranJeu extends ScreenAdapter {

    final private PlatVenture platVenture;
    final private Texture texture;

    private final OrthographicCamera camera;
    private final FitViewport vp;
    private final OrthographicCamera cameraTexte;
    private float tempsEntreRenderer = 0;

    private final Box2DDebugRenderer debugRenderer;

    private final EcouteurJoueur ecouteurJoueur = new EcouteurJoueur();
    private BitmapFont bitmapfont;

    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.texture = new Texture("images/Back.png");
        this.platVenture.setMonde(new Monde());

        // On définit la caméra
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth(), camera);
        vp.apply();
        camera.update();

        // On définit la caméra pour les textes
        cameraTexte = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // On charge les polices de texte.
        chargerFont();


        // On définit l'écran de debug
        debugRenderer = new Box2DDebugRenderer();
        // On définit les controles du joueurs
        Gdx.input.setInputProcessor(this.ecouteurJoueur);
    }

    private void chargerFont() {
        // On récupère la police et on applique des paramètres
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        freeTypeFontParameter.size = (int) (60f * Gdx.graphics.getWidth() / 1024f);
        freeTypeFontParameter.color = Color.YELLOW;
        freeTypeFontParameter.borderColor = Color.BLACK;
        freeTypeFontParameter.borderWidth = (3f * Gdx.graphics.getWidth() / 1024f);

        // On génère notre police
        bitmapfont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        // On libère le générateur
        freeTypeFontGenerator.dispose();
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
        if (this.platVenture.getMonde().getNiveauLargeur() < camera.viewportWidth) {
            this.camera.position.x = camera.viewportWidth / 2f - this.platVenture.getMonde().getNiveauLargeur() / 2f;
            this.camera.position.y = this.platVenture.getMonde().getNiveauHauteur() / 2f;
        }
        this.centrerCameraJoueur();
        platVenture.getListeAff().setProjectionMatrix(camera.combined);
        platVenture.getListeAff().begin();
        //On affiche le mode de debug s'il est actif
        if (this.ecouteurJoueur.isDebugActif()) {
            debugRenderer.render(platVenture.getMonde().getWorld(), camera.combined);
        } else {
            // On dessine le fond
            platVenture.getListeAff().draw(this.texture, 0, 0, platVenture.getMonde().getNiveauLargeur(), platVenture.getMonde().getNiveauHauteur());
            // On affiche les différents éléments du jeu
            for (Element e : this.platVenture.getMonde().getElements()) {
                // On gère les animations des gemmes
                if (e.getBody().getUserData() == UserData.GEMME) {
                    tempsEntreRenderer += Gdx.graphics.getDeltaTime();
                    TextureRegion t = new TextureRegion((TextureRegion) (((Gemme) e).getAnimation().getKeyFrame(tempsEntreRenderer, true)));
                    platVenture.getListeAff().draw(t, e.getPosition().x + 0.25f, e.getPosition().y + 0.25f, 0.5f, 0.5f);
                } else {
                    if (e.getTexture() != null) {
                        if (e.estJoueur()) {
                            if (e.getBody().getLinearVelocity().y < -0.01 || e.getBody().getLinearVelocity().y > 0.01) {
                                e.setTexture(((JoueurP) e).getTextureJump());
                            } else if (e.getBody().getLinearVelocity().x < -0.1 || e.getBody().getLinearVelocity().x > 0.1) {
                                e.setTexture(((JoueurP) e).getTextureRun());
                            } else {
                                e.setTexture(((JoueurP) e).getTextureIdle());
                            }
                            if (e.getBody().getLinearVelocity().x < -0.1) {
                                platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x + 0.25f + e.getLargeur(), e.getPosition().y, -e.getLargeur(), e.getHauteur());
                            } else {
                                platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x + 0.25f, e.getPosition().y, e.getLargeur(), e.getHauteur());
                            }
                        } else {
                            // On oriente la sortie en fonction de son emplacement
                            if (e.getBody().getUserData() == UserData.SORTIEZ) {
                                if (e.getBody().getPosition().x < 2) {
                                    platVenture.getListeAff().draw(e.getTexture(), e.getBody().getPosition().x + e.getLargeur(), e.getPosition().y - 0.25f, -e.getLargeur(), e.getHauteur() + 0.25f);
                                } else {
                                    platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x, e.getPosition().y - 0.25f, e.getLargeur(), e.getHauteur() + 0.25f);
                                }
                            } else {
                                platVenture.getListeAff().draw(e.getTexture(), e.getPosition().x, e.getPosition().y, e.getLargeur(), e.getHauteur());
                            }
                        }
                    }
                }
            }
            platVenture.getListeAff().setProjectionMatrix(cameraTexte.combined);
            //On affiche le texte sur la camera
            // Le score
            bitmapfont.draw(platVenture.getListeAff(), "Score : " + platVenture.getMonde().getScore(), camera.position.x + cameraTexte.viewportWidth / 2 - 20 - (int) (7 * platVenture.getMonde().getScore() / 10f), camera.position.y + cameraTexte.viewportHeight / 2 - 10, 0, 0, false);
            // Le timer
            bitmapfont.draw(platVenture.getListeAff(), "" + platVenture.getMonde().getTemps(), camera.position.x, camera.position.y + cameraTexte.viewportHeight / 2 - 7, 0, 0, false);
            // Les textes en cas de victoire ou défaite
            if (platVenture.getMonde().isGagne()) {
                bitmapfont.draw(platVenture.getListeAff(), "Bravo :-)", camera.position.x + camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2, 0, 0, false);
            } else if (platVenture.getMonde().isPerdu()) {
                bitmapfont.draw(platVenture.getListeAff(), "Dommage :-/", camera.position.x + camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2, 0, 0, false);
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
        if (this.platVenture.getMonde().isAuDebut()) {
            camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
            this.platVenture.getMonde().setAuDebut(false);
        } else {
            if (positionJoueurY > camera.position.y && (camera.position.y + camera.viewportHeight / 2) < this.platVenture.getMonde().getNiveauHauteur()) {
                camera.position.set(new Vector2(camera.position.x, positionJoueurY), 0);
            }
            // Si le joueur est trop dans le bas de la caméra et qu'on dépasse pas la largeur du niveau
            if (positionJoueurY < camera.position.y && camera.position.y > camera.viewportHeight / 2f) {
                camera.position.set(new Vector2(camera.position.x, positionJoueurY), 0);
            }
            // L'inverse des deux précédents
            if (positionJoueurX > camera.position.x && (camera.position.x + camera.viewportWidth / 2) < this.platVenture.getMonde().getNiveauLargeur()) {
                camera.position.set(new Vector2(positionJoueurX, camera.position.y), 0);
            }
            if (positionJoueurX < camera.position.x && camera.position.x > 16 / 2f) {
                camera.position.set(new Vector2(positionJoueurX, camera.position.y), 0);
            }
        }
        camera.update();
    }
}
