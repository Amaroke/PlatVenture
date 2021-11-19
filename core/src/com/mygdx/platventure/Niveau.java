package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Niveau {
    private int largeur;
    private int hauteur;
    private int temps;
    private char[][] tableau;

    public Niveau(String niveau) {
            FileHandle file = Gdx.files.internal(niveau);
            String text = file.readString();
            System.out.println("Contenu du fichier: ");
            System.out.println(text);
    }
}
