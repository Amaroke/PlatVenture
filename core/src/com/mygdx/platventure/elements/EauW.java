package com.mygdx.platventure.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.ecouteurs.UserData;

public class EauW extends Element {

    private final PolygonShape forme = new PolygonShape();

    public EauW(Vector2 position) {
        super(position);
        Vector2[] points = {
                new Vector2(0, 0),
                new Vector2(0, 0.75f),
                new Vector2(1, 0.75f),
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
            // L'objet est traversable
            fixtureDef.isSensor = true;
            getBody().createFixture(fixtureDef);
            getBody().setUserData(UserData.EAUW);
        }
        this.forme.dispose();
    }

}
