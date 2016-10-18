package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.phigames.phiadvent.components.CCollider;

/**
 * Created by labpos on 18/10/16.
 */

public class CollisionSystem extends IteratingSystem {

    public CollisionSystem() {
        super(Family.all(CCollider.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
