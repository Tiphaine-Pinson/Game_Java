package com.projetJava;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Platform {
    private Body platformBody;

    public Platform(World world, int posX, int posY, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);

        platformBody = world.createBody(bodyDef);

        PolygonShape platformShape = new PolygonShape();
        platformShape.setAsBox(width / 2, height / 2);

        FixtureDef platformFixtureDef = new FixtureDef();
        platformFixtureDef.shape = platformShape;

        platformBody.createFixture(platformFixtureDef);

        platformShape.dispose();

        // Création du capteur au-dessus de la plateforme
        PolygonShape sensorShape = new PolygonShape();
        float sensorHeight = 0.1f;
        sensorShape.setAsBox(width / 2 - 5, sensorHeight, new Vector2(0, height / 2 + sensorHeight), 0);

        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true;

        Fixture sensorFixture = platformBody.createFixture(sensorFixtureDef);
        sensorFixture.setUserData(this);

        sensorShape.dispose();
    }

    public Body getPlatformBody() {
        return platformBody;
    }
}
