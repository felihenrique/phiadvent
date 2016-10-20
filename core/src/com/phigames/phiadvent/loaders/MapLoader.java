package com.phigames.phiadvent.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by labpos on 18/10/16.
 */

public class MapLoader {
    private static ObjectMap<String, TiledMap> maps = new ObjectMap<String, TiledMap>();
    private static ObjectMap<String, int[][]> collisionData = new ObjectMap<String, int[][]>();
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
            loadCollisionData(path);
            return map;
        }
    }

    public static int[][] getCollisionData(String path) {
        return collisionData.get(path);
    }

    private static void loadCollisionData(String path) {
        TiledMap map = maps.get(path);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("collision");
        if (layer == null) {
            Gdx.app.error("Error", "O mapa " + path + "nao tem informacoes de colisao!");
            return;
        }
        int[][] data = new int[map.getProperties().get("width", Integer.class)]
                [map.getProperties().get("height", Integer.class)];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = layer.getCell(i, j) == null ? 0 : 1;
            }
        }
        collisionData.put(path, data);
    }

    private static void imprimirMatriz(int[][] data) {
        System.out.print("{");
        for (int i = 0; i < data.length; i++) {
            System.out.print("{");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + ",");
            }
            System.out.print("},");
        }
        System.out.print("}");
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