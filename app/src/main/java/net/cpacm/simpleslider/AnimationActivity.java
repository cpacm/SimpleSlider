/*
 *  Copyright (C) 2016 cpacm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.cpacm.simpleslider;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.animation.DefaultDescriptionAnimation;
import net.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.DescriptionSliderView;
import net.cpacm.library.slider.OnSliderClickListener;
import net.cpacm.library.transformers.RotateDownTransformer;

public class AnimationActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;
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
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);
        for (int i = 0; i < urls.length; i++) {
            DescriptionSliderView sliderView = new DescriptionSliderView(getApplicationContext());
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
        simpleSliderLayout.setSliderTransformDuration(2000);//翻页的速度为2秒
        simpleSliderLayout.setPageTransformer(new RotateDownTransformer());//翻页动画
        simpleSliderLayout.setAnimationListener(new DefaultDescriptionAnimation());//为slider设置特定的动画
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_indicator);
        simpleSliderLayout.setViewPagerIndicator(circlePageIndicator);//为viewpager设置指示器

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
