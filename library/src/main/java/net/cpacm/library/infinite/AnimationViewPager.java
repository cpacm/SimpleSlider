/*
 *  Copyright (C) 2016 cpacm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.cpacm.library.infinite;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.animation.OnAnimationListener;

/**
 * A {@link ViewPager} that allows define custom animation
 */
public class AnimationViewPager extends ViewPager {

    private int position = 0, prePositon = 0;
    private OnAnimationListener animationListener;
    private BaseSliderView slider, preSlider;
    private boolean animating = false;

    public AnimationViewPager(Context context) {
        super(context);
        initAnimationViewPager();
    }

    public AnimationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimationViewPager();
    }

    private void initAnimationViewPager() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset == 0) {
                    animateSliderEnd(position);
                    if (slider != null)
                        animationListener.onNextAnimationEnd(slider);
                }
            }

            @Override
            public void onPageSelected(int position) {
                prePositon = AnimationViewPager.this.position;
                AnimationViewPager.this.position = position;
                if (prePositon > position)
                    animateSliderStart(position, position + 1);
                if (prePositon < position)
                    animateSliderStart(position, position - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE) {
                    animateSliderEnd(position);
                }
            }
        });
    }

    private void animateSliderStart(int position, int prePositon) {
        if (animationListener == null) return;
        BaseSliderView slider = null, preSlider = null;
        if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter pagerAdapter = (InfinitePagerAdapter) getAdapter();
            slider = pagerAdapter.getSliderView(position);
            preSlider = pagerAdapter.getSliderView(prePositon);
        } else if (getAdapter() instanceof BaseSliderAdapter) {
            BaseSliderAdapter sliderAdapter = (BaseSliderAdapter) getAdapter();
            slider = sliderAdapter.getSliderView(position);
            preSlider = sliderAdapter.getSliderView(prePositon);
        }
        if (slider != null)
            animationListener.onNextAnimationStart(slider);
        if (preSlider != null)
            animationListener.onPreAnimationStart(preSlider);

    }

    private void animateSliderEnd(int position) {
        if (animationListener == null) return;
        if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter pagerAdapter = (InfinitePagerAdapter) getAdapter();
            if (slider == null) slider = pagerAdapter.getSliderView(position);
            else if (slider != pagerAdapter.getSliderView(position)) {
                preSlider = slider;
                slider = pagerAdapter.getSliderView(position);
            }
        } else if (getAdapter() instanceof BaseSliderAdapter) {
            BaseSliderAdapter sliderAdapter = (BaseSliderAdapter) getAdapter();
            if (slider == null) slider = sliderAdapter.getSliderView(position);
            else if (slider != sliderAdapter.getSliderView(position)) {
                preSlider = slider;
                slider = sliderAdapter.getSliderView(position);
            }
        }
        if (slider != null)
            animationListener.onNextAnimationEnd(slider);
        if (preSlider != null)
            animationListener.onPreAnimationEnd(preSlider);
        animating = false;
    }

    public OnAnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(OnAnimationListener animationListener) {
        this.animationListener = animationListener;
    }
}