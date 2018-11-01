package com.cpacm.library.infinite;

import android.database.DataSetObserver;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
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
    private boolean infiniteEnable = true;

    private PagerAdapter adapter;

    public InfinitePagerAdapter() {
    }

    public void setAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
    }

    public PagerAdapter getRealAdapter() {
        return this.adapter;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (adapter == null) return null;
        return adapter.getPageTitle(getRealPosition(position));
    }

    @Override
    public int getCount() {
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        if (getRealCount() < 3 || !infiniteEnable) return getRealCount();
        return VIRTUAL_ITEM_COUNT;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        if (adapter == null) return 0;
        return adapter.getCount();
    }

    /**
     * 返回用户设置的 adapter 上的真实位置
     *
     * @param virtualPosition
     * @return
     */
    public int getRealPosition(final int virtualPosition) {
        if (adapter == null || adapter.getCount() == 0) return virtualPosition;
        return virtualPosition % adapter.getCount();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (adapter == null) throw new UnsupportedOperationException(
                "Required adapter was null");
        return adapter.instantiateItem(container, getRealPosition(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (adapter == null) throw new UnsupportedOperationException(
                "Required adapter was null");
        adapter.destroyItem(container, getRealPosition(position), object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        if (adapter == null) super.startUpdate(container);
        adapter.startUpdate(container);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (adapter == null) super.finishUpdate(container);
        adapter.finishUpdate(container);
    }

    @Override
    public float getPageWidth(int position) {
        if (adapter == null) return super.getPageWidth(position);
        return adapter.getPageWidth(getRealPosition(position));
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (adapter == null) throw new UnsupportedOperationException(
                "Required adapter was null");
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        if (adapter == null) return;
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        if (adapter == null) return super.saveState();
        return adapter.saveState();
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (adapter == null) super.unregisterDataSetObserver(observer);
        else adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (adapter == null) super.registerDataSetObserver(observer);
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter != null) adapter.notifyDataSetChanged();
        super.notifyDataSetChanged();
    }

    public boolean isInfiniteEnable() {
        return infiniteEnable;
    }

    public void setInfiniteEnable(boolean infiniteEnable) {
        this.infiniteEnable = infiniteEnable;
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