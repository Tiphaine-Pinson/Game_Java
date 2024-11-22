package com.projetJava;

import com.projetJava.Entity.Sword;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SwordTest {

    private Sword sword;

    @Before
    public void setUp() throws Exception {
        sword = new Sword(10, 20);
    }

    @Test
    public void testGetDamage() throws Exception {
        assertEquals(10, sword.getDamage());
    }

    @Test
    public void testSetDamage() throws Exception {
        sword.setDamage(8);
        assertEquals(8, sword.getDamage());
    }

    @Test
    public void testGetScope() throws Exception {
        assertEquals(20, sword.getScope(), 0.01);
    }

    @Test
    public void testSetScope() throws Exception {
        sword.setScope(15);
        assertEquals(15, sword.getScope(), 0.01);
    }

}
