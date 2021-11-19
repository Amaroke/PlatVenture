package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Niveau {
    private final int largeur;
    private final int hauteur;
    private final int temps;
    private final char[][] tableau;

    public Niveau(String niveau) {
        FileHandle file = Gdx.files.internal(niveau);
        String[] tabString = file.readString().split("\\n");
        String[] tabString0 = tabString[0].split(" ");
        largeur = Integer.parseInt(tabString0[0]);
        hauteur = Integer.parseInt(tabString0[1]);
        temps = Integer.parseInt(tabString0[2]);
        tableau = new char[largeur][hauteur];
        for(int i = 1; i<hauteur+1;++i){
            for(int j = 0; j<largeur-1; ++j){
                tableau[j][i] = tabString[i].charAt(j);
            }
        }

        System.out.println(tableau[0][0]);
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getTemps() {
        return temps;
    }
}
