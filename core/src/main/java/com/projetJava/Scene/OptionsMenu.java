package com.projetJava.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.projetJava.AssetsManager;
import com.projetJava.Main;

public class OptionsMenu extends Scene {

    private Stage stage;
    private Skin skin;
    private Slider volumeSlider;
    private TextButton backButton;
    private TextButton keyBindingButton;

    private Music backgroundMusic;

    public OptionsMenu() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/glassy-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        backgroundMusic = AssetsManager.getMusic("Sound/Hollow.wav");

        Label volumeLabel = new Label("Volume:", skin);
        volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSlider.setValue(backgroundMusic.getVolume());
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                backgroundMusic.setVolume(volume);
            }
        });

        CheckBox fullscreenCheckbox = new CheckBox("Fullscreen", skin);
        fullscreenCheckbox.setChecked(Gdx.graphics.isFullscreen());
        fullscreenCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fullscreenCheckbox.isChecked()) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    Gdx.graphics.setWindowedMode(1280, 720);
                }
            }
        });

        keyBindingButton = new TextButton("Controls", skin);
        keyBindingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remapKeys();
            }
        });

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.setScene(new Menu());
            }
        });

        table.add(volumeLabel).left().pad(10);
        table.add(volumeSlider).fillX().uniformX().pad(10);
        table.row().pad(10);
        table.add(fullscreenCheckbox).fillX().uniformX().pad(10);
        table.row().pad(10);
        table.add(keyBindingButton).fillX().uniformX().pad(10);
        table.row().pad(10);
        table.add(backButton).fillX().uniformX().pad(10);
    }

    @Override
    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }

    // Méthode pour redemander les touches, a voir si on a le temps ...
    private void remapKeys() {
        System.out.println("not yet implemented.");
    }

}
