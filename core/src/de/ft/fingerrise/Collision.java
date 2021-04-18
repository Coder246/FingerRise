package de.ft.fingerrise;

import com.badlogic.gdx.math.Rectangle;

public class Collision {
    private final static Rectangle rec1 = new Rectangle();
    private final static Rectangle rec2 = new Rectangle();


    public static boolean object(float obj1_x, float obj1_y, float obj1_w, float obj1_h, float obj2_x, float obj2_y, float obj2_w, float obj2_h) {


        rec1.set(obj1_x, obj1_y, obj1_w, obj1_h);
        rec2.set(obj2_x, obj2_y, obj2_w, obj2_h);

        return rec1.overlaps(rec2);

    }
}
