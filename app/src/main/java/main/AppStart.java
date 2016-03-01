package main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import kai.module.R;
import kai.module.observerPattern;

/**
 * Created by kevin on 15/9/11.
 */
public class AppStart extends AppCompatActivity implements Observer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);
        viewpageInit();
    }

    ArrayList<String> data = new ArrayList<>();
    private void viewpageInit(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        for(int i=0;i<8;i++){
            data.add("第 "+i+" 個");
        }
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        final PagerSlidingTabStrip pagerTab = (PagerSlidingTabStrip)findViewById(R.id.pagertab);
        pagerTab.setShouldExpand(true);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //pagerTab.setTabPaddingLeftRight(0);
        //pagerTab.setTabPaddingLeftRight(metrics.widthPixels/5);
        //pagerTab.setAllCaps(true);
        pagerTab.setViewPager(viewPager);
    }

    public class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(data.get(position));
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position);
        }

    }

    float EventX,EventY;
    private void preLoading(){
        observerPattern.getInstance().addObserver(this);
        if(!observerPattern.getInstance().isRunning()){
            observerPattern.getInstance().startObserverPattern();
        }
        findViewById(R.id.tv_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observerPattern.getInstance().stopObserverPattern();
            }
        });

        findViewById(R.id.imageView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        EventX = event.getRawX();
                        EventY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setTranslationX(event.getRawX() - EventX );
                        v.setTranslationY(event.getRawY() - EventY );
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void update(Observable observable, final Object data) {
        Log.d("tag", "Observable產生變化" + (long) data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.tv_data)).setText("data:" + data);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observerPattern.getInstance().stopObserverPattern();
        observerPattern.getInstance().deleteObservers();
    }
}
