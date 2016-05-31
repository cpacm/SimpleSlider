package net.cpacm.simpleslider;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.transformers.FlipPageViewTransformer;
import net.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.ImageSliderView;
import net.cpacm.library.slider.OnSliderClickListener;

public class BasicActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;
    private TextView codeView;
    private CirclePageIndicator circlePageIndicator;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private String[] strs = {"夜空", "车站", "夕阳", "世界", "神社", "碑"};
    private String[] urls = {
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B81.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B82.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B83.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B84.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B85.jpg",
            "http://7xi4up.com1.z0.glb.clouddn.com/%E5%A3%81%E7%BA%B86.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);
        codeView = (TextView) findViewById(R.id.code_tv);
        for (int i = 0; i < urls.length; i++) {
            ImageSliderView sliderView = new ImageSliderView(getApplicationContext());
            sliderView.empty(R.drawable.image_empty);
            imageLoader.displayImage(urls[i], sliderView.getImageView());
            sliderView.setPageTitle(strs[i]);
            sliderView.bundle(new Bundle());
            sliderView.getBundle()
                    .putString("type", strs[i]);
            sliderView.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Snackbar.make(getWindow().getDecorView(), slider.getPageTitle(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
            simpleSliderLayout.addSlider(sliderView);
        }
        simpleSliderLayout.setCycling(true);//无限循环
        simpleSliderLayout.setAutoCycling(true);//自动循环
        simpleSliderLayout.setSliderDuration(3000);//每隔3秒进行翻页
        simpleSliderLayout.setSliderTransformDuration(1000);//翻页的速度为1秒
        simpleSliderLayout.setPageTransformer(new FlipPageViewTransformer());//翻页动画
        simpleSliderLayout.setAnimationListener(null);//为slider设置特定的动画
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        simpleSliderLayout.setViewPagerIndicator(circlePageIndicator);//为viewpager设置指示器

        codeView.setText("setCycling(true);//无限循环\n\n" +
                "setAutoCycling(true);//自动循环\n\n" +
                "setSliderDuration(3000);//每隔3秒进行翻页\n\n" +
                "setSliderTransformDuration(1000);//翻页的速度为1秒\n\n" +
                "setPageTransformer(new FlipPageViewTransformer());//翻页动画\n\n" +
                "setAnimationListener(null);//为slider设置特定的动画\n\n" +
                "setViewPagerIndicator(circlePageIndicator);//为viewpager设置指示器");
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
