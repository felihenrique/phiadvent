package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by labpos on 18/10/16.
 */

public class CCamera implements Component, Pool.Poolable, Json.Serializable {
    public OrthographicCamera cam;
    public Entity entityToFollow;
    public float followSpeed;
    public CCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(w, h);
        cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
    }

    @Override
    public void reset() {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }

    @Override
    public void write(Json json) {

    }
}
