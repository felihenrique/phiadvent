package com.phigames.phiadvent.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by labpos on 18/10/16.
 */

public class TextureCache {
    private static ObjectMap<String, Texture> textureMap = new ObjectMap<String, Texture>();
    public static Texture load(String path, boolean ignoreCache) {
        if (!ignoreCache && textureMap.containsKey(path)) {
            return textureMap.get(path);
        } else {
            if (textureMap.containsKey(path)) textureMap.remove(path);
            Texture texture = new Texture(Gdx.files.internal(path));
            textureMap.put(path, texture);
            return texture;
        }
    }
    public static void unload(String path) {
        if (textureMap.containsKey(path)) {
            textureMap.get(path).dispose();
            textureMap.remove(path);
        }
    }
    public static void clear() {
        for (int i = 0; i < textureMap.size; i++) {
            ObjectMap.Entry<String, Texture> next = textureMap.iterator().next();
            next.value.dispose();
        }
        textureMap.clear();
    }
}
