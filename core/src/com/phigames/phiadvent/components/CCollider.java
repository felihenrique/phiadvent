package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by labpos on 18/10/16.
 */

public class CCollider implements Component, Pool.Poolable {
    public float width;
    public float height;

    @Override
    public void reset() {

    }
}
