package net.cpacm.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import net.cpacm.library.infinite.InfinitePagerAdapter;
import net.cpacm.library.slider.BaseSliderView;

/**
 * simple slider
 * Auther: cpacm
 * Date: 2016/3/8.
 */
public class SimpleSliderLayout extends RelativeLayout {

    private Context mContext;
    private InfinitePagerAdapter infinitePagerAdapter;
    private BaseSliderAdapter baseSliderAdapter;
    private ViewPager simpleViewPager;


    /**
     * the duration between animation.
     */
    private long sliderDuration = 3000;

    private Handler sliderHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == 0) {
                moveNextPosition(true);
                sliderHandler.sendEmptyMessageDelayed(0, sliderDuration);
            }
        }
    };


    public SimpleSliderLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleSliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mContext = context;
        View parent = LayoutInflater.from(mContext).inflate(R.layout.simple_slider_layout, this, true);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SimpleSliderLayout);

        simpleViewPager = (ViewPager) parent.findViewById(R.id.simple_slider_viewpager);
        baseSliderAdapter = new BaseSliderAdapter();
        infinitePagerAdapter = new InfinitePagerAdapter(baseSliderAdapter);
        setCycling(true);
        setAutoCycling(true);
    }

    /**
     * if cycling
     * 是否无限循环
     *
     * @param isCycling
     */
    private void setCycling(boolean isCycling) {
        if (isCycling) cycling();
        else stopCycling();
    }

    public void cycling() {
        simpleViewPager.setAdapter(infinitePagerAdapter);
    }

    public void stopCycling() {
        simpleViewPager.setAdapter(baseSliderAdapter);
    }

    /**
     * if auto cycling
     * 是否自动循环
     *
     * @param autoCycling
     */
    private void setAutoCycling(boolean autoCycling) {
        if (autoCycling)
            startAutoCycling();
        else
            stopAutoCycling();
    }

    public void startAutoCycling() {
        sliderHandler.removeMessages(0);
        sliderHandler.sendEmptyMessageDelayed(0, sliderDuration);
    }

    public void stopAutoCycling() {
        sliderHandler.removeMessages(0);
    }

    public void setSliderDuration(long sliderDuration) {
        this.sliderDuration = sliderDuration;
    }

    public <T extends BaseSliderView> void addSlider(T baseSlider) {
        baseSliderAdapter.addSlider(baseSlider);
    }

    public void removeSlider(int position) {
        baseSliderAdapter.removeSliderAt(position);
    }

    public <T extends BaseSliderView> void removeSlider(T baseSlider) {
        baseSliderAdapter.removeSlider(baseSlider);
    }

    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        if (listener == null) return;
        simpleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                listener.onPageScrolled(position % infinitePagerAdapter.getRealCount(), positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                listener.onPageSelected(position % infinitePagerAdapter.getRealCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                listener.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * move to prev slide.
     */
    public void movePrevPosition(boolean smooth) {
        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");
        simpleViewPager.setCurrentItem(simpleViewPager.getCurrentItem() - 1, smooth);
    }

    public void movePrevPosition() {
        movePrevPosition(true);
    }

    /**
     * move to next slide.
     */
    public void moveNextPosition(boolean smooth) {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");

        simpleViewPager.setCurrentItem(simpleViewPager.getCurrentItem() + 1, smooth);
    }

    private BaseSliderAdapter getRealAdapter() {
        PagerAdapter adapter = simpleViewPager.getAdapter();
        if (adapter != null && adapter instanceof InfinitePagerAdapter) {
            return ((InfinitePagerAdapter) adapter).getRealAdapter();
        }
        return (BaseSliderAdapter) adapter;
    }

    public void moveNextPosition() {
        moveNextPosition(true);
    }
}
