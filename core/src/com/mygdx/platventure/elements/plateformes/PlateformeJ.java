package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.platventure.elements.briques.Brique;

public class PlateformeJ extends Brique {

    public PlateformeJ(Vector2 position) {
        super(position);
        Vector2[] vectors = {
                new Vector2(0, 0.75f),
                new Vector2(1, 0.75f),
                new Vector2(1, 0),
                new Vector2(0.5f, 0),
                new Vector2(0, 0.375f),

        };

        this.getForme().set(vectors);
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(this.getPosition());
    }

    @Override
    public void setFixture() {
        if ((this.bodyDef != null) && (this.body != null)) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = getForme();
            fixtureDef.density = getDensite();
            fixtureDef.restitution = getRestitution();
            fixtureDef.friction = getFriction();
            getBody().createFixture(fixtureDef);
        }
        this.getForme().dispose();
    }

}
