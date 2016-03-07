package net.cpacm.simpleslider;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> viewList;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewList = new ArrayList<>();
        ImageView img;
        LayoutInflater lf = getLayoutInflater().from(this);
        int[] ids = {R.mipmap.android, R.mipmap.html5, R.mipmap.github, R.mipmap.ios, R.mipmap.cpacm, R.mipmap.java};
        for (int i = 0; i < ids.length; i++) {
            View view = lf.inflate(R.layout.image_item, null);
            img = (ImageView) view.findViewById(R.id.item_image);
            img.setBackgroundResource(ids[i]);
            viewList.add(view);
        }
        sliderAdapter = new SliderAdapter(viewList);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(sliderAdapter);
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
