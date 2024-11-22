package com.projetJava;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {

    private World world;

    @Before
    public void setUp() {
        // Initialiser un monde Box2D pour les tests
        world = new World(new Vector2(0, -9.8f), true);
    }

    @After
    public void tearDown() {
        // Libérer les ressources après les tests
        world.dispose();
    }

    @Test
    public void testPlatformCreation() {
        // Création d'une plateforme
        int posX = 10;
        int posY = 20;
        float width = 5.0f;
        float height = 2.0f;

        Platform platform = new Platform(world, posX, posY, width, height);

        // Vérifier que le corps a bien été créé
        Body platformBody = platform.getPlatformBody();
        assertNotNull(platformBody);

        // Vérifier la position du corps
        assertEquals(posX, platformBody.getPosition().x, 0.01f);
        assertEquals(posY, platformBody.getPosition().y, 0.01f);

        // Vérifier que le corps est statique
        assertEquals(BodyDef.BodyType.StaticBody, platformBody.getType());

        // Vérifier les fixtures
        boolean hasMainFixture = false;
        boolean hasSensorFixture = false;

        for (Fixture fixture : platformBody.getFixtureList()) {
            if (!fixture.isSensor()) {
                // Fixture principale
                hasMainFixture = true;
                assertEquals(0f, fixture.getDensity(), 0.01f);
                assertEquals(0.2f, fixture.getFriction(), 0.01f);
                assertEquals(0f, fixture.getRestitution(), 0.01f);
            } else {
                // Fixture capteur
                hasSensorFixture = true;
                assertEquals(platform, fixture.getUserData());
            }
        }

        assertTrue("Main fixture missing", hasMainFixture);
        assertTrue("Sensor fixture missing", hasSensorFixture);
    }

    @Test
    public void testPlatformSensorShape() {
        // Création d'une plateforme
        int posX = 10;
        int posY = 20;
        float width = 5.0f;
        float height = 2.0f;

        Platform platform = new Platform(world, posX, posY, width, height);

        Body platformBody = platform.getPlatformBody();

        // Vérifier la présence et la taille correcte du capteur
        boolean sensorShapeVerified = false;
        for (Fixture fixture : platformBody.getFixtureList()) {
            if (fixture.isSensor()) {
                PolygonShape shape = (PolygonShape) fixture.getShape();
                Vector2 sensorCenter = new Vector2();
                shape.getVertex(0, sensorCenter);

                // Vérification des dimensions du capteur
                assertEquals(width / 2, Math.abs(sensorCenter.x), 0.01f);
                sensorShapeVerified = true;
            }
        }

        assertTrue("Sensor shape not found or incorrect", sensorShapeVerified);
    }
}
