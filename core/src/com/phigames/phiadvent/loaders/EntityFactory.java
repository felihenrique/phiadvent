package com.phigames.phiadvent.loaders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.phigames.phiadvent.SceneManager;
import com.phigames.phiadvent.components.CAnim;
import com.phigames.phiadvent.components.CMovement;
import com.phigames.phiadvent.components.CSprite;
import com.phigames.phiadvent.components.Player;

/**
 * Created by labpos on 18/10/16.
 */


public class EntityFactory {
    private static Json json = new Json();

    public static Entity createEnemy() {
        Entity ent = SceneManager.getWorld().createEntity();

        CSprite spr = new CSprite();
        spr.position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        CMovement mc = new CMovement();
        mc.velocity = 40;

        final int variation = 500;
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));
        mc.addPathPoint(new Vector2(MathUtils.random() * variation, MathUtils.random() * variation));

        CAnim anim = json.fromJson(CAnim.class, Gdx.files.local("animation_handle/char.json"));

        ent.add(spr).add(mc).add(anim);

        SceneManager.getWorld().addEntity(ent);
        return ent;
    }

    public static Entity createPlayer() {
        Entity ent = SceneManager.getWorld().createEntity();

        CSprite spr = new CSprite();
        spr.position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        CMovement mc = new CMovement();
        mc.velocity = 60;

        CAnim anim = json.fromJson(CAnim.class, Gdx.files.local("animation_handle/char.json"));

        ent.add(spr).add(mc).add(anim).add(new Player());
        SceneManager.getWorld().addEntity(ent);

        return ent;
    }

}
