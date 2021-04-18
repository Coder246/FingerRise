package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FingerPoint {

    private final Color color;
    private float x = 0;
    private float y = 0;
    private int pointer = -1;
    private static final float radius = Gdx.graphics.getWidth() / 8f / 2;
    private static final boolean[] globalUsedFingers = {false,false,false,false,false,false,false,false,false,false};

    public FingerPoint(float x, float y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, radius);
    }

    void update() {
        if (pointer != -1) {
            if (Gdx.input.isTouched(pointer)) {
                float newX = Gdx.input.getX(pointer) + radius / 2;
                float newY = Gdx.graphics.getHeight() - Gdx.input.getY(pointer) + radius / 2;
                if(Math.abs(newX-this.x)<300&&Math.abs(newY-this.y)<300) {
                    this.x = newX;
                    this.y = newY;
                }else{
                    globalUsedFingers[pointer] = false;
                    pointer = -1;
                }

            } else {
                globalUsedFingers[pointer] = false;
                pointer = -1;
            }
        } else {
            if (Gdx.input.justTouched()) {
                int tempPointerValue = lowestPossiblePointer(this.x,this.y);
                if (tempPointerValue != -1) {
                    System.out.println(tempPointerValue);
                        globalUsedFingers[tempPointerValue] = true;
                        this.pointer = tempPointerValue;

                }

            }


        }

    }

    private static int lowestPossiblePointer(float x,float y) {
        for(int i=0;i<globalUsedFingers.length;i++) {
            if(!globalUsedFingers[i]&&isPointerRange(i,x,y)) {
                return i;
            }
        }
        return -1;

    }

    private static boolean isPointerRange (int pointer,float x,float y) {
        return  (Collision.object(x - radius + 20, y - radius - 20, radius * 2 + 40, radius * 2 + 40, Gdx.input.getX(pointer), Gdx.graphics.getHeight() - Gdx.input.getY(pointer), 3, 3));
    }

}
