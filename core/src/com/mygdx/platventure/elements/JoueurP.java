package com.mygdx.platventure.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.ecouteurs.UserData;

public class JoueurP extends Element {

    private final PolygonShape formeTete = new PolygonShape();
    private final CircleShape formePied = new CircleShape();
    // On respecte le ratio de l'image
    private static final float ratio = 500f * 0.5f / 290f;

    public JoueurP(Vector2 position) {
        super(position, new Texture("images/Idle__000.png"));
        this.hauteur = ratio;
        this.largeur = 0.5f;
        Vector2[] vecteurs = {
                new Vector2(0.25f, ratio / 2),
                new Vector2(0.5f, ratio),
                new Vector2(0.75f, ratio / 2),
                new Vector2(0.50f, ratio / 4)
        };
        this.formeTete.set(vecteurs);
        this.formePied.setRadius(ratio / 8);
        this.formePied.setPosition(new Vector2(0.50f, ratio / 8));
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        // L'element de peut se déplacer
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Il ne peut pas faire de rotation, et donc ne peut pas tomber
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() {
        if ((this.bodyDef != null) && (this.body != null)) {
            FixtureDef fixtureDefTete = new FixtureDef();
            fixtureDefTete.shape = this.formeTete;
            fixtureDefTete.density = 0.5f;
            fixtureDefTete.restitution = 0.1f;
            fixtureDefTete.friction = 0.5f;
            getBody().createFixture(fixtureDefTete);
            getBody().setUserData(UserData.JOUEURP);
            FixtureDef fixtureDefPied = new FixtureDef();
            fixtureDefPied.shape = this.formePied;
            fixtureDefPied.density = 0.5f;
            fixtureDefPied.restitution = 0.1f;
            fixtureDefPied.friction = 0.5f;
            getBody().createFixture(fixtureDefPied);
            getBody().setUserData(UserData.JOUEURP);
            getBody().setTransform(new Vector2(getPosition().x, getPosition().y), 0);
        }
        this.formeTete.dispose();
        this.formePied.dispose();
    }

    public void setMouvevement(Vector2 v) {
        // On met "< 0.00001" au lieu de "== 0", pour plus de liberté de mouvement
        if (this.getBody().getLinearVelocity().y < 0.00001f && this.getBody().getLinearVelocity().y > -0.00001f) {
            this.getBody().applyForceToCenter(v, true);
        }
    }

    @Override
    public boolean estJoueur() {
        return true;
    }
}
