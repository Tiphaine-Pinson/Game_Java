package com.projetJava;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationManager {

    public Animation<TextureRegion> idleAnimation;
    public Animation<TextureRegion> walkAnimation;
    public Animation<TextureRegion> attackAnimation;
    public Animation<TextureRegion> deathAnimation;

    private Animation<TextureRegion> currentAnimation;
    private float animationTime = 0f;

    public AnimationManager(TextureAtlas idleAtlas, TextureAtlas walkAtlas, TextureAtlas attackAtlas,
            TextureAtlas deathAtlas) {
        if (idleAtlas != null) {
            idleAnimation = createAnimation(idleAtlas, "Idle", 5f, Animation.PlayMode.LOOP);
        }
        if (walkAtlas != null) {
            walkAnimation = createAnimation(walkAtlas, "Walk", 0.1f, Animation.PlayMode.LOOP);
        }
        if (attackAtlas != null) {
            attackAnimation = createAnimation(attackAtlas, "Attack", 0.15f, Animation.PlayMode.NORMAL);
        }
        if (deathAtlas != null) {
            deathAnimation = createAnimation(deathAtlas, "Death", 0.1f, Animation.PlayMode.NORMAL);
        }
        currentAnimation = idleAnimation;
    }

    public AnimationManager(TextureAtlas idleAtlas, TextureAtlas walkAtlas, TextureAtlas attackAtlas) {
        this(idleAtlas, walkAtlas, attackAtlas, null);
    }

    private Animation<TextureRegion> createAnimation(TextureAtlas atlas, String regionName, float frameDuration,
            Animation.PlayMode playMode) {
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(regionName);
        if (regions.size == 0) {
            throw new IllegalArgumentException(regionName);
        }
        return new Animation<>(frameDuration, regions, playMode);
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        if (currentAnimation != animation) {
            currentAnimation = animation;
            animationTime = 0f;
        }
    }

    public TextureRegion getCurrentFrame(float delta) {
        animationTime += delta;
        return currentAnimation.getKeyFrame(animationTime);
    }

    public boolean isAnimationFinished(float animationTime) {
        return currentAnimation.isAnimationFinished(animationTime);
    }
}
