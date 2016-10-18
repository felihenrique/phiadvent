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
    public static void loadScene() {
        world = new PooledEngine();

        Entity player = EntityFactory.createPlayer();

        Entity camera = world.createEntity();
        cameraComponent = new CCamera(player);
        camera.add(cameraComponent);
        world.addEntity(camera);

        EntityFactory.createEnemy();
        EntityFactory.createEnemy();

        world.addSystem(new AnimUpdateSystem());
        world.addSystem(new CameraSystem());
        world.addSystem(new CollisionSystem());
        world.addSystem(new MoveAnimSelectorSystem());
        world.addSystem(new MovementSystem());
        world.addSystem(new PlayerInputSystem());
        world.addSystem(new RenderSystem());
    }

    public static PooledEngine getWorld() {
        return world;
    }

    public static CCamera getCamera() {
        return cameraComponent;
    }
}
