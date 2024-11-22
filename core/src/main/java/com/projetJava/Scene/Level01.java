package com.projetJava.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.projetJava.AssetsManager;
import com.projetJava.Entity.BasicEnemy;
import com.projetJava.Entity.Enemy;
import com.projetJava.Entity.Player;
import com.projetJava.Entity.SwordEnemy;
import com.projetJava.Platform;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.ArrayList;
import java.util.Map;

public class Level01 extends Scene {

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private SpriteBatch batch = new SpriteBatch();
    private boolean isPaused = false;
    private BitmapFont font;

    private ShapeRenderer shapeRenderer;
    private FreeTypeFontGenerator generator;
    private BitmapFont gameFont;

    // C'est la liste des ennmis
    private ArrayList<Enemy> enemies;

    public Level01() {
        font = new BitmapFont(); // Police par défaut
        font.setColor(Color.WHITE); // Couleur du texte

        map = new TmxMapLoader().load("Niveau01/platformer.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        spriteBatch = new SpriteBatch();

        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(450, 350, 0);
        camera.update();

        shapeRenderer = new ShapeRenderer();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Cinzel-VariableFont_wght.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 54;
        parameter.color = Color.WHITE;
        gameFont = generator.generateFont(parameter);

        new Platform(world, -580, 300, 100, 600);
        new Platform(world, 460, -25, 2000, 25);
        new Platform(world, 2060, -150, 1200, 25);
        new Platform(world, 2660, 0, 100, 325);
        new Platform(world, 3525, 170, 2000, 25);
        new Platform(world, 5175, 325, 800, 25);
        new Platform(world, 2400, 0, 350, 25);
        new Platform(world, 6450, 400, 500, 25);

        enemies = new ArrayList<>();
        TextureAtlas basicIdleAtlas = AssetsManager.getTextureAtlas("Ennemy/Idle/Idle.atlas");
        TextureAtlas basicWalkAtlas = AssetsManager.getTextureAtlas("Ennemy/Walking/Walk.atlas");
        TextureAtlas basicAttackAtlas = AssetsManager.getTextureAtlas("Ennemy/Attack02/Attack.atlas");
        TextureAtlas basicDeathAtlas = AssetsManager.getTextureAtlas("Ennemy/Dying/Death.atlas");

        enemies.add(new BasicEnemy(2, 1350, 100, 175, 200, 50, world, basicIdleAtlas, basicWalkAtlas, basicAttackAtlas,
                basicDeathAtlas));
        enemies.add(new BasicEnemy(2, 2500, 0, 175, 200, 50, world, basicIdleAtlas, basicWalkAtlas, basicAttackAtlas,
                basicDeathAtlas));
        enemies.add(new BasicEnemy(2, 3500, 250, 175, 200, 50, world, basicIdleAtlas, basicWalkAtlas, basicAttackAtlas,
                basicDeathAtlas));
        enemies.add(new BasicEnemy(2, 4250, 250, 175, 200, 50, world, basicIdleAtlas, basicWalkAtlas, basicAttackAtlas,
                basicDeathAtlas));

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Object contactA = contact.getFixtureA().getUserData();
                Object contactB = contact.getFixtureB().getUserData();

                if ((contactA instanceof Platform && contactB instanceof Player) ||
                        (contactA instanceof Player && contactB instanceof Platform)) {
                    player.canJump();
                }

                if (contactA instanceof Map<?, ?> eMap && contactB instanceof Enemy enemy) {
                    if (eMap.containsKey("inProximityRight")) {
                        player.addEnemy("Right", enemy);
                        System.out.println("Enemy is in Right");
                        System.out.println("Enemy live : " + enemy.getHealth());
                    } else if (eMap.containsKey("inProximityLeft")) {
                        player.addEnemy("Left", enemy);
                        System.out.println("Enemy is in Left");
                        System.out.println("Enemy live : " + enemy.getHealth());
                    }
                } else if (contactB instanceof Map<?, ?> eMap && contactA instanceof Enemy enemy) {
                    if (eMap.containsKey("inProximityRight")) {
                        player.addEnemy("Right", enemy);
                        System.out.println("Enemy is in Right");
                        System.out.println("Enemy live : " + enemy.getHealth());
                    } else if (eMap.containsKey("inProximityLeft")) {
                        player.addEnemy("Left", enemy);
                        System.out.println("Enemy is in Left");
                        System.out.println("Enemy live : " + enemy.getHealth());
                    }
                }

                if (contactA instanceof Map<?, ?> eMap && contactB instanceof Player gamer) {
                    if (eMap.containsKey("inProximityRight")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityRight");
                        enemy.setPlayer("Right", gamer);
                        System.out.println("Player is in Right");
                        System.out.println("Player live : " + gamer.getHealth());
                    } else if (eMap.containsKey("inProximityLeft")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityLeft");
                        enemy.setPlayer("Left", gamer);
                        System.out.println("Player is in Left");
                        System.out.println("Player live : " + gamer.getHealth());
                    }
                } else if (contactB instanceof Map<?, ?> eMap && contactA instanceof Player gamer) {
                    if (eMap.containsKey("inProximityRight")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityRight");
                        enemy.setPlayer("Right", gamer);
                        System.out.println("Player is in Right");
                        System.out.println("Player live : " + gamer.getHealth());
                    } else if (eMap.containsKey("inProximityLeft")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityLeft");
                        enemy.setPlayer("Left", gamer);
                        System.out.println("Player is in Left");
                        System.out.println("Player live : " + gamer.getHealth());
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                Object contactA = contact.getFixtureA().getUserData();
                Object contactB = contact.getFixtureB().getUserData();

                if (contactA instanceof Map<?, ?> eMap && contactB instanceof Enemy enemy) {
                    if (eMap.containsKey("inProximityRight")) {
                        player.removeEnemy("Right", enemy);
                        System.out.println("Enemy is not in Right");
                    } else if (eMap.containsKey("inProximityLeft")) {
                        player.removeEnemy("Left", enemy);
                        System.out.println("Enemy is not in Left");
                    }
                } else if (contactB instanceof Map<?, ?> eMap && contactA instanceof Enemy enemy) {
                    if (eMap.containsKey("inProximityRight")) {
                        player.removeEnemy("Right", enemy);
                        System.out.println("Enemy is not in Right");
                    } else if (eMap.containsKey("inProximityLeft")) {
                        player.removeEnemy("Left", enemy);
                        System.out.println("Enemy is not in left");
                    }
                }

                if (contactA instanceof Map<?, ?> eMap && contactB instanceof Player gamer) {
                    if (eMap.containsKey("inProximityRight")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityRight");
                        enemy.removePlayer();
                        System.out.println("Player is not in Right");
                    } else if (eMap.containsKey("inProximityLeft")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityLeft");
                        enemy.removePlayer();
                        System.out.println("Player is not in Left");
                    }
                } else if (contactB instanceof Map<?, ?> eMap && contactA instanceof Player gamer) {
                    if (eMap.containsKey("inProximityRight")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityRight");
                        enemy.removePlayer();
                        System.out.println("Player is not in Right");
                    } else if (eMap.containsKey("inProximityLeft")) {
                        Enemy enemy = (Enemy) eMap.get("inProximityLeft");
                        enemy.removePlayer();
                        System.out.println("Player is not in Left");
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    @Override
    public void update() {
        if (!isPaused) {
            player.moveToRight();
            player.moveToLeft();
            player.attack();
            player.jump(Gdx.graphics.getDeltaTime());
            for (Enemy enemy : enemies) {
                enemy.update(Gdx.graphics.getDeltaTime());
                enemy.behavior(Gdx.graphics.getDeltaTime());
            }
        }
    }

    @Override
    public void render(float delta) {

        update();
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        pause();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.update(batch, camera);

        for (Enemy enemy : enemies) {
            enemy.update(batch);
        }

        player.update(Gdx.graphics.getDeltaTime());
        batch.end();

        camera.position.set(new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y), 0);
        camera.update();
        debugRenderer.render(world, camera.combined);
        if (isPaused) {
            world.step(0, 0, 0);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.6f);
            shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            shapeRenderer.end();

            batch.begin();
            GlyphLayout layout = new GlyphLayout();
            layout.setText(gameFont, "PAUSE");

            float textWidth = layout.width;
            float textHeight = layout.height;
            float centerX = camera.position.x - textWidth / 2;
            float centerY = camera.position.y + textHeight / 2;

            gameFont.draw(batch, layout, centerX, centerY);
            batch.end();
        } else {
            world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }
    }

    public void quit() {
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        gameFont.dispose();
        generator.dispose();

    }
}
