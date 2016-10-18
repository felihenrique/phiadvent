package com.phigames.phiadvent.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by labpos on 18/10/16.
 */

public class Collision {
    public static boolean TestRectangle(float x1, float y1, float width1, float height1,
                                        float x2, float y2, float width2, float height2) {
        return (x1 < x2 + width2) && (x1 + width1 > width2) && (y1 < y2 + height2) && (height1 + y1 > y2);
    }
    public static boolean TestCircle(Vector2 center1, float radius1, Vector2 center2, float radius2) {
        return true;
    }
}
