package com.projetJava.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    protected float health, posX, posY, width, height;
    protected int speed;
    protected Body body;
    private Map<String, Entity> eMapRight = new HashMap<>();
    private Map<String, Entity> eMapLeft = new HashMap<>();

    public Entity(float health, float posX, float posY, float width, float height, int speed, World world) {
        this.health = health;
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.width = width;
        this.height = height;
        createEntity(world);
        createSensor();
    }

    public Entity(float health, float posX, float posY, float width, float height, int speed, World world,
            float offsetX) {
        this.health = health;
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.width = width;
        this.height = height;
        createEntity(world);
        createSensor(offsetX);
    }

    private void createEntity(World world) {
        // création du body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(width / 3);

        // Définir un filtre simple
        Filter filter = new Filter();
        filter.groupIndex = -1; // Tous les objets ayant ce même `groupIndex` ne se collisionneront pas

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        // création du body data
        Fixture fixtureBody = body.createFixture(fixtureDef);
        fixtureBody.setUserData(this);
        fixtureBody.setFilterData(filter);

        circleShape.dispose();
    }

    private void createSensor() {
        // création du capteur droit (pour repérer les ennemies proches)
        PolygonShape sensorShapeRight = new PolygonShape();
        sensorShapeRight.setAsBox(width / 3, height / 3, new Vector2((width + width) / 3, 0), 0);

        // création fixtures pour le sensor
        FixtureDef fixtureDefRight = new FixtureDef();
        fixtureDefRight.shape = sensorShapeRight;
        fixtureDefRight.isSensor = true;

        // création du body data
        Fixture fixtureSensorRight = body.createFixture(fixtureDefRight);
        eMapRight.put("inProximityRight", this);
        fixtureSensorRight.setUserData(eMapRight);
        sensorShapeRight.dispose();

        // Capteur gauche (uniquement sur la gauche du joueur)
        PolygonShape sensorShapeLeft = new PolygonShape();
        sensorShapeLeft.setAsBox(width / 3, height / 3, new Vector2(-width / 1.5f, 0), 0);

        FixtureDef fixtureDefLeft = new FixtureDef();
        fixtureDefLeft.density = 0f;
        fixtureDefLeft.friction = 0f;
        fixtureDefLeft.restitution = 0f;
        fixtureDefLeft.shape = sensorShapeLeft;
        fixtureDefLeft.isSensor = true;

        Fixture fixtureSensorLeft = body.createFixture(fixtureDefLeft);
        eMapLeft.put("inProximityLeft", this);
        fixtureSensorLeft.setUserData(eMapLeft);
        sensorShapeLeft.dispose();
    }

    private void createSensor(float offsetX) {
        // création du capteur droit
        PolygonShape sensorShapeRight = new PolygonShape();
        sensorShapeRight.setAsBox(((width / 3) * 0.9f) + offsetX, height / 3,
                new Vector2((((width + width) / 3) * 0.9f) + offsetX * 2, 0), 0);

        // création fixtures pour le sensor
        FixtureDef fixtureDefRight = new FixtureDef();
        fixtureDefRight.density = 0f;
        fixtureDefRight.friction = 0f;
        fixtureDefRight.restitution = 0f;
        fixtureDefRight.shape = sensorShapeRight;
        fixtureDefRight.isSensor = true;

        // création du body data
        Fixture fixtureSensorRight = body.createFixture(fixtureDefRight);
        eMapRight.put("inProximityRight", this);
        fixtureSensorRight.setUserData(eMapRight);
        sensorShapeRight.dispose();

        // création du capteur gauche (pour repérer les ennemies proches)
        PolygonShape sensorShapeLeft = new PolygonShape();
        sensorShapeLeft.setAsBox(width / 3, height / 3, new Vector2((width - width * 3) / 3, 0), 0);

        // création fixtures pour le sensor
        FixtureDef fixtureDefLeft = new FixtureDef();
        fixtureDefLeft.shape = sensorShapeLeft;
        fixtureDefLeft.isSensor = true;

        // création du body data
        Fixture fixtureSensorLeft = body.createFixture(fixtureDefLeft);
        eMapLeft.put("inProximityLeft", this);
        fixtureSensorLeft.setUserData(eMapLeft);
        sensorShapeRight.dispose();
    }

    public abstract void attack();

    public void getDamage(int damage) {
        health -= damage;
        if (health <= 0)
            health = 0;
    }

    public abstract void update(SpriteBatch batch);

    public abstract void update(float delta);

    public abstract void moveToRight();

    public abstract void moveToLeft();

    public float getHealth() {
        return health;
    }

    public Body getBody() {
        return body;
    }
}
