package com.cpacm.library;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.cpacm.library.infinite.FixedSpeedScroller;
import com.cpacm.library.infinite.InfinitePagerAdapter;

import java.lang.reflect.Field;

/**
 * <p>
 *
 * @author cpacm 2018/5/18
 */
public class SimpleViewPager extends ViewPager {

    private final static int MIN_CYCLE_COUNT = 3;
    private final static int DEFAULT_SCROLL_DURATION = 500;

    private int count;//item count
    private boolean autoScroll = false;
    private boolean scrollPositive = true;
    private boolean mIsDataSetChanged;

    private boolean disableDrawChildOrder = false;
    /**
     * the duration between scroll action
     */
    private long sliderDuration = 2000;
    // Flag for invalidate transformer side scroll when use setCurrentItem() method
    private boolean isInitialItem;

    private final Handler autoScrollHandler = new Handler();
    private final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (!autoScroll) return;
            setCurrentItem(getRealItem() + (scrollPositive ? 1 : -1));
            autoScrollHandler.postDelayed(this, sliderDuration);
        }
    };
    private InfinitePagerAdapter infinitePagerAdapter;

    public SimpleViewPager(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SimpleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        infinitePagerAdapter = new InfinitePagerAdapter();
        super.setAdapter(infinitePagerAdapter);
    }

    /**
     * speed settings for page
     *
     * @param period
     * @param interpolator
     */
    public void setSliderTransformDuration(int period, Interpolator interpolator) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), interpolator, period);
            mScroller.set(this, scroller);
        } catch (Exception e) {

        }
    }

    public void setPageTransformer(PageTransformer transformer) {
        setPageTransformer(true, transformer);
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, @Nullable PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        if (adapter == null) throw new NullPointerException("You have to set adapter");
        count = adapter.getCount();
        infinitePagerAdapter.setAdapter(adapter);
        notifyDataSetChanged();
        resetPager();
    }

    public void setSliderDuration(long sliderDuration) {
        this.sliderDuration = sliderDuration;
    }

    @Nullable
    public PagerAdapter getRealAdapter() {
        return infinitePagerAdapter.getRealAdapter();
    }

    @Nullable
    @Override
    public PagerAdapter getAdapter() {
        return infinitePagerAdapter;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
            if (autoScroll) {
                autoScrollHandler.removeCallbacks(autoScrollRunnable);
            }
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
            if (autoScroll) {
                startAutoScroll(scrollPositive);
            }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(final boolean hasWindowFocus) {
        if (hasWindowFocus) {
            invalidateTransformer();
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }


    @Override
    protected void onDetachedFromWindow() {
        stopAutoScroll();
        super.onDetachedFromWindow();
    }

    @Override
    public void setCurrentItem(final int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(final int item, final boolean smoothScroll) {
        // Flag for invalidate transformer side scroll when use setCurrentItem() method
        isInitialItem = true;
        super.setCurrentItem(getVirtualCurrentItem(item), smoothScroll);
    }


    //scroll to next item
    public void scrollToNextItem() {
        setCurrentItem(getRealItem() + 1);
    }

    //scroll to previous item
    public void scrollToPreItem() {
        setCurrentItem(getRealItem() - 1);
    }

    // Set current item where you put original adapter position and this method calculate nearest
    // position to scroll from center if at first initial position or nearest position of old position
    public int getVirtualCurrentItem(final int item) {
        if (getRealAdapter() == null) return item;

        final int count = getRealAdapter().getCount();
        return getCurrentItem() + Math.min(count, item) - getRealItem();
    }

    public void resetPager() {
        if (infinitePagerAdapter.isInfiniteEnable() && infinitePagerAdapter.getRealCount() > MIN_CYCLE_COUNT) {
            super.setCurrentItem(((infinitePagerAdapter.getCount() / 2) / count) * count);
        } else {
            setCurrentItem(0);
        }
        postInvalidateTransformer();
    }

    /**
     * 返回真实位置
     *
     * @return
     */
    public int getRealItem() {
        return infinitePagerAdapter.getRealPosition(getCurrentItem());
    }

    /**
     * 当数据更新时调用此方法刷新
     */
    public void notifyDataSetChanged() {
        infinitePagerAdapter.notifyDataSetChanged();
        mIsDataSetChanged = true;
        postInvalidateTransformer();
    }

    public void invalidateTransformer() {
        if (infinitePagerAdapter != null && count > 0) {
            if (beginFakeDrag()) {
                fakeDragBy(0f);
                endFakeDrag();
            }
        }
    }

    public void postInvalidateTransformer() {
        post(new Runnable() {
            @Override
            public void run() {
                invalidateTransformer();
            }
        });
    }

    // Start auto scroll
    public void startAutoScroll(final boolean scrollPositive) {
        autoScroll = true;
        this.scrollPositive = scrollPositive;

        autoScrollHandler.removeCallbacks(autoScrollRunnable);
        autoScrollHandler.postDelayed(autoScrollRunnable, sliderDuration);
    }

    // Stop auto scroll
    public void stopAutoScroll() {
        if (!autoScroll) return;
        autoScroll = false;
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
    }

    public boolean isInfiniteEnable() {
        return infinitePagerAdapter.isInfiniteEnable();
    }

    public void setInfiniteEnable(boolean enableInfinite) {
        infinitePagerAdapter.setInfiniteEnable(enableInfinite);
    }

    @Override
    public void setOverScrollMode(final int overScrollMode) {
        super.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public int getCount() {
        return count;
    }

    @Override
    protected void setChildrenDrawingOrderEnabled(final boolean enabled) {
        if (disableDrawChildOrder) {
            super.setChildrenDrawingOrderEnabled(false);
            return;
        }
        super.setChildrenDrawingOrderEnabled(enabled);

    }

    public void setDisableDrawChildOrder(boolean disableDrawChildOrder) {
        this.disableDrawChildOrder = disableDrawChildOrder;
    }

    public boolean ismIsDataSetChanged() {
        return mIsDataSetChanged;
    }

    public boolean isInitialItem() {
        return isInitialItem;
    }

    public void setInitialItem(boolean initialItem) {
        this.isInitialItem = initialItem;
    }
}
