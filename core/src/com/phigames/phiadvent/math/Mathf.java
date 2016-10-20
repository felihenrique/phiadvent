package com.phigames.phiadvent.math;

/**
 * Created by philipe on 19/10/16.
 */

public class Mathf {
    public static float excludeRange(float value, float min, float max) {
        if (value > min && value < max) {
            return ((value - min) > (max - value)) ? min : max;
        } else {
            return value;
        }
    }
}
