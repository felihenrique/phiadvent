package com.phigames.phiadvent.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by labpos on 18/10/16.
 */

public class MapLoader {
    private static ObjectMap<String, TiledMap> maps = new ObjectMap<String, TiledMap>();
    // TODO: Essa classe alem de armazenar o mapa ela cria a matriz de colisão, gera a lista de entidade que devem
    // TODO: ser instanciadas pelo GameManager (monstros, npcs, itens), lê a posição inicial do jogador e a lista de portais
    public static TiledMap load(String path) {
        if (maps.containsKey(path)) {
            return maps.get(path);
        } else {
            TmxMapLoader loader = new TmxMapLoader();
            TmxMapLoader.Parameters param = new TmxMapLoader.Parameters();
            param.textureMinFilter = Texture.TextureFilter.Nearest;
            param.textureMagFilter = Texture.TextureFilter.Nearest;
            TiledMap map = loader.load(path);
            maps.put(path, map);
            return map;
        }
    }
    public static void remove(String path) {
        if (maps.containsKey(path)) {
            maps.get(path).dispose();
            maps.remove(path);
        } else {
            Gdx.app.log("Not Found", "O mapa" + path + " não foi encontrado na lista");
        }
    }
}