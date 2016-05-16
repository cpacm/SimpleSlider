package net.cpacm.library.animation;

import net.cpacm.library.slider.BaseSliderView;

/**
 * @auther: cpacm
 * @date: 2016/5/13
 * @desciption: animation transformer
 */
public interface OnAnimationListener {
    void onNextAnimationStart(BaseSliderView slider);

    void onNextAnimationEnd(BaseSliderView slider);

    void onPreAnimationStart(BaseSliderView slider);

    void onPreAnimationEnd(BaseSliderView slider);
}
