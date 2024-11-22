package com.projetJava;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

// On importe tous les assets içi pour centraliser

public class AssetsManager {

    private static AssetManager assetManager = new AssetManager();

    public static void load() {

        assetManager.load("Player/Animations/Attack/Attack.atlas", TextureAtlas.class);

        assetManager.load("Player/Animations/Idle/Idle.atlas", TextureAtlas.class);

        assetManager.load("Player/Animations/Walk/Walk.atlas", TextureAtlas.class);

        assetManager.load("Ennemy/Idle/Idle.atlas", TextureAtlas.class);

        assetManager.load("Ennemy/Attack02/Attack.atlas", TextureAtlas.class);

        assetManager.load("Ennemy/Walking/Walk.atlas", TextureAtlas.class);

        assetManager.load("Ennemy/Dying/Death.atlas", TextureAtlas.class);

        assetManager.load("Ennemy/Attack_sword/Attack.atlas", TextureAtlas.class);

        assetManager.load("Boss/Idle/Idle.atlas", TextureAtlas.class);

        assetManager.load("Boss/Walk/Walk.atlas", TextureAtlas.class);

        assetManager.load("Boss/Attack/Attack.atlas", TextureAtlas.class);

        assetManager.load("Boss/Death/Death.atlas", TextureAtlas.class);

        assetManager.load("Player/Health.atlas", TextureAtlas.class);

        assetManager.load("Sound/Hollow.wav", Music.class);

    }

    public static void finishLoading() {
        assetManager.finishLoading();
    }

    public static Texture getTexture(String fileName) {
        return assetManager.get(fileName, Texture.class);
    }

    public static TextureAtlas getTextureAtlas(String fileName) {
        return assetManager.get(fileName, TextureAtlas.class);
    }

    public static Music getMusic(String fileName) {
        return assetManager.get(fileName, Music.class);
    }

    public static void dispose() {
        assetManager.dispose();
    }

}
