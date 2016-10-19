package com.phigames.phiadvent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.phigames.phiadvent.components.CCamera;
import com.phigames.phiadvent.components.CSprite;
import com.phigames.phiadvent.loaders.EntityFactory;
import com.phigames.phiadvent.systems.RenderSystem;

public class SceneManager {
    private static CCamera cameraComponent;
    private static PooledEngine world;
    private static boolean initialized = false;

    public static void initialiaze() {
        world = new PooledEngine();
        Entity camera = world.createEntity();
        cameraComponent = new CCamera();
        camera.add(cameraComponent);
        world.addEntity(camera);
        world.addSystem(new RenderSystem());
        initialized = true;
    }

    public static void loadScene(String path) {
        // TODO: Carregar aqui também todos os dados que persistem entre diferentes cenas, ex: playerprefs
        if (!initialized) initialiaze();
        RenderSystem rs = world.getSystem(RenderSystem.class);
        rs.setCurrentMap(path);

        MapObjects entities = rs.getCurrentMap().getLayers().get("entities").getObjects();
        // Configurações do jogador
        Entity player = EntityFactory.createPlayer("entities/player.json");
        float px = entities.get("player_spawn").getProperties().get("x", Float.class);
        float py = entities.get("player_spawn").getProperties().get("y", Float.class);
        player.getComponent(CSprite.class).position = new Vector2(px, py);
        cameraComponent.entityToFollow = player;
        // Carrega as entidades
        for (int i = 0; i < entities.getCount(); i++) {
            MapObject object = entities.get(i);
            if (object.getName().equals("enemy")) {
                float ex = object.getProperties().get("x", Float.class);
                float ey = object.getProperties().get("y", Float.class);
                EntityFactory.createEntity(object.getProperties().get("path", String.class), new Vector2(ex, ey));
            }
        }
        // Carrega os sistemas
        String[] activesystems = entities.get("world_configuration").getProperties().
                get("activesystems", String.class).split(";");
        for (int i = 0; i < activesystems.length; i++) {
            Object object = null;
            String fullPath = "com.phigames.phiadvent.systems." + activesystems[i];
            try {
                Class classDefinition = Class.forName(fullPath);
                object = classDefinition.newInstance();
            } catch (InstantiationException e) {
                System.out.println(e);
            } catch (IllegalAccessException e) {
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
            SceneManager.getWorld().addSystem((EntitySystem)object);
        }
    }
    /*
    Exemplo Json Cena:
    {
        name: "City of Souls",
        background_music: "audio/nice_music.ogg",
        map: "maps/city_of_souls.tmx",
        systems: [],
        entities: [],
        portals: [],
    }
    Exemplo Json Entidade:
    {
        components: [
            {
                class: "com.phigames.phiadvent.components.CAnim",
                content: "{currentAnim:idle_down,animations:[walk_up,walk_right,
                walk_left,walk_down,idle_down,idle_up,idle_right,idle_left],
                timeElapsed:0,speedMultiplier:1.3}"
            },
            {
                class:
            }
        ]
    }

     */

    public static PooledEngine getWorld() {
        return world;
    }

    public static CCamera getCamera() {
        return cameraComponent;
    }
}
