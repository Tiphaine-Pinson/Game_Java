package com.projetJava;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BasicEnemyWithoutGraphics extends EnemyWithoutGraphics {

    private float timer = 0.0f, timerATK = 0.0f;
    private int move = 0;

    public BasicEnemyWithoutGraphics(float health, float posX, float posY, float width, float height, int speed,
                                     World world) {
        super(health, posX, posY, width, height, speed, world, 4, 50);
    }

    @Override
    public void update(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {
        if (health <= 0) {
            body.setActive(false);
        }
    }

    @Override
    public void attack() {
        for (PlayerWithoutGraphics player : player.values()) {
            player.takeDamage(damage);
        }
    }

    @Override
    public void moveToRight() {
        body.setLinearVelocity(new Vector2(speed, 0));
    }

    @Override
    public void moveToLeft() {
        body.setLinearVelocity(new Vector2(-speed, 0));
    }

    private boolean goTo(float firstX, float secondX) {
        if (supAs(firstX, secondX)) {
            this.isFacingRight = false;
            if (firstX <= secondX + 1f) {
                body.setLinearVelocity(new Vector2(0, 0));
                return true;
            } else {
                moveToLeft();
            }

        } else {
            isFacingRight = true;
            if (firstX >= secondX - 0.5f) {
                body.setLinearVelocity(new Vector2(0, 0));
                return true;
            } else {
                moveToRight();
            }
        }
        return false;
    }

    private boolean supAs(float firstX, float secondX) {
        return (firstX > secondX);
    }

    @Override
    public void behavior(float delta) {
        if (this.player.isEmpty()) {
            switch (move) {
                case 0:
                    if (goTo(body.getPosition().x, posX - 150)) {
                        move = 1;
                    }
                    break;
                case 1:
                    timer += delta;
                    if (timer >= 2f) {
                        move = 2;
                        timer = 0;
                    }
                    break;
                case 2:
                    if (goTo(body.getPosition().x, posX)) {
                        move = 3;
                    }
                    break;
                case 3:
                    timer += delta;
                    if (timer >= 2f) {
                        move = 0;
                        timer = 0;
                    }
            }
        } else {
            body.setLinearVelocity(0, 0);
            timerATK += delta;
            if (timerATK > 2f) {
                for (PlayerWithoutGraphics player : player.values()) {
                    player.takeDamage(damage);
                    System.out.println("tiens prends ça.");
                    System.out.println("Nombres de degats infligé : " + damage);
                    System.out.println("Vies du joueur après l'attaque : " + player.getHealth());
                    timerATK = 0;
                }
            }
        }
    }
}
