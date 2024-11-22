package com.projetJava.Entity;

import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;
import java.util.Map;

public abstract class Enemy extends Entity {

    protected int damage, value;
    protected Map<String, Player> player = new HashMap<>();
    protected boolean isFacingRight = true;

    public Enemy(float health, float posX, float posY, float width, float height, int speed, World world,
            int damage, int value) {
        super(health, posX, posY, width, height, speed, world);
        this.damage = damage;
        this.value = value;
    }

    public Enemy(float health, float posX, float posY, float width, float height, int speed, World world,
            int damage, int value, float offsetX) {
        super(health, posX, posY, width, height, speed, world, offsetX);
        this.damage = damage;
        this.value = value;
    }

    public abstract void behavior(float delta);

    public void setPlayer(String str, Player player) {
        this.player.put(str, player);
        if (str.equals("Right")) {
            isFacingRight = true;
        } else if (str.equals("Left")) {
            isFacingRight = false;
        }
    }

    public void removePlayer() {
        if (!player.isEmpty()) {
            if (player.containsKey("Right")) {
                player.remove("Right");
            } else if (player.containsKey("Left")) {
                player.remove("Left");
            }
        }
    }
}