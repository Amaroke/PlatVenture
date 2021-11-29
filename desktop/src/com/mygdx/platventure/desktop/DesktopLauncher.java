package com.mygdx.platventure.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.platventure.PlatVenture;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("PlatVenture");
        // On règle le jeu à 60fps.
        config.setForegroundFPS(60);
        // Onfait une fenêtre de 800x400
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new PlatVenture(), config);
    }
}
