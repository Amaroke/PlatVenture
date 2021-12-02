package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.ecouteurs.UserData;
import com.mygdx.platventure.elements.Element;

public abstract class Plateforme extends Element {

    protected final PolygonShape forme;
    protected final float densite;
    protected final float restitution;
    protected final float friction;

    public Plateforme(Vector2 position, Texture texture) {
        super(position, texture);
        this.hauteur = 0.75f;
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;
        Vector2[] vectors = {
                new Vector2(0, 0.75f),
                new Vector2(1, 0.75f),
                new Vector2(1, 0),
                new Vector2(0.5f, 0),
                new Vector2(0, 0.375f),
        };
        this.forme.set(vectors);
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() {
        if ((this.bodyDef != null) && (this.body != null)) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = forme;
            fixtureDef.density = densite;
            fixtureDef.restitution = restitution;
            fixtureDef.friction = friction;
            getBody().createFixture(fixtureDef);
            getBody().setUserData(UserData.BRIQUE);
        }
        this.forme.dispose();
    }

}
