package com.phigames.phiadvent.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.phigames.phiadvent.SceneManager;
import com.phigames.phiadvent.components.CAnim;
import com.phigames.phiadvent.components.CCamera;
import com.phigames.phiadvent.components.CSprite;
import com.phigames.phiadvent.loaders.MapLoader;

/**
 * Created by labpos on 18/10/16.
 */

public class RenderSystem extends EntitySystem {

    private SpriteBatch batch;
    private TiledMapRenderer mapRenderer;
    private static TiledMap currentMap;
    private static String currentMapPath;
    private ComponentMapper<CSprite> mSprite;
    private ComponentMapper<CAnim> mAnim;
    private ComponentMapper<CCamera> mCamera;
    private Family renderFamily;

    public RenderSystem() {
        initialize();
    }

    protected void initialize() {
        batch = new SpriteBatch();
        mSprite = ComponentMapper.getFor(CSprite.class);
        mAnim = ComponentMapper.getFor(CAnim.class);
        mCamera = ComponentMapper.getFor(CCamera.class);
        renderFamily = Family.all(CSprite.class).get();
    }

    public void setCurrentMap(String mapPath) {
        //TODO: Chamar função para atualizar a matriz de colisão do mapa
        if (currentMap != null) {
            currentMap.dispose();
            MapLoader.remove(currentMapPath);
        }
        currentMap = MapLoader.load(mapPath);
        currentMapPath = mapPath;
        mapRenderer = new OrthogonalTiledMapRenderer(currentMap);
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final CCamera camera = SceneManager.getCamera();
        final MapProperties pro = getCurrentMap().getProperties();

        Vector2 pos = mSprite.get(camera.entityToFollow).position;
        float pos_x = MathUtils.clamp(pos.x, Gdx.graphics.getWidth() / 2, pro.get("width", Integer.class) * 32 -
                Gdx.graphics.getWidth() / 2);
        float pos_y = MathUtils.clamp(pos.y, Gdx.graphics.getHeight() / 2, pro.get("height", Integer.class) * 32 -
                Gdx.graphics.getHeight() / 2);

        //camera.cam.position.lerp(new Vector3(pos_x, pos_y, 0), deltaTime * 10);
        //camera.cam.position.set((int)camera.cam.position.x, (int)camera.cam.position.y, 0);
        camera.cam.position.set((int)pos_x, (int)pos_y, 0);
        SceneManager.getCamera().cam.update();
        if (mapRenderer != null) {
            int[] layers = {0, 1, 2, 3};
            mapRenderer.setView(SceneManager.getCamera().cam);
            mapRenderer.render(layers);
        }

        batch.setProjectionMatrix(SceneManager.getCamera().cam.combined);
        batch.begin();
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(renderFamily);
        for (Entity e : entities) {
            renderEntity(e);
        }
        batch.end();

        if (mapRenderer != null) {
            int[] layersOver = {4, 5};
            mapRenderer.render(layersOver);
        }
    }

    private void renderEntity(Entity entity) {
        final CSprite spr = mSprite.get(entity);
        final CAnim anim = mAnim.get(entity);
        if (anim.getCurrent() == null) return;
        final TextureRegion region = anim.getCurrent().getKeyFrame(anim.timeElapsed);

        batch.setColor(spr.sprColor);
        batch.draw(region, (int)spr.position.x, (int)spr.position.y, spr.anchor.x, spr.anchor.y,
                region.getRegionWidth(), region.getRegionHeight(), spr.scale.x, spr.scale.y,
                spr.rotation);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        batch.dispose();
    }
}
