package com.projetJava.Entity;

public class Sword {

    private float scope;
    private int damage;

    public Sword(int damage, float scope) {
        this.damage = damage;
        this.scope = scope;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getScope() {
        return scope;
    }

    public void setScope(float scope) {
        this.scope = scope;
    }
}
