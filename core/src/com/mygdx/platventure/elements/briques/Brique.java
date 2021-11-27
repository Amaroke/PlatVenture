package com.mygdx.platventure.elements.briques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.elements.Element;

public abstract class Brique extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public Brique(Vector2 position) {
        super(position);
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;
    }

    public PolygonShape getForme() {
        return forme;
    }

    public float getDensite() {
        return densite;
    }

    public float getRestitution() {
        return restitution;
    }

    public float getFriction() {
        return friction;
    }
}
