package net.cpacm.library.infinite;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.cpacm.library.slider.BaseSliderView;


/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 * Thanks to: https://github.com/antonyt/InfiniteViewPager
 * <p/>
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;

    private BaseSliderAdapter adapter;

    //用于维护viewpager的缓存
    private RecordData preData;
    private RecordData currentData;
    private RecordData nextData;

    private int currentPosition = -2;

    public InfinitePagerAdapter(BaseSliderAdapter adapter) {
        this.adapter = adapter;
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
                super.onChanged();
            }
        });
        preData = new RecordData(-1, null);
        currentData = new RecordData(-1, null);
        nextData = new RecordData(-1, null);
    }

    public BaseSliderAdapter getRealAdapter() {
        return this.adapter;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return adapter.getPageTitle(position % getRealCount());
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
        int prePosition = currentPosition - 1;
        int nextPosition = currentPosition + 1;
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // focus next
        if (position > nextPosition) {
            RecordData data = preData;
            preData = currentData;
            currentData = nextData;
            Object v = adapter.instantiateItem(container, virtualPosition);
            nextData = new RecordData(virtualPosition, v);
            currentPosition = position - 1;
            if (data.object != null) {
                destroyCacheItem(container, data.key, data.object);
            }
            return nextData.object;

        }
        // focus pre
        else if (position < prePosition) {
            RecordData data = nextData;
            nextData = currentData;
            currentData = preData;
            Object v = adapter.instantiateItem(container, virtualPosition);
            preData = new RecordData(virtualPosition, v);
            currentPosition = position + 1;
            if (data.object != null) {
                destroyCacheItem(container, data.key, data.object);
            }
            return preData.object;
        }
        return adapter.instantiateItem(container, virtualPosition);
    }

    public void destroyCacheItem(ViewGroup container, int position, Object object) {
        if (object == preData.object || object == currentData.object || object == nextData.object)
            return;
        adapter.destroyItem(container, position, object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (getRealCount() == 0) {
            return;
        }

    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */
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

    class RecordData {
        public Integer key;
        public Object object;

        public RecordData(Integer key, Object object) {
            this.key = key;
            this.object = object;
        }
    }
}