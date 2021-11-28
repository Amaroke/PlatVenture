package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.platventure.elements.Element;

public abstract class Gemme extends Element {

    protected CircleShape forme;

    public Gemme(Vector2 position) {
        super(position);
        this.forme = new CircleShape();
        this.forme.setPosition(new Vector2(0.5f, 0.5f));
        this.forme.setRadius(0.25f);
    }

    public CircleShape getForme() {
        return forme;
    }
}
