package com.projetJava;

import com.badlogic.gdx.physics.box2d.World;
import com.projetJava.Entity.Entity;

import java.util.HashMap;
import java.util.Map;

public abstract class EnemyWithoutGraphics extends Entity {

    protected int damage, value;
    protected Map<String, PlayerWithoutGraphics> player = new HashMap<>();
    protected boolean isFacingRight = true;

    public EnemyWithoutGraphics(float health, float posX, float posY, float width, float height, int speed, World world,
                 int damage, int value) {
        super(health, posX, posY, width, height, speed, world);
        this.damage = damage;
        this.value = value;
    }

    public abstract void behavior(float delta);

    public void setPlayer(String str, PlayerWithoutGraphics player) {
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

    public int getDamage() {
        return damage;
    }
}
