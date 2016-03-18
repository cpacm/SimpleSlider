package net.cpacm.library.slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import net.cpacm.library.R;

/**
 * 图片轮播图
 * Auther: cpacm
 * Date: 2016/3/7 0007
 */
public class ImageSliderView extends BaseSliderView {


    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;

    private ImageView imageView;

    public ImageSliderView(Context context) {
        super(context);
    }

    @Override
    public View setSliderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.default_image_slider, null);
        imageView = (ImageView) view.findViewById(R.id.image_item);
        if (mEmptyPlaceHolderRes != 0)
            imageView.setBackgroundResource(mEmptyPlaceHolderRes);
        bindSliderToPager(view);
        return view;
    }

    /**
     * the placeholder image when loading image
     *
     * @param resId Image resource id
     * @return
     */
    public ImageSliderView empty(int resId) {
        mEmptyPlaceHolderRes = resId;
        return this;
    }

    /**
     *
     * @return ImageView
     */
    public ImageView getImageView() {
        return imageView;
    }
}
