package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.platventure.PlatVenture;

public class EcranPresentation extends ScreenAdapter {
    final PlatVenture platVenture;
    final Texture fond;

    public EcranPresentation(PlatVenture platVenture) {
        this.platVenture = platVenture;
        fond = new Texture("images/Intro.png");
    }

    @Override
    public void show() {
        // On joue le son en début de partie
        Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/win.ogg"));
        music.play();
        // On attends 3 secondes pour passer à l'écran suivant
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                platVenture.setScreen(new EcranJeu(platVenture));
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        platVenture.getListeAff().begin();
        platVenture.getListeAff().draw(this.fond, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        platVenture.getListeAff().end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
