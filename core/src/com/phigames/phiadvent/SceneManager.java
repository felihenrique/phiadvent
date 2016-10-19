package com.phigames.phiadvent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.phigames.phiadvent.components.CCamera;
import com.phigames.phiadvent.loaders.EntityFactory;
import com.phigames.phiadvent.systems.AnimUpdateSystem;
import com.phigames.phiadvent.systems.CameraSystem;
import com.phigames.phiadvent.systems.CollisionSystem;
import com.phigames.phiadvent.systems.MoveAnimSelectorSystem;
import com.phigames.phiadvent.systems.MovementSystem;
import com.phigames.phiadvent.systems.PlayerInputSystem;
import com.phigames.phiadvent.systems.RenderSystem;

public class SceneManager {
    private static CCamera cameraComponent;
    private static PooledEngine world;

    public static void initiliaze() {
        world = new PooledEngine();
        Entity camera = world.createEntity();
        cameraComponent = new CCamera();
        camera.add(cameraComponent);
        world.addEntity(camera);
        world.addSystem(new CameraSystem());
        world.addSystem(new RenderSystem());
    }

    public static void loadScene() {
        Entity player = EntityFactory.createPlayer();
        cameraComponent.entityToFollow = player;
        EntityFactory.createEnemy("entities/enemy.json");
        EntityFactory.createEnemy("entities/enemy2.json");
        world.addSystem(new AnimUpdateSystem());
        world.addSystem(new CollisionSystem());
        world.addSystem(new MoveAnimSelectorSystem());
        world.addSystem(new MovementSystem());
        world.addSystem(new PlayerInputSystem());
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
