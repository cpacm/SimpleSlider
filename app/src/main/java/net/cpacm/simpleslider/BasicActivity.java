package net.cpacm.simpleslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.Transformers.ZoomOutSlideTransformer;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.ImageSliderView;
import net.cpacm.library.slider.OnSliderClickListener;

public class BasicActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private static final int[] ICONS = new int[]{
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location
    };
    private String[] strs = {"android", "h5", "github", "ios", "cpacm", "java"};
    private int[] ids = {R.mipmap.android, R.mipmap.html5, R.mipmap.github, R.mipmap.ios, R.mipmap.cpacm, R.mipmap.java};
    private String[] urls = {
            "http://7xi4up.com1.z0.glb.clouddn.com/android.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/html5.png",
            "http://7xi4up.com1.z0.glb.clouddn.com/github.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/ios.png",
            "http://7xi4up.com1.z0.glb.clouddn.com/cpacm.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/java.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);

        for (int i = 0; i < urls.length; i++) {
            ImageSliderView sliderView = new ImageSliderView(getApplicationContext());
            sliderView.empty(R.mipmap.ic_launcher);
            imageLoader.displayImage(urls[i], sliderView.getImageView());
            sliderView.setPageTitle(strs[i]);
            sliderView.bundle(new Bundle());
            sliderView.getBundle()
                    .putString("type", strs[i]);
            sliderView.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Log.d("simpleSlider", slider.getBundle().getString("type"));
                }
            });
            simpleSliderLayout.addSlider(sliderView);
        }
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
        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
