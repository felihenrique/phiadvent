package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.phigames.phiadvent.SceneManager;
import com.phigames.phiadvent.components.CCollider;
import com.phigames.phiadvent.components.CMovement;
import com.phigames.phiadvent.components.CSprite;
import com.phigames.phiadvent.loaders.MapLoader;
import com.phigames.phiadvent.math.Collision;

/**
 * Created by labpos on 18/10/16.
 */

public class MapCollisionSystem extends IteratingSystem
{
    public MapCollisionSystem() {
        super(Family.all(CCollider.class).get());
        mCollider = ComponentMapper.getFor(CCollider.class);
        mSprite = ComponentMapper.getFor(CSprite.class);
        mMovement = ComponentMapper.getFor(CMovement.class);
    }
    private ComponentMapper<CCollider> mCollider;
    private ComponentMapper<CSprite> mSprite;
    private ComponentMapper<CMovement> mMovement;
    private RenderSystem renderSystem;
    /* Passos para verificar a colisão:
        1 - verificar quantos tiles o collider ocupa tanto horizontal quanto vertical e somar 1
        2 - Montar os 4 loops para percorrer todos os tiles ao redor do collider
        3 -
        http://jonathanwhiting.com/tutorial/collision/
    */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (renderSystem == null) renderSystem = getEngine().getSystem(RenderSystem.class);
        final int[][] data = MapLoader.getCollisionData(renderSystem.getCurrentMapPath());

        final CCollider collider = mCollider.get(entity);
        final CSprite sprite = mSprite.get(entity);
        final CMovement movement = mMovement.get(entity);

        final int occup_x = (int)(collider.width / 32f) + 1;
        final int occup_y = (int)(collider.height / 32f) + 1;
        final int grid_x = (int)(sprite.position.x / 32f);
        final int grid_y = (int)(sprite.position.y / 32f);

        for (int i = grid_x; i < grid_x + occup_x; i++) {
            for (int j = grid_y; j < grid_y + occup_y; j++) {
                float tile_x = i * 32;
                float tile_y = j * 32;
                float px = sprite.position.x;
                float py = sprite.position.y;
                float w = collider.width;
                float h = collider.height;
                if (    data[i][j] == 1 &&
                        Collision.tescRect(tile_x, tile_y, 32, 32, sprite.position.x, sprite.position.y, w, h))
                {
                    Vector2 oldPosition = sprite.position.cpy().sub(movement.velocityVec);
                    sprite.position.set(oldPosition.x, oldPosition.y);

                    sprite.position.x += movement.velocityVec.x;
                    if (Collision.tescRect(tile_x, tile_y, 32, 32, sprite.position.x, sprite.position.y, w, h))
                    {
                        sprite.position.x = oldPosition.x;
                    }

                    sprite.position.y += movement.velocityVec.y;
                    if (Collision.tescRect(tile_x, tile_y, 32, 32, sprite.position.x, sprite.position.y, w, h))
                    {
                        sprite.position.y = oldPosition.y;
                    }
                }
            }
        }
    }
}
