package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by philipe on 18/10/16.
 */

public class NameComponent implements Component, Pool.Poolable {
    public String name = "";
    @Override
    public void reset() {
        name = "";
    }
}
