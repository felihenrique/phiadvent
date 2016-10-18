package com.phigames.phiadvent.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by labpos on 18/10/16.
 */

public class AnimationCache {
    private static ObjectMap<String, Animation> animMap =
            new ObjectMap<String, Animation>();

    public static Animation get(String name) {
        return animMap.get(name);
    }

    public static void add(String name) {
        if(animMap.containsKey(name)) return;
        String path = "animation/" + name + ".json";

        FileHandle file = Gdx.files.internal(path);
        Json json = new Json();
        AnimationData data = json.fromJson(AnimationData.class, file);
        Texture texture = TextureCache.load(data.textureName, false);
        Array<TextureRegion> textureRegions = new Array<TextureRegion>(data.frames.length);
        for (short i = 0; i < data.frames.length; i++) {
            int x = data.cellWidth * data.frames[i][0];
            int y = data.cellHeight * data.frames[i][1];
            textureRegions.add(new TextureRegion(texture, x, y, data.cellWidth, data.cellHeight));
        }
        Animation.PlayMode pm;
        switch (data.animationPlayMode) {
            case 1:
                pm = Animation.PlayMode.LOOP;
                break;
            case 2:
                pm = Animation.PlayMode.LOOP_PINGPONG;
                break;
            default:
                pm = Animation.PlayMode.NORMAL;
        }

        animMap.put(name, new Animation(data.frameDuration, textureRegions, pm));
    }

    public static void unload(String name) {
        animMap.remove(name);
    }
}

/* Exemplo de animação em json

{
    "textureName": "textures/player.png",
    "frameDuration": 2.0,
    "cellWidth": 32,
    "cellHeight: 48,
    "frames": [
     [0, 1],
     [0, 2],
     [0, 3],
     [0, 4]
    ],
    "animationPlayMode": 0
}

 */

