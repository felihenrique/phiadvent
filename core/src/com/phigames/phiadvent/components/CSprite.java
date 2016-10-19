package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by labpos on 18/10/16.
 */

public class CSprite implements Component, Pool.Poolable {
    public Color sprColor = Color.WHITE;
    public Vector2 position = new Vector2(0, 0);
    public Vector2 scale = new Vector2(1, 1);
    public Vector2 anchor = new Vector2(0, 0);
    public float rotation = 0;
    public CSprite() {}

    @Override
    public void reset() {
        sprColor = Color.WHITE;
        position = new Vector2(1, 1);
        scale = new Vector2(1, 1);
        anchor = new Vector2(1, 1);
        rotation = 0;
    }

    public Vector2 getCenter()
    {
        return position.add(anchor);
    }
}
