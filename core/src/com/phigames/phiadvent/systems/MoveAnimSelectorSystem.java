package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.phigames.phiadvent.components.CAnim;
import com.phigames.phiadvent.components.CMovement;

/**
 * Created by labpos on 18/10/16.
 */

public class MoveAnimSelectorSystem extends IteratingSystem {

    ComponentMapper<CMovement> mMovement;
    ComponentMapper<CAnim> mAnim;

    public MoveAnimSelectorSystem() {
        super(Family.all(CAnim.class).get());
        mMovement = ComponentMapper.getFor(CMovement.class);
        mAnim = ComponentMapper.getFor(CAnim.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final CMovement movement = mMovement.get(entity);
        final CAnim anim = mAnim.get(entity);
        if (movement.isWalking()) {
            if (movement.velocityVec == null) return;
            final float vel_x = movement.velocityVec.x;
            final float vel_y = movement.velocityVec.y;
            if (vel_x != 0 || vel_y != 0) {
                if (Math.abs(vel_x) > Math.abs(vel_y)) {
                    if (vel_x < 0) anim.setCurrent("walk_left");
                    else anim.setCurrent("walk_right");
                }
                else {
                    if (vel_y < 0) anim.setCurrent("walk_down");
                    else anim.setCurrent("walk_up");
                }
            }
        } else if (movement.isIdle()) {
            final float vel_x = movement.getLastVelocityVec().x;
            final float vel_y = movement.getLastVelocityVec().y;
            if (vel_x != 0 || vel_y != 0) {
                if (Math.abs(vel_x) > Math.abs(vel_y)) {
                    if (vel_x < 0) anim.setCurrent("idle_left");
                    else anim.setCurrent("idle_right");
                }
                else {
                    if (vel_y < 0) anim.setCurrent("idle_down");
                    else anim.setCurrent("idle_up");
                }
            }
        }

    }
}
