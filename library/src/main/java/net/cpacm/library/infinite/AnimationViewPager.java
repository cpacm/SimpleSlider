package net.cpacm.library.infinite;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import net.cpacm.library.BaseSliderAdapter;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.animation.OnAnimationListener;

/**
 * A {@link ViewPager} that allows define custom animation
 */
public class AnimationViewPager extends ViewPager {

    private int position = 0;
    private OnAnimationListener animationListener;
    private BaseSliderView slider, preSlider;
    private boolean animating = false;

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
                if (position == 0 && positionOffset == 0) {
                    getSlider(position);
                    if (slider != null)
                        animationListener.onNextAnimationEnd(slider);
                    if (preSlider != null)
                        animationListener.onPreAnimationEnd(preSlider);
                }
            }

            @Override
            public void onPageSelected(int position) {
                AnimationViewPager.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (animationListener == null) return;
                if (state == SCROLL_STATE_SETTLING || state == SCROLL_STATE_DRAGGING) {
                    if (animating) return;
                    animating = true;
                    getSlider(position + 1);
                    if (slider != null)
                        animationListener.onNextAnimationStart(slider);
                    if (preSlider != null)
                        animationListener.onPreAnimationStart(preSlider);
                } else if (state == SCROLL_STATE_IDLE) {
                    getSlider(position);
                    if (slider != null)
                        animationListener.onNextAnimationEnd(slider);
                    if (preSlider != null)
                        animationListener.onPreAnimationEnd(preSlider);
                    animating = false;
                }
            }
        });
    }

    private void getSlider(int position) {
        if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter pagerAdapter = (InfinitePagerAdapter) getAdapter();
            slider = pagerAdapter.getSliderView(position);
            if (position != 0) preSlider = pagerAdapter.getSliderView(position - 1);
        } else if (getAdapter() instanceof BaseSliderAdapter) {
            BaseSliderAdapter sliderAdapter = (BaseSliderAdapter) getAdapter();
            slider = sliderAdapter.getSliderView(position);
            if (position != 0) preSlider = sliderAdapter.getSliderView(position - 1);
        }
    }

    public OnAnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(OnAnimationListener animationListener) {
        this.animationListener = animationListener;
    }
}