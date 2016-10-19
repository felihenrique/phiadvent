package com.phigames.phiadvent.loaders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.phigames.phiadvent.SceneManager;
import com.phigames.phiadvent.components.CSprite;

public class EntityFactory {
    private static Json json = new Json();

    public static Entity createEntity(String path, Vector2 position) {
        Entity ent = SceneManager.getWorld().createEntity();
        Component[] components = json.fromJson(Component[].class, Gdx.files.internal(path));
        for (Component c: components) {
            ent.add(c);
        }
        ent.getComponent(CSprite.class).position = position;
        SceneManager.getWorld().addEntity(ent);
        return ent;
    }

    public static Entity createPlayer(String path) {
        Entity ent = SceneManager.getWorld().createEntity();
        Component[] components = json.fromJson(Component[].class, Gdx.files.internal(path));
        for (Component c: components) {
            ent.add(c);
        }
        ent.getComponent(CSprite.class).position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        SceneManager.getWorld().addEntity(ent);
        return ent;
    }
}
