package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlateformeJ extends Plateforme {

    public PlateformeJ(Vector2 position) {
        super(position, new Texture("images/Platform_J.png"));
        Vector2[] points = {
                new Vector2(0, 0.75f),
                new Vector2(1, 0.75f),
                new Vector2(1, 0),
                new Vector2(0.5f, 0),
                new Vector2(0, 0.375f),
        };
        this.forme.set(points);
    }
}
