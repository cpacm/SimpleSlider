package net.cpacm.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
     * if cycling
     * 是否循环
     */
    private boolean isCycling;

    /**
     * if auto cycling
     * 是否自动循环
     */
    private boolean autoCycling;

    /**
     * the duration between animation.
     */
    private long sliderDuration = 3000;


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
        LayoutInflater.from(mContext).inflate(R.layout.simple_slider_layout, this, true);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SimpleSliderLayout);

        simpleViewPager = (ViewPager) findViewById(R.id.simple_slider_viewpager);
        baseSliderAdapter = new BaseSliderAdapter();
        infinitePagerAdapter = new InfinitePagerAdapter(baseSliderAdapter);
        simpleViewPager.setAdapter(infinitePagerAdapter);
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


}
