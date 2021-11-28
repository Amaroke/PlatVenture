package com.mygdx.platventure.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SortieZ extends Element {

    private final PolygonShape forme = new PolygonShape();

    public SortieZ(Vector2 position) {
        super(position);
        Vector2[] vectors = {
                new Vector2(0, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(1, 0)
        };
        this.forme.set(vectors);
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
            fixtureDef.shape = forme;
            fixtureDef.density = 0;
            fixtureDef.restitution = 0;
            fixtureDef.friction = 0;
            getBody().createFixture(fixtureDef);
        }
        this.forme.dispose();
    }

}
