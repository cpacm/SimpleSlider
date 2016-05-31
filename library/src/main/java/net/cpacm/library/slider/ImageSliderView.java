package net.cpacm.library.slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import net.cpacm.library.R;

/**
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
    public View getSliderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.default_image_slider, null);
        imageView = (ImageView) view.findViewById(R.id.image_item);
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
        imageView.setImageResource(mEmptyPlaceHolderRes);
        return this;
    }

    /**
     * get ImageView for ImageLoader
     *
     * @return ImageView
     */
    public ImageView getImageView() {
        return imageView;
    }
}
