package net.cpacm.library.transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * This is all transformers father.
 * <p>
 * BaseTransformer implement ViewPager.PageTransformer
 * which is just same as {@link android.support.v4.view.ViewPager.PageTransformer}.
 * <p>
 * if you want to make an acceptable transformer, please do not forget to extend from this class.
 */
public abstract class BaseTransformer implements ViewPager.PageTransformer {

    /**
     * Called each {@link #transformPage(View, float)}.
     *
     * @param view
     * @param position
     */
    protected abstract void onTransform(View view, float position);

    @Override
    public void transformPage(View view, float position) {
        //initLocation(view);
        onTransform(view, position);
    }

    private void initLocation(View view) {
        ViewHelper.setRotationX(view, 0);
        ViewHelper.setRotationY(view, 0);
        ViewHelper.setRotation(view, 0);
        ViewHelper.setScaleX(view, 1);
        ViewHelper.setScaleY(view, 1);
        ViewHelper.setPivotX(view, 0);
        ViewHelper.setPivotY(view, 0);
        ViewHelper.setTranslationY(view, 0);
        ViewHelper.setTranslationX(view, 0);
        ViewHelper.setAlpha(view, 1f);
    }

}