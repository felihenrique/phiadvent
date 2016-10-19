package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.phigames.phiadvent.components.CCamera;
import com.phigames.phiadvent.components.CSprite;

/**
 * Created by labpos on 18/10/16.
 */

public class CameraSystem extends IteratingSystem {
    ComponentMapper<CCamera> mCamera;
    ComponentMapper<CSprite> mSpr;
    RenderSystem renderSystem;
    float last_x = 0;
    float last_y = 0;

    public CameraSystem() {
        super(Family.all(CCamera.class).get());
        mCamera = ComponentMapper.getFor(CCamera.class);
        mSpr = ComponentMapper.getFor(CSprite.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderSystem = getEngine().getSystem(RenderSystem.class);
        if (renderSystem.getCurrentMap() == null) return;

        final CCamera camera = mCamera.get(entity);
        if (camera.entityToFollow == null) return;

        final MapProperties pro = renderSystem.getCurrentMap().getProperties();
        Vector2 pos = mSpr.get(camera.entityToFollow).position;
        float pos_x = MathUtils.clamp(pos.x, Gdx.graphics.getWidth() / 2, pro.get("width", Integer.class) * 32 -
                Gdx.graphics.getWidth() / 2);
        float pos_y = MathUtils.clamp(pos.y, Gdx.graphics.getHeight() / 2, pro.get("height", Integer.class) * 32 -
                Gdx.graphics.getHeight() / 2);
        camera.cam.position.lerp(new Vector3((int)pos_x, (int)pos_y, 0), deltaTime * 7);
    }
}
