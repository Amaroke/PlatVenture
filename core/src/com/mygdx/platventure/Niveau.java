package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Niveau {
    private final int temps;
    private final char[][] tableau;

    public Niveau(String niveau) {
        FileHandle file = Gdx.files.internal(niveau);
        String[] tabString = file.readString().split("\\n");
        String[] tabString0 = tabString[0].split(" ");
        int largeur = Integer.parseInt(tabString0[0]);
        int hauteur = Integer.parseInt(tabString0[1]);
        temps = Integer.parseInt(tabString0[2]);
        tableau = new char[largeur][hauteur];
        for (int i = 1; i < hauteur + 1; ++i) {
            for (int j = 0; j < largeur; ++j) {
                tableau[j][i - 1] = tabString[i].charAt(j);
            }
        }
    }

    public char[][] getTableau() {
        return tableau;
    }

    public int getTemps() {
        return temps;
    }
}
