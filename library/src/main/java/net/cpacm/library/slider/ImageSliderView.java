package net.cpacm.library.slider;

import android.content.Context;
import android.view.View;

/**
 * 图片轮播图
 * Auther: cpacm
 * Date: 2016/3/7 0007
 */
public class ImageSliderView extends BaseSliderView {

    /**
     * Error place holder image.
     */
    private int mErrorPlaceHolderRes;

    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;


    protected ImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        return null;
    }
}
