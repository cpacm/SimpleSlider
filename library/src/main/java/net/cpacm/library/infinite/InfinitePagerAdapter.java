package net.cpacm.library.infinite;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.cpacm.library.BaseSliderAdapter;
import net.cpacm.library.slider.BaseSliderView;

import java.util.Observable;
import java.util.Observer;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 * Thanks to: https://github.com/antonyt/InfiniteViewPager
 * <p/>
 * 通过设置count最大数使得viewpager可以无限向右滑动
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;

    private BaseSliderAdapter adapter;

    public InfinitePagerAdapter(BaseSliderAdapter adapter) {
        this.adapter = adapter;
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
                super.onChanged();
            }
        });
    }

    public BaseSliderAdapter getRealAdapter() {
        return this.adapter;
    }

    @Override
    public int getCount() {
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        if (getRealCount() == 0) return 0;
        if (getRealCount() == 1) return 1;
        return Integer.MAX_VALUE;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        return adapter.getCount();
    }

    public BaseSliderView getSliderView(int position) {
        return adapter.getSliderView(position % getRealCount());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (getRealCount() == 0) {
            return null;
        }
        int virtualPosition = position % getRealCount();
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (getRealCount() == 0) {
            return;
        }
        int virtualPosition = position % getRealCount();
        debug("destroyItem: real position: " + position);
        debug("destroyItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        adapter.destroyItem(container, virtualPosition, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    /*
     * End delegation
     */

    private void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}