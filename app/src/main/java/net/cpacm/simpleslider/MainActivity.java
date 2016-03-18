package net.cpacm.simpleslider;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.ImageSliderView;
import net.cpacm.library.slider.OnSliderClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);
        int[] ids = {R.mipmap.android, R.mipmap.html5, R.mipmap.github, R.mipmap.ios, R.mipmap.cpacm, R.mipmap.java};
        for (int i = 0; i < ids.length; i++) {
            ImageSliderView imageSliderView = new ImageSliderView(getApplicationContext());
            imageSliderView.empty(R.mipmap.ic_launcher);
            imageSliderView.getImageView().setBackgroundResource(ids[i]);
            imageSliderView.bundle(new Bundle());
            imageSliderView.getBundle()
                    .putString("user", "cpacm");
            imageSliderView.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Log.d("simple", slider.getBundle().getString("user"));
                }
            });
            simpleSliderLayout.addSlider(imageSliderView);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
