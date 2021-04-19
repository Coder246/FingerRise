package de.ft.fingerrise;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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

    public static boolean objectwithrotation(float obj1_x, float obj1_y, float obj1_w, float obj1_h, float obj1_angle, float obj2_x, float obj2_y, float obj2_w, float obj2_h, float obj2_angle) {


        Polygon obj1 = new Polygon(new float[]{0, 0, obj1_w, 0, obj1_w, obj1_h, 0, obj1_h});
        Polygon obj2 = new Polygon(new float[]{0, 0, obj2_w, 0, obj2_w, obj2_h, 0, obj2_h});
        obj1.setOrigin(obj1_w/2, obj1_h/2);
        obj2.setOrigin(obj2_w/2, obj2_h/2);
        obj1.setPosition(obj1_x, obj1_y);
        obj2.setPosition(obj2_x, obj2_y);
        obj1.setRotation(obj1_angle);
        obj2.setRotation(obj2_angle);

        return Intersector.overlapConvexPolygons(obj1, obj2);

    }

    public static boolean circlewithrotation(float rectX, float rectY, float rectW, float rectH, float rectAngle, float circleX, float circleY, float circleRadius) {
        // Rotate circle's center point back

        double unrotatedCircleX = Math.cos(rectAngle) * (circleX - rectW/2) -
                Math.sin(rectAngle) * (circleY - rectH/2) + rectW/2;
        double unrotatedCircleY  = Math.sin(rectAngle) * (circleX - rectW/2) +
                Math.cos(rectAngle) * (circleY - rectH/2) + rectY/2;

// Closest point in the rectangle to the center of circle rotated backwards(unrotated)
        double closestX, closestY;

// Find the unrotated closest x point from center of unrotated circle
        if (unrotatedCircleX  < rectX)
            closestX = rectX;
        else if (unrotatedCircleX  > rectX + rectW)
            closestX = rectX + rectW;
        else
            closestX = unrotatedCircleX ;

// Find the unrotated closest y point from center of unrotated circle
        if (unrotatedCircleY < rectY)
            closestY = rectY;
        else if (unrotatedCircleY > rectY + rectH)
            closestY = rectY + rectH;
        else
            closestY = unrotatedCircleY;

        double distance = findDistance(unrotatedCircleX , unrotatedCircleY, closestX, closestY);
        return distance < circleRadius; // Collision
    }
    private static double findDistance(double fromX, double fromY, double toX, double toY){
        double a = Math.abs(fromX - toX);
        double b = Math.abs(fromY - toY);

        return Math.sqrt((a * a) + (b * b));
    }

}
