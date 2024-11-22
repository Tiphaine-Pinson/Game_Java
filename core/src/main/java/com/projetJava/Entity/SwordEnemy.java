package com.projetJava.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.projetJava.AnimationManager;

public class SwordEnemy extends Enemy {
    private AnimationManager animationManager;

    private TextureRegion currentFrame;
    private float timer = 0.0f;
    private int move = 0;
    private boolean isDead = false;
    private float deathAnimationTime = 0f;

    public SwordEnemy(float health, float posX, float posY, float width, float height, int speed, World world,
            TextureAtlas idleAtlas, TextureAtlas walkAtlas, TextureAtlas attackAtlas, TextureAtlas deathAtlas) {
        super(health, posX, posY, width, height, speed, world, 50, 50, 5);

        animationManager = new AnimationManager(idleAtlas, walkAtlas, attackAtlas, deathAtlas);

    }

    @Override
    public void update(SpriteBatch batch) {
        if (health > 0) {
            Vector2 position = body.getPosition();

            if (!player.isEmpty()) {
                animationManager.setAnimation(animationManager.attackAnimation);
            } else if (body.getLinearVelocity().x != 0) {
                animationManager.setAnimation(animationManager.walkAnimation);
            } else {
                animationManager.setAnimation(animationManager.idleAnimation);
            }

            currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
        } else {
            if (!isDead) {
                animationManager.setAnimation(animationManager.deathAnimation);
                isDead = true;
                body.setLinearVelocity(0, 0);
            }

            deathAnimationTime += Gdx.graphics.getDeltaTime();
            if (animationManager.isAnimationFinished(deathAnimationTime)) {
                body.setActive(false);
            }

            currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
        }

        Vector2 position = body.getPosition();
        if (isFacingRight) {
            batch.draw(currentFrame, position.x - width / 2, position.y - height / 2, width, height);
        } else {
            batch.draw(currentFrame, position.x + width / 2, position.y - height / 2, -width, height);
        }
    }

    @Override
    public void update(float delta) {
        if (health <= 0) {
            body.setActive(false);
            return;
        }

        behavior(delta);
    }

    @Override
    public void attack() {
        for (Player player : player.values()) {
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
            if (firstX <= secondX + 0.5f) {
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
            timer += delta;
            if (timer > 2f) {
                animationManager.setAnimation(animationManager.attackAnimation);
                for (Player player : player.values()) {
                    player.takeDamage(damage);
                    System.out.println("tiens prends ça.");
                    System.out.println("Nombres de degats infligé : " + damage);
                    System.out.println("Vies du joueur après l'attaque : " + player.getHealth());
                    timer = 0;
                }
            }
        }
    }
}
