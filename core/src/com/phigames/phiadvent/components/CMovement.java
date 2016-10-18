package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by labpos on 18/10/16.
 */

public class CMovement implements Component, Pool.Poolable {
    private Array<Vector2> path = new Array<Vector2>();
    public float velocity = 0f;
    public Vector2 velocityVec = new Vector2(0, 0);
    private Vector2 lastVelocityVec = new Vector2(0, -1);
    private boolean walking = false;
    private boolean stunned = false;
    private boolean attacking = false;
    public CMovement() { }

    @Override
    public void reset() {
        path = new Array<Vector2>();
        velocity = 0;
        velocityVec = new Vector2(0, 0);
        lastVelocityVec = new Vector2(0, -1);
        walking = false;
        stunned = false;
        attacking = false;
    }

    public void removeFirst() {
        if (path.size == 0) return;
        else if (path.size == 1) {
            walking = false;
        }
        path.removeIndex(0);
        if (velocityVec != null) lastVelocityVec = velocityVec.cpy();
        velocityVec = null;
    }

    public Array<Vector2> getPath() {
        return path;
    }

    public void addPathPoint(Vector2 p) {
        walking = true;
        path.add(p);
    }

    public Vector2 getLastVelocityVec() {
        return lastVelocityVec;
    }

    public void cancelMovement() {
        path.clear();
        if (velocityVec != null) lastVelocityVec = velocityVec.cpy();
        walking = false;
    }

    public boolean isWalking() {
        return walking && !stunned && !attacking;
    }

    public boolean isIdle() { return !walking && !stunned && !attacking; }

    public boolean isStunned() {
        return stunned;
    }
}