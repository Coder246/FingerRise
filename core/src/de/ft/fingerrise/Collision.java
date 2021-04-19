package de.ft.fingerrise;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Collision {
    private final static Rectangle rec1 = new Rectangle();
    private final static Rectangle rec2 = new Rectangle();


    public static boolean object(float obj1_x, float obj1_y, float obj1_w, float obj1_h, float obj2_x, float obj2_y, float obj2_w, float obj2_h) {


        rec1.set(obj1_x, obj1_y, obj1_w, obj1_h);
        rec2.set(obj2_x, obj2_y, obj2_w, obj2_h);

        return rec1.overlaps(rec2);

    }

    public static boolean objectwithrotation(float obj1_x, float obj1_y, float obj1_w, float obj1_h, float obj1_angle, float obj2_x, float obj2_y, int obj2_w, int obj2_h, float obj2_angle) {


        Polygon obj1 = new Polygon(new float[]{0, 0, obj1_w, 0, obj1_w, obj1_h, 0, obj1_h});
        Polygon obj2 = new Polygon(new float[]{0, 0, obj2_w, 0, obj2_w, obj2_h, 0, obj2_h});
        obj1.setOrigin(0, 0);
        obj2.setOrigin(0, 0);
        obj1.setPosition(obj1_x, obj1_y);
        obj2.setPosition(obj2_x, obj2_y);
        obj1.setRotation(obj1_angle);
        obj2.setRotation(obj2_angle);

        return Intersector.overlapConvexPolygons(obj1, obj2);

    }

}
