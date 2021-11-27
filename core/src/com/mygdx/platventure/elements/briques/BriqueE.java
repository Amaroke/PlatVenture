package com.mygdx.platventure.elements.briques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class BriqueE extends Brique {

    public BriqueE(Vector2 position) {
        super(position);
        Vector2[] vectors = {
                new Vector2(0, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(1, 0)
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
