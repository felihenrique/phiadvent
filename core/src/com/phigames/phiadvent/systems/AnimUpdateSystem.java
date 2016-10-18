package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.phigames.phiadvent.components.CAnim;

public class AnimUpdateSystem extends IteratingSystem {
    ComponentMapper<CAnim> mAnim;

    public AnimUpdateSystem() {
        super(Family.all(CAnim.class).get());
        mAnim = ComponentMapper.getFor(CAnim.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CAnim anim = mAnim.get(entity);
        anim.timeElapsed += deltaTime * anim.speedMultiplier;
    }
}
