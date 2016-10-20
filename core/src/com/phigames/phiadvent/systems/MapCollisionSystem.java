package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.phigames.phiadvent.components.CCollider;

/**
 * Created by labpos on 18/10/16.
 */

public class MapCollisionSystem extends IteratingSystem {

    public MapCollisionSystem() {
        super(Family.all(CCollider.class).get());
    }
    /* Passos para verificar a colisão:
        1 - verificar quantos tiles o collider ocupa tanto horizontal quanto vertical e somar 1
        2 - Montar os 4 loops para percorrer todos os tiles ao redor do collider
        3 -
        http://jonathanwhiting.com/tutorial/collision/
    */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
