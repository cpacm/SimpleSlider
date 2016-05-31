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

package net.cpacm.library.animation;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;

import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.DescriptionSliderView;

/**
 * @auther: cpacm
 * @date: 2016/5/26
 */
public class DefaultDescriptionAnimation implements OnAnimationListener {

    private final long DURATION = 300;

    @Override
    public void onNextAnimationStart(BaseSliderView slider) {
        Log.d("simpleSlider", "onNextAnimationStart:" + slider.getPageTitle());
    }

    @Override
    public void onNextAnimationEnd(BaseSliderView slider) {
        Log.d("simpleSlider", "onNextAnimationEnd:" + slider.getPageTitle());
        DescriptionSliderView sliderView = (DescriptionSliderView) slider;
        if (sliderView.getTitleLayout().getVisibility() != View.VISIBLE)
            translateShowAnimate(sliderView.getTitleLayout());
    }

    @Override
    public void onPreAnimationStart(BaseSliderView slider) {
        Log.d("simpleSlider", "onPreAnimationStart:" + slider.getPageTitle());
    }

    @Override
    public void onPreAnimationEnd(BaseSliderView slider) {
        Log.d("simpleSlider", "onPreAnimationEnd:" + slider.getPageTitle());
        DescriptionSliderView sliderView = (DescriptionSliderView) slider;
        alphaHideAnimate(sliderView.getTitleLayout());
    }

    public void alphaHideAnimate(View v) {
        v.clearAnimation();
        v.setVisibility(View.INVISIBLE);
        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setDuration(DURATION);
        v.startAnimation(aa);
    }

    public void translateShowAnimate(View v) {
        v.setVisibility(View.VISIBLE);
        v.clearAnimation();
        TranslateAnimation ta = new TranslateAnimation(0, 0, v.getHeight(), 0);
        ta.setDuration(DURATION);
        v.startAnimation(ta);
    }
}
