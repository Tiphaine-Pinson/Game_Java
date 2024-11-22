package com.projetJava.Scene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.projetJava.AssetsManager;
import com.projetJava.Entity.Player;
import com.projetJava.Entity.Sword;

public abstract class Scene implements Screen {

    protected final World world = new World(new Vector2(0, -30f), true);
    Sword sword = new Sword(1, 5f);

    protected final Player player = new Player(3, 100, 50, 200, 200, 100, world,
            sword, AssetsManager.getTextureAtlas("Player/Animations/Idle/Idle.atlas"),
            AssetsManager.getTextureAtlas("Player/Animations/Walk/Walk.atlas"),
            AssetsManager.getTextureAtlas("Player/Animations/Attack/Attack.atlas"),
            sword.getScope());
    protected Music backgroundMusic;

    @Override
    public void show() {

        backgroundMusic = AssetsManager.getMusic("Sound/Hollow.wav");

        if (backgroundMusic != null) {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
    }

    @Override
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        world.dispose();
    }

    public abstract void update();

}
