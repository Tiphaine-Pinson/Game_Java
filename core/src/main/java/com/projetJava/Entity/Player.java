package com.projetJava.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.projetJava.AnimationManager;
import com.projetJava.AssetsManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Player extends Entity {

    private final float DECELERATE = 1.5f;

    private Sword weapon;
    private int money = 0, nbJump = 2, lives = 3;
    private float timer = 0, fullLives = health * lives;
    private ArrayList<Map<String, Enemy>> ennemies = new ArrayList<>();
    private Sprite sprite;
    private boolean isJumping = false, isFacingRight = true, movingRight = false, movingLeft = false,
            canJump = true;

    private TextureAtlas healthAtlas;
    private ArrayList<Sprite> heartSprites = new ArrayList<>();

    private AnimationManager animationManager;

    private Animation<TextureRegion> currentAnimation;

    private float animationTime = 0f;

    public Player(float health, float posX, float posY, float width, float height, int speed, World world,
            Sword weapon, TextureAtlas idleAtlas, TextureAtlas walkAtlas, TextureAtlas attackAtlas, float offsetX) {
        super(health, posX, posY, width, height, speed, world, offsetX);
        this.weapon = weapon;

        animationManager = new AnimationManager(
                AssetsManager.getTextureAtlas("Player/Animations/Idle/Idle.atlas"),
                AssetsManager.getTextureAtlas("Player/Animations/Walk/Walk.atlas"),
                AssetsManager.getTextureAtlas("Player/Animations/Attack/Attack.atlas"));

        TextureRegion defaultFrame = idleAtlas.findRegion("Idle");
        if (defaultFrame != null) {
            this.sprite = new Sprite(defaultFrame);
            this.sprite.setSize(width, height);
            this.sprite.setOriginCenter();
        } else {
            throw new RuntimeException("Erreur : NADA !");
        }

        this.healthAtlas = AssetsManager.getTextureAtlas("Player/Health.atlas");
        if (healthAtlas == null) {
            throw new RuntimeException("Erreur : L'atlas des cœurs (Health.atlas) n'a pas été chargé !");
        } else {
            System.out.println("ok c'est ok");
        }
        updateHeartSprites();
    }

    private void updateHeartSprites() {
        heartSprites.clear();
        for (int i = 0; i < lives; i++) {
            Sprite heartSprite = new Sprite(healthAtlas.findRegion("health_full"));
            heartSprite.setSize(78, 177);
            heartSprites.add(heartSprite);
        }
    }

    @Override
    public void update(SpriteBatch batch) {
        // J'ai implanté cette méthode car elle est présente dans Abstract
        // j'utilise une autre méthode en dessous pour mettre la caméra

        update(batch, null);
    }

    // Nouvelle méthode qui prend en compte la caméra
    public void update(SpriteBatch batch, OrthographicCamera camera) {

        animationTime += Gdx.graphics.getDeltaTime();

        if (currentAnimation == animationManager.attackAnimation
                && !animationManager.isAnimationFinished(animationTime)) {
        } else {
            if (movingRight || movingLeft) {
                currentAnimation = animationManager.walkAnimation;
            } else {
                currentAnimation = animationManager.idleAnimation;
            }
        }

        TextureRegion currentFrame = currentAnimation.getKeyFrame(animationTime);
        Vector2 position = body.getPosition();

        float drawX = isFacingRight ? position.x - width / 2 : position.x + width / 2;
        float drawWidth = isFacingRight ? width : -width;

        batch.draw(currentFrame, drawX, position.y - height / 2, drawWidth, height);

        if (camera != null) {
            for (int i = 0; i < heartSprites.size(); i++) {
                Sprite heart = heartSprites.get(i);
                heart.setPosition(camera.position.x - camera.viewportWidth / 2 + 10 + i * (heart.getWidth() + 5),
                        camera.position.y + camera.viewportHeight / 2 - heart.getHeight() - 10);
                heart.draw(batch);
            }
        }
    }

    public void takeDamage(int damage) {
        fullLives -= damage;
        if (fullLives <= 0) {
            lives = 0;
            health = 0;
            fullLives = 0;
            System.out.println("Game Over hahaha looser !");
            body.setActive(false);
        } else {
            if (fullLives - health <= 0) {
                lives = 1;
                health = fullLives;
            } else {
                if (fullLives - health * 2 <= 0) {
                    lives = 2;
                    health = fullLives - health;
                }
            }
        }
        updateHeartSprites();
    }

    @Override
    public void attack() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            System.out.println("j'aaaaaataque !!");
            animationManager.setAnimation(animationManager.attackAnimation);

            animationTime = 0f;
            currentAnimation = animationManager.attackAnimation;
            if (!ennemies.isEmpty()) {
                if (isFacingRight) {
                    for (Map<String, Enemy> e : ennemies) {
                        if (e.containsKey("Right")) {
                            e.get("Right").getDamage(weapon.getDamage());
                            System.out.println("Attaque à droite, dégâts infligés : " + weapon.getDamage());
                        }
                    }
                } else {
                    for (Map<String, Enemy> e : ennemies) {
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
            float currentVelocityX = body.getLinearVelocity().x;
            falling(delta);

            if (isJumping) {
                body.setLinearVelocity(new Vector2(currentVelocityX, 1000 - body.getWorld().getGravity().y));
                isJumping = false;
            }

            if (movingRight) {
                body.setLinearVelocity(speed, body.getLinearVelocity().y);
            } else if (movingLeft) {
                body.setLinearVelocity(-speed, body.getLinearVelocity().y);
            } else {
                if (currentVelocityX > 0) {
                    // Ralentir le mouvement à droite
                    body.setLinearVelocity(currentVelocityX - DECELERATE, body.getLinearVelocity().y);
                } else if (currentVelocityX < 0) {
                    // Ralentir le mouvement à gauche
                    body.setLinearVelocity(Math.min(currentVelocityX + DECELERATE, 0), body.getLinearVelocity().y);
                }
            }

            sprite.setFlip(!isFacingRight, false);
            Vector2 position = body.getPosition();
            sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
        }

    }

    private void falling(float delta) {
        if (isJumping || canJump)
            body.setLinearVelocity(
                    new Vector2(body.getLinearVelocity().x,
                            body.getLinearVelocity().y + body.getWorld().getGravity().y * delta * 5));
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && canJump && nbJump > 0 && !isJumping) {
            isJumping = true;
            nbJump--;
        }

        if (isJumping) {
            if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                isJumping = false;
            }
        }
    }

    public void addEnemy(String str, Enemy enemy) {
        Map<String, Enemy> eMap = new HashMap<>();
        eMap.put(str, enemy);
        ennemies.add(eMap);
    }

    public void removeEnemy(String str, Enemy enemy) {
        ennemies.removeIf(eMap -> eMap.get(str) == enemy);
    }

    public ArrayList<Map<String, Enemy>> getEnnemies() {
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
}
