package com.projetJava;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.projetJava.Scene.Menu;
import com.projetJava.Scene.Scene;

public class Main extends Game {
    private static Scene currentScene;

    public static void setScene(Scene newScene) {
        if (currentScene != null) {
            currentScene.dispose();
        }

        currentScene = newScene;
        currentScene.show();

        Gdx.app.log("Scene Change", "New Scene: " + newScene.getClass().getSimpleName());
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    @Override
    public void create() {
        AssetsManager.load();
        AssetsManager.finishLoading();

        setScene(new Menu());
    }

    @Override
    public void render() {
        if (currentScene != null) {
            currentScene.render(Gdx.graphics.getDeltaTime());
        }
    }

    public Main() {

    }

    @Override
    public void dispose() {
        if (currentScene != null) {
            currentScene.dispose();
        }
        AssetsManager.dispose();
    }
}
