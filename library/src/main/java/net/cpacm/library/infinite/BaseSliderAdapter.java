package net.cpacm.library.infinite;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.cpacm.library.slider.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A slider adapter
 */
public class BaseSliderAdapter extends PagerAdapter {

    private List<BaseSliderView> mContents;
    private boolean hasRepeatView = false;

    public BaseSliderAdapter() {
        mContents = new ArrayList<>();
    }

    public <T extends BaseSliderView> void addSlider(T slider) {
        mContents.add(slider);
        notifyDataSetChanged();
    }

    public BaseSliderView getSliderView(int position) {
        if (position < 0 || position >= mContents.size()) {
            return null;
        } else {
            return mContents.get(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public <T extends BaseSliderView> void removeSlider(T slider) {
        if (mContents.contains(slider)) {
            mContents.remove(slider);
            notifyDataSetChanged();
        }
    }

    public void removeSliderAt(int position) {
        if (mContents.size() > position) {
            mContents.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeAllSliders() {
        mContents.clear();
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < 0 || position >= mContents.size()) {
            return null;
        } else {
            return mContents.get(position).getPageTitle();
        }
    }

    @Override
    public int getCount() {
        return mContents.size();
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
        BaseSliderView b = mContents.get(position);
        View v = b.getView();
        try {
            container.addView(v);
        } catch (Exception e) {
            container.removeView(v);
            container.addView(v);
        }
        return v;
    }
}
