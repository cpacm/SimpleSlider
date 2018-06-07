package com.cpacm.library.infinite;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 * <p/>
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;
    private final static int VIRTUAL_ITEM_COUNT = 10_000_000;

    private final PagerAdapter adapter;

    public InfinitePagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
    }

    public PagerAdapter getRealAdapter() {
        return this.adapter;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return adapter.getPageTitle(getRealPosition(position));
    }

    @Override
    public int getCount() {
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        if (getRealCount() == 0) return 0;
        if (getRealCount() == 1) return 1;
        if (getRealCount() == 2) return 2;
        return VIRTUAL_ITEM_COUNT;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        return adapter.getCount();
    }

    /**
     * 返回用户设置的 adapter 上的真实位置
     *
     * @param virtualPosition
     * @return
     */
    public int getRealPosition(final int virtualPosition) {
        return virtualPosition % adapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return adapter.instantiateItem(container, getRealPosition(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        adapter.destroyItem(container, getRealPosition(position), object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public float getPageWidth(int position) {
        return adapter.getPageWidth(getRealPosition(position));
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
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        // Callback for invalidating transformer position
        //if (mOnNotifyDataSetChangedListener != null) mOnNotifyDataSetChangedListener.onChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        adapter.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
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