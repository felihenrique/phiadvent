package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.phigames.phiadvent.SceneManager;
import com.phigames.phiadvent.components.CCamera;
import com.phigames.phiadvent.components.CMovement;
import com.phigames.phiadvent.components.CSprite;
import com.phigames.phiadvent.components.Player;
import com.phigames.phiadvent.math.PathFinder;

/**
 * Created by labpos on 18/10/16.
 */

public class PlayerInputSystem extends IteratingSystem {
    ComponentMapper<CMovement> mMovement;
    ComponentMapper<CSprite> mSpr;

    public PlayerInputSystem() {
        super(Family.all(Player.class).get());
        mMovement = ComponentMapper.getFor(CMovement.class);
        mSpr = ComponentMapper.getFor(CSprite.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final CMovement movement = mMovement.get(entity);
        final CSprite spr = mSpr.get(entity);
        final CCamera camera = SceneManager.getCamera();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            movement.cancelMovement();
            byte[][] data = new byte[][] { {0, 1}, {0, 1} };
            Vector3 temp = camera.cam.unproject(new Vector3(Gdx.input.getX(),
                    Gdx.input.getY(), 0));
            Vector2 mousePos = new Vector2(temp.x - 16, temp.y - 24);
            Vector2[] path = PathFinder.find(data, spr.position, mousePos);
            for (Vector2 p : path) {
                movement.addPathPoint(p);
            }
        }
    }
}
