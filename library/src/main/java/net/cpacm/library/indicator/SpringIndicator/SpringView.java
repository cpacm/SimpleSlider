/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cpacm.library.indicator.SpringIndicator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by chenupt@gmail.com on 2015/1/31.
 * Description : Draw a head point and foot point.
 */
public class SpringView extends View {

    private Paint paint;
    private Path path;

    private Point headPoint;
    private Point footPoint;

    private boolean cycling = false;

    public SpringView(Context context) {
        this(context, null);
    }

    public SpringView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAlpha(0);

        headPoint = new Point();
        footPoint = new Point();

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
    }

    private void makePath() {

        float headOffsetX = (float) (headPoint.getRadius() * Math.sin(Math.atan((footPoint.getY() - headPoint.getY()) / (footPoint.getX() - headPoint.getX()))));
        float headOffsetY = (float) (headPoint.getRadius() * Math.cos(Math.atan((footPoint.getY() - headPoint.getY()) / (footPoint.getX() - headPoint.getX()))));

        float footOffsetX = (float) (footPoint.getRadius() * Math.sin(Math.atan((footPoint.getY() - headPoint.getY()) / (footPoint.getX() - headPoint.getX()))));
        float footOffsetY = (float) (footPoint.getRadius() * Math.cos(Math.atan((footPoint.getY() - headPoint.getY()) / (footPoint.getX() - headPoint.getX()))));

        float x1 = headPoint.getX() - headOffsetX;
        float y1 = headPoint.getY() + headOffsetY;

        float x2 = headPoint.getX() + headOffsetX;
        float y2 = headPoint.getY() - headOffsetY;

        float x3 = footPoint.getX() - footOffsetX;
        float y3 = footPoint.getY() + footOffsetY;

        float x4 = footPoint.getX() + footOffsetX;
        float y4 = footPoint.getY() - footOffsetY;

        float anchorX = (footPoint.getX() + headPoint.getX()) / 2;
        float anchorY = (footPoint.getY() + headPoint.getY()) / 2;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x3, y3);
        path.lineTo(x4, y4);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x1, y1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        makePath();
        if (!isCycling())
            canvas.drawPath(path, paint);
        canvas.drawCircle(headPoint.getX(), headPoint.getY(), headPoint.getRadius(), paint);
        canvas.drawCircle(footPoint.getX(), footPoint.getY(), footPoint.getRadius(), paint);
        super.onDraw(canvas);
    }

    public void animCreate() {
        setPivotX(getHeadPoint().getX());
        setPivotY(getFootPoint().getY());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator oaX = ObjectAnimator.ofFloat(this, "scaleX", 0.3f, 1f);
        ObjectAnimator oaY = ObjectAnimator.ofFloat(this, "scaleY", 0.3f, 1f);
        ObjectAnimator oaA = ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1f);
        animatorSet.play(oaX).with(oaY).with(oaA);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setStartDelay(300);
        animatorSet.start();
    }

    public Point getHeadPoint() {
        return headPoint;
    }

    public Point getFootPoint() {
        return footPoint;
    }

    public void setIndicatorColor(int color) {
        paint.setColor(color);
    }

    public int getIndicatorColor() {
        return paint.getColor();
    }

    public boolean isCycling() {
        return cycling;
    }

    public void setCycling(boolean cycling) {
        this.cycling = cycling;
    }
}
