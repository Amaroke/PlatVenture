package com.mygdx.platventure.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.ecouteurs.UserData;

public class Brique extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public Brique(Vector2 position, char type) {
        super(position, new Texture("images/Brick_" + type + ".png"));
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;
        Vector2[] points = {
                new Vector2(0, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(1, 0)
        };
        this.forme.set(points);
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
