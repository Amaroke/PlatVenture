package com.mygdx.platventure.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Element {

    protected Vector2 position;
    protected Body body;
    protected BodyDef bodyDef;
    private Texture texture;

    public Element(Vector2 position) {
        this.position = position;
    }

    public abstract void setBodyDef();

    public abstract void setFixture();

    public void createBody(World monde) {
        this.body = monde.createBody(this.bodyDef);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }

    public void setPosition(Vector2 pos) {
        this.position = pos;
    }
}
