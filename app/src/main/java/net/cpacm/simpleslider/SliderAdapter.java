package net.cpacm.simpleslider;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.cpacm.library.slider.BaseSliderView;

import java.util.List;

/**
 * 滑动适配器
 * Auther: cpacm
 * Date: 2016/3/7 0007
 */
public class SliderAdapter extends PagerAdapter {

    private List<? extends BaseSliderView> viewList;

    public SliderAdapter(List<? extends BaseSliderView> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position).getView());
        return viewList.get(position).getView();
    }
}
