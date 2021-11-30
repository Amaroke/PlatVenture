package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.platventure.ecouteurs.UserData;

public class Gemme2 extends Gemme {

    public Gemme2(Vector2 position) {
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
            // L'objet est traversable
            fixtureDef.isSensor = true;
            getBody().createFixture(fixtureDef);
            getBody().setUserData(UserData.GEMME2);
        }
        this.getForme().dispose();
    }

}

