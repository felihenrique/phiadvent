package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.phigames.phiadvent.components.CMovement;
import com.phigames.phiadvent.components.CSprite;

/**
 * Created by labpos on 18/10/16.
 */


public class MovementSystem extends IteratingSystem {

    ComponentMapper<CSprite> mSprite;
    ComponentMapper<CMovement> mMovement;
    CSprite spr;
    CMovement mc;

    public MovementSystem()
    {
        super(Family.all(CMovement.class).get());
        mSprite = ComponentMapper.getFor(CSprite.class);
        mMovement = ComponentMapper.getFor(CMovement.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        spr = mSprite.get(entity);
        mc = mMovement.get(entity);
        Array<Vector2> path = mc.getPath();
        if (path.size == 0) return;
        if (spr.position.dst2(path.get(0)) < 10f) {
            mc.removeFirst();
            return;
        }
        mc.velocityVec = path.get(0).cpy().sub(spr.position).nor().scl(mc.velocity * deltaTime);
        spr.position = spr.position.add(mc.velocityVec);
    }
}
