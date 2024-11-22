package com.projetJava;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.projetJava.Entity.Sword;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicEnemyTest {

    private BasicEnemyWithoutGraphics basicEnemy;
    private PlayerWithoutGraphics player;
    private World world;

    @Before
    public void setUp() {
        // Création des mocks
        world = new World(new Vector2(0, -15), true);
        Sword sword = new Sword(10, 20);
        player = new PlayerWithoutGraphics(100, 100, 50, 125, 150, 100, world,
                sword, sword.getScope());

        // Initialisation de l'ennemi avec des paramètres de test
        basicEnemy = new BasicEnemyWithoutGraphics(100f, 0f, 0f, 1f, 1f, 5, world);

    }

    @Test
    public void testMoveToRight() {
        // Vérifier que l'ennemi se déplace vers la droite
        basicEnemy.moveToRight();
        Vector2 velocity = basicEnemy.getBody().getLinearVelocity();
        assert (velocity.x == 5); // La vitesse de déplacement doit être 5 dans la direction X
    }

    @Test
    public void testMoveToLeft() {
        // Vérifier que l'ennemi se déplace vers la gauche
        basicEnemy.moveToLeft();
        Vector2 velocity = basicEnemy.getBody().getLinearVelocity();
        assert (velocity.x == -5); // La vitesse de déplacement doit être -5 dans la direction X
    }

    @Test
    public void testBehaviorWithNoPlayer() {
        // Simuler un comportement sans joueur
        basicEnemy.behavior(1f); // Delta temps fictif
        System.out.println(basicEnemy.getBody().getLinearVelocity().x);
        assert (basicEnemy.getBody().getLinearVelocity().x != 0); // L'ennemi devrait se déplacer
    }

    @Test
    public void testAttack() {
        // Simuler une attaque de l'ennemi
        basicEnemy.setPlayer("Right", player);

        basicEnemy.attack();

        // Vérifier que le joueur a bien pris des dégâts
        Assert.assertEquals(100 * 3 - basicEnemy.getDamage(), player.getFullLives(), 0.01);
    }

    @Test
    public void testBehaviorWithPlayer() {
        // Simuler un comportement avec un joueur proche
        basicEnemy.setPlayer("Right", player);

        // Effectuer plusieurs itérations de comportement de l'ennemi
        for (int i = 0; i < 3; i++) {
            basicEnemy.behavior(1f); // Delta fictif
        }

        // Vérifier que l'ennemi a attaqué après le temps écoulé
        Assert.assertEquals(100 * 3 - basicEnemy.getDamage(), player.getFullLives(), 0.01);
    }
}
