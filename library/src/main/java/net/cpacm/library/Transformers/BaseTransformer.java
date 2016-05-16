package net.cpacm.library.Transformers;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * This is all transformers father.
 * <p/>
 * BaseTransformer implement ViewPager.PageTransformer
 * which is just same as {@link android.support.v4.view.ViewPager.PageTransformer}.
 * <p/>
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
        onTransform(view, position);
    }

}