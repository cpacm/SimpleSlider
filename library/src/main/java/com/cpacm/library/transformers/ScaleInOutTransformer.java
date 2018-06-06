package com.cpacm.library.transformers;

import android.view.View;

/**
 * <p>
 *
 * @author cpacm 2018/6/6
 */
public class ScaleInOutTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {

        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setPivotY(view.getHeight() / 2f);
        float scale = position < 0 ? 1f + position : 1f - position;
        view.setScaleX(scale);
        view.setScaleY(scale);

    }
}
