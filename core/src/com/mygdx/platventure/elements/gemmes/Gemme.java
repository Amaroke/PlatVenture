package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.platventure.elements.Element;

public abstract class Gemme extends Element {

    protected CircleShape forme;
    private final Animation animation;

    public Gemme(Vector2 position, Texture texture) {
        super(position, texture);
        this.forme = new CircleShape();
        this.forme.setPosition(new Vector2(0.5f, 0.5f));
        this.forme.setRadius(0.25f);

        // On découpe le sprite
        TextureRegion[][] textureRegions = TextureRegion.split(getTexture(), 56, 56);
        TextureRegion[] sousTextureRegions1 = new TextureRegion[textureRegions.length];
        for (int i = 0; i < textureRegions.length; i++) {
            sousTextureRegions1[i] = textureRegions[i][0];
        }
        animation = new Animation<>(0.2f, sousTextureRegions1);
    }

    public CircleShape getForme() {
        return forme;
    }

    public abstract int getPoints();

    public Animation getAnimation() {
        return animation;
    }

}
