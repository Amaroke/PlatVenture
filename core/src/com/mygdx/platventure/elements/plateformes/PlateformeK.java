package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlateformeK extends Plateforme {

    public PlateformeK(Vector2 position) {
        super(position, new Texture("images/Platform_K.png"));
        Vector2[] points = {
                new Vector2(0, 0),
                new Vector2(0, 0.75f),
                new Vector2(1, 0.75f),
                new Vector2(1, 0)
        };
        this.forme.set(points);
    }
}
