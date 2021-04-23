package de.ft.fingerrise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class FingerPoint {

    private final Color color;
    private float x;
    private float y;

    private final float initX;
    private final float initY;

    private boolean allowFingerDrag = true;

    private float offsetX;
    private float offsetY;
    private int pointer = -1;
    private static final float radius = Gdx.graphics.getWidth() / 8f / 2;
    protected static final boolean[] globalUsedFingers = {false, false, false, false, false, false, false, false, false, false};

    public FingerPoint(float x, float y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
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
                if (Math.abs(newX - this.x) < 500 && Math.abs(newY - this.y) < 500) {
                    if (LevelManager.levelStarted || Game.inMenu) {
                        if ((newX + this.offsetX - radius) > 0 && (newX + this.offsetX + radius) < Gdx.graphics.getWidth()) {
                            this.x = newX + this.offsetX;
                        }
                        if ((newY + this.offsetY - radius) > 0 && (newY + this.offsetY + radius) < Gdx.graphics.getHeight()) {
                            this.y = newY + this.offsetY;
                        }
                    }
                } else {
                    globalUsedFingers[pointer] = false;
                    pointer = -1;
                }

            } else {
                globalUsedFingers[pointer] = false;
                pointer = -1;
            }
        } else if (allowFingerDrag) {
            if (Gdx.input.justTouched()) {
                int tempPointerValue = lowestPossiblePointer(this.x, this.y);
                if (tempPointerValue != -1) {
                    Gdx.input.vibrate(30);
                    FingerRise.click.play();
                    globalUsedFingers[tempPointerValue] = true;
                    this.pointer = tempPointerValue;

                    this.offsetX = x - Gdx.input.getX(pointer) - radius / 2;
                    this.offsetY = y - (Gdx.graphics.getHeight() - Gdx.input.getY(pointer)) - radius / 2;

                }

            }


        }

    }

    private int lowestPossiblePointer(float x, float y) {
        for (int i = 0; i < globalUsedFingers.length; i++) {
            if (!globalUsedFingers[i] && isPointerRange(i, x, y)) {
                return i;
            }
        }
        return -1;

    }

    private boolean isPointerRange(int pointer, float x, float y) {
        return (object(x - radius - 20, y - radius - 20, radius * 2 + 40, radius * 2 + 40, Gdx.input.getX(pointer), Gdx.graphics.getHeight() - Gdx.input.getY(pointer), 3, 3));
    }

    Rectangle rec1 = new Rectangle();
    Rectangle rec2 = new Rectangle();


    public boolean object(float obj1_x, float obj1_y, float obj1_w, float obj1_h, float obj2_x, float obj2_y, float obj2_w, float obj2_h) {


        rec1.set(obj1_x, obj1_y, obj1_w, obj1_h);
        rec2.set(obj2_x, obj2_y, obj2_w, obj2_h);


        return rec1.overlaps(rec2);

    }

    public float getX() {
        return x;
    }

    public float getInitX() {
        return initX;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public static float getRadius() {
        return radius;
    }

    public void resetPosition() {
        this.x = this.initX;
        this.y = this.initY;
        if (this.pointer != -1) {
            FingerPoint.globalUsedFingers[this.pointer] = false;
        }
        this.pointer = -1;
    }

    public void looseFinger() {
        if (this.pointer != -1) {
            FingerPoint.globalUsedFingers[this.pointer] = false;
        }
        this.pointer = -1;
    }

    public float getInitY() {
        return initY;
    }

    public boolean ready() {
        return pointer != -1;
    }

    public void setAllowFingerDrag(boolean allow) {
        if (allow) {
            allowFingerDrag = true;
        } else {
            if (this.pointer != -1) {
                FingerPoint.globalUsedFingers[this.pointer] = false;
            }
            this.pointer = -1;
            allowFingerDrag = false;
        }

    }


}
