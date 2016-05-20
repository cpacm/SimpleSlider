package net.cpacm.simpleslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.Transformers.FlipPageViewTransformer;
import net.cpacm.library.Transformers.ZoomOutSlideTransformer;
import net.cpacm.library.indicator.SpringIndicator.SpringIndicator;
import net.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.ImageSliderView;
import net.cpacm.library.animation.OnAnimationListener;
import net.cpacm.library.slider.OnSliderClickListener;

public class MainActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    protected static final int[] ICONS = new int[]{
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);
        int[] ids = {R.mipmap.android, R.mipmap.html5, R.mipmap.github, R.mipmap.ios, R.mipmap.cpacm, R.mipmap.java};
        String[] strs = {"android", "h5", "github", "ios", "cpacm", "java"};
        for (int i = 0; i < strs.length; i++) {
            ImageSliderView imageSliderView = new ImageSliderView(getApplicationContext());
            imageSliderView.empty(R.mipmap.ic_launcher);
            //imageLoader.displayImage("", imageSliderView.getImageView());
            imageSliderView.getImageView().setBackgroundResource(ids[i]);
            imageSliderView.setPageTitle(strs[i]);
            imageSliderView.bundle(new Bundle());
            imageSliderView.getBundle()
                    .putString("type", strs[i]);
            imageSliderView.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Log.d("simpleSlider", slider.getBundle().getString("type"));
                }
            });
            simpleSliderLayout.addSlider(imageSliderView);
            simpleSliderLayout.setSliderTransformDuration(2000);
            simpleSliderLayout.setPageTransformer(new ZoomOutSlideTransformer());
        }

        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        simpleSliderLayout.setViewPagerIndicator(springIndicator);

        simpleSliderLayout.setAnimationListener(new OnAnimationListener() {
            @Override
            public void onNextAnimationStart(BaseSliderView slider) {
                Log.d("simpleSlider", "next_start:" + slider.getBundle().getString("type"));
            }

            @Override
            public void onNextAnimationEnd(BaseSliderView slider) {
                Log.d("simpleSlider", "next_end:" + slider.getBundle().getString("type"));
            }

            @Override
            public void onPreAnimationStart(BaseSliderView slider) {
                Log.d("simpleSlider", "pre_start:" + slider.getBundle().getString("type"));
            }

            @Override
            public void onPreAnimationEnd(BaseSliderView slider) {
                Log.d("simpleSlider", "pre_end:" + slider.getBundle().getString("type"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
