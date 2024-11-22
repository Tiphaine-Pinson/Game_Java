package com.projetJava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projetJava.Entity.Entity;
import com.projetJava.Entity.Sword;

import java.util.*;

public class PlayerWithoutGraphics extends Entity {

    private Sword weapon;
    private int money = 0, nbJump = 2, lives = 3;
    private float timer = 0, fullLives = health * lives;
    private ArrayList<Map<String, EnemyWithoutGraphics>> ennemies = new ArrayList<>();
    private boolean isJumping = false, isFacingRight = true, movingRight = false, movingLeft = false,
        canJump = true;

    public PlayerWithoutGraphics(float health, float posX, float posY, float width, float height, int speed, World world,
                  Sword weapon, float offsetX) {
        super(health, posX, posY, width, height, speed, world, offsetX);
        this.weapon = weapon;
    }

    @Override
    public void update(SpriteBatch batch) {

    }


    public void takeDamage(int damage) {
        fullLives -= damage;
        if(fullLives <= 0) {
            lives = 0;
            health = 0;
            fullLives = 0;
            System.out.println("Game Over hahaha looser !");
            body.setActive(false);
        }else {
            if(fullLives - health <= 0) {
                lives = 1;
                health = fullLives;
            }else {
                if(fullLives - health * 2 <= 0) {
                    lives = 2;
                    health = fullLives - health;
                }
            }
        }
    }

    @Override
    public void attack() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            System.out.println("j'aaaaaataque !!");
            if (!ennemies.isEmpty()) {
                if (isFacingRight) {
                    for (Map<String, EnemyWithoutGraphics> e : ennemies) {
                        if (e.containsKey("Right")) {
                            e.get("Right").getDamage(weapon.getDamage());
                            System.out.println("Attaque à droite, dégâts infligés : " + weapon.getDamage());
                        }
                    }
                } else {
                    for (Map<String, EnemyWithoutGraphics> e : ennemies) {
                        if (e.containsKey("Left")) {
                            e.get("Left").getDamage(weapon.getDamage());
                            System.out.println("Attaque à gauche, dégâts infligés : " + weapon.getDamage());
                        }
                    }
                }
            } else {
                System.out.println("Aucun ennemi trouvé à " + (isFacingRight ? "droite" : "gauche") + " !");
            }
        }
    }

    @Override
    public void update(float delta) {
        if (body.isActive()) {
            falling(delta);

            if (isJumping) {
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y + (1500) * delta));
                isJumping = false;
            }

            if (movingRight) {
                body.setLinearVelocity(speed, body.getLinearVelocity().y - 175 * delta);
            } else if (movingLeft) {
                body.setLinearVelocity(-speed, body.getLinearVelocity().y - 175 * delta);
            } else {
                float currentVelocityX = body.getLinearVelocity().x;
                if (currentVelocityX > 0) {
                    // Ralentir le mouvement à droite
                    body.setLinearVelocity(currentVelocityX - 2.5f, body.getLinearVelocity().y - 175 * delta);
                } else if (currentVelocityX < 0) {
                    // Ralentir le mouvement à gauche
                    body.setLinearVelocity(Math.min(currentVelocityX + 2.5f, 0), body.getLinearVelocity().y - 175 * delta);
                }
            }
        }

    }

    private void falling(float delta) {
        if (isJumping || canJump)
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y - 175 * delta));
    }

    @Override
    public void moveToRight() {
        movingRight = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if (movingRight)
            isFacingRight = true;
    }

    @Override
    public void moveToLeft() {
        movingLeft = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        if (movingLeft)
            isFacingRight = false;
    }

    public void canJump() {
        canJump = true;
        nbJump = 2;
        timer = 0;
    }

    public void jump(float delta) {
        System.out.println(body.getLinearVelocity().y);
        switch (nbJump){
            case 2, 1:
                if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) ||
                    Gdx.input.isKeyPressed(Input.Keys.UP)) && canJump && timer <= 1f && nbJump > 0) {
                    timer += delta;
                    isJumping = true;
                }
                if(timer >= 1f){
                    isJumping = false;
                    if(!(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) ||
                        Gdx.input.isKeyPressed(Input.Keys.UP))){
                        nbJump--;
                        timer = 0;
                    }
                }
                break;
            case 0, -1, -2:
                body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y - 10));
                canJump = false;
                isJumping = false;
                break;
        }
    }

    public void addEnemy(String str, EnemyWithoutGraphics enemy) {
        Map<String, EnemyWithoutGraphics> eMap = new HashMap<>();
        eMap.put(str, enemy);
        ennemies.add(eMap);
    }

    public void removeEnemy(String str, EnemyWithoutGraphics enemy) {
        ennemies.removeIf(eMap -> eMap.get(str) == enemy);
    }

    public ArrayList<Map<String, EnemyWithoutGraphics>> getEnnemies() {
        return ennemies;
    }

    public float getFullLives() {
        return fullLives;
    }

    public int getLives() {
        return lives;
    }

    public int getNbJump() {
        return nbJump;
    }

    public Sword getWeapon() {
        return weapon;
    }

    public int getMoney() {
        return money;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }
}
