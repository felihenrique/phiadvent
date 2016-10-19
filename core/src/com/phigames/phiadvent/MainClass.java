package com.phigames.phiadvent;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.phigames.phiadvent.loaders.TextureCache;

public class MainClass extends ApplicationAdapter {
	
	@Override
	public void create () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        SceneManager.loadScene("maps/principal.tmx");
	}

	@Override
	public void render () {
        SceneManager.getWorld().update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
        TextureCache.clear();
	}
}
