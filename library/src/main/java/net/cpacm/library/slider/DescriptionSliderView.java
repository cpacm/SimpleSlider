package net.cpacm.library.slider;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.cpacm.library.R;

/**
 * @auther: cpacm
 * @date: 2016/5/26
 * @desciption: 配有文字的SlideView
 */
public class DescriptionSliderView extends BaseSliderView {

    private ImageView imageView;
    private int mEmptyPlaceHolderRes;
    private View titleLayout;
    private TextView titleView;

    public DescriptionSliderView(Context context) {
        super(context);
    }

    @Override
    public View getSliderView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.default_description_slider, null);
        imageView = (ImageView) view.findViewById(R.id.image_item);
        titleLayout = view.findViewById(R.id.slider_title_layout);
        titleView = (TextView) view.findViewById(R.id.slider_title);

        bindSliderToPager(view);
        return view;
    }

    public View getTitleLayout() {
        return titleLayout;
    }

    @Override
    public void setPageTitle(String pageTitle) {
        if (!TextUtils.isEmpty(pageTitle))
            titleView.setText(pageTitle);
        super.setPageTitle(pageTitle);
    }

    /**
     * the placeholder image when loading image
     *
     * @param resId Image resource id
     * @return
     */
    public DescriptionSliderView empty(int resId) {
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
