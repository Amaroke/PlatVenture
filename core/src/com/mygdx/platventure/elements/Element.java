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
    protected Texture texture;
    protected float hauteur = 1;
    protected float largeur = 1;

    public Element(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getHauteur() {
        return hauteur;
    }

    public float getLargeur() {
        return largeur;
    }

    public boolean estJoueur() {
        return false;
    }
}
