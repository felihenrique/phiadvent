package com.phigames.phiadvent.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.phigames.phiadvent.loaders.AnimationCache;

/**
 * Created by labpos on 18/10/16.
 */


public class CAnim implements Json.Serializable, Component, Pool.Poolable {
    private String currentAnim;
    private Array<String> animations = new Array<String>();
    public float timeElapsed = 0;
    public float speedMultiplier = 1;

    @Override
    public void reset() {
        currentAnim = null;
        animations = new Array<String>();
        timeElapsed = 0;
        speedMultiplier = 1;
    }

    public void setCurrent(String name) {
        if (currentAnim != null && currentAnim.equals(name)) return;
        currentAnim = name;
        timeElapsed = 0;
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        currentAnim = jsonData.get("currentAnim").asString();
        String[] temp = jsonData.get("animations").asStringArray();
        for (String t : temp) {
            addAnimation(t);
        }
        timeElapsed = jsonData.get("timeElapsed").asFloat();
        speedMultiplier = jsonData.get("speedMultiplier").asFloat();
    }

    @Override
    public void write(Json json) {
        json.writeValue("currentAnim", currentAnim);
        json.writeArrayStart("animations");
        for (int i = 0; i < animations.size; i++) {
            json.writeValue(animations.get(i));
        }
        json.writeArrayEnd();
        json.writeValue("timeElapsed", timeElapsed);
        json.writeValue("speedMultiplier", speedMultiplier);
    }

    public Animation getCurrent() {
        return AnimationCache.get(currentAnim);
    }

    public void addAnimation(String name) {
        animations.add(name);
        AnimationCache.add(name);
    }

}
