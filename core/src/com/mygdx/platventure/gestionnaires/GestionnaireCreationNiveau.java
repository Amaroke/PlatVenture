package com.mygdx.platventure.gestionnaires;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GestionnaireCreationNiveau {

    private final int temps;
    private final char[][] tableau;
    private final int largeur;
    private final int hauteur;

    public GestionnaireCreationNiveau(String niveau) {
        FileHandle file = Gdx.files.internal(niveau);
        String[] tabString = file.readString().split("\\n");
        String[] tabString0 = tabString[0].split(" ");
        // On récupère la largeur du niveau
        largeur = Integer.parseInt(tabString0[0]);
        // On récupère la hauteur du niveau
        hauteur = Integer.parseInt(tabString0[1]);
        // On récupère le temps du niveau
        temps = Integer.parseInt(tabString0[2]);
        // On récupère le contenu du niveau, sous la forme d'une matrice de caractères
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

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}
