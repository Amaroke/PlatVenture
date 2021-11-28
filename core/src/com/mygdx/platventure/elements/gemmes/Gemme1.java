package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Gemme1 extends Gemme {

    public Gemme1(Vector2 position) {
        super(position);
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
            fixtureDef.shape = this.getForme();
            fixtureDef.density = 0;
            fixtureDef.restitution = 0;
            fixtureDef.friction = 0;
            getBody().createFixture(fixtureDef);
        }
        this.getForme().dispose();
    }

}
