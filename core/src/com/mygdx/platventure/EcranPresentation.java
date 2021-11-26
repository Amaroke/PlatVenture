package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class EcranPresentation extends ScreenAdapter {
    final PlatVenture game;
    final Texture font;

    public EcranPresentation(PlatVenture game) {
        this.game = game;
        font = new Texture("images/Intro.png");
    }

    @Override
    public void show() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/win.ogg"));
        music.play();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new EcranJeu(game));
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getListeAff().begin();
        game.getListeAff().draw(this.font, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getListeAff().end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
