package com.projetJava.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.projetJava.Main;

public class Menu extends Scene {

    private Stage stage;
    private Skin skin;

    public Menu() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/glassy-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton storyButton = new TextButton("Story", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        storyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Story button clicked");
                // ATTENTION AJOUT DU NIVEAU 1 ICI
                Main.setScene(new Level01());
            }
        });

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Options button clicked");
                // ATTENTION ON AJOUTERA LES OPTIONS
                Main.setScene(new OptionsMenu());
            }
        });

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(storyButton).fillX().uniformX().pad(10);
        table.row();
        table.add(optionsButton).fillX().uniformX().pad(10);
        table.row();
        table.add(quitButton).fillX().uniformX().pad(10);
    }

    public Stage getStage() {
        return stage;
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
}
