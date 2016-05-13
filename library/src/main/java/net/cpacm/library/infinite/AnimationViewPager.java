package net.cpacm.library.infinite;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import net.cpacm.library.BaseSliderAdapter;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.OnAnimationListener;

import static android.support.v4.view.ViewPager.*;

/**
 * A {@link ViewPager} that allows define custom animation
 */
public class AnimationViewPager extends ViewPager {

    private int position = 0;
    private OnAnimationListener animationListener;

    public AnimationViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public AnimationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    private void initViewPager() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AnimationViewPager.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                BaseSliderView slider, preSlider;
                if (getAdapter() instanceof InfinitePagerAdapter) {
                    InfinitePagerAdapter pagerAdapter = (InfinitePagerAdapter) getAdapter();
                    slider = pagerAdapter.getSliderView(position);
                    if (position != 0) preSlider = pagerAdapter.getSliderView(position - 1);
                } else if (getAdapter() instanceof BaseSliderAdapter) {
                    BaseSliderAdapter sliderAdapter = (BaseSliderAdapter) getAdapter();
                    slider = sliderAdapter.getSliderView(position);
                    if (position != 0) preSlider = sliderAdapter.getSliderView(position - 1);
                }
                if (state == SCROLL_STATE_IDLE) {

                } else if (state == SCROLL_STATE_SETTLING) {

                }
            }
        });
    }


}