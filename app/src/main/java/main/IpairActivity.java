package main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import kai.module.R;

/**
 * Created by kevin on 15/9/11.
 */
public class IpairActivity extends AppCompatActivity {

    Toolbar toolbar;
    PagerSlidingTabStrip pagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipart_main);
        setToolBar();
        setDrawerLayout();
        viewpageInit();
    }

    private void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("123");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

//    public void hideToolBar(){
//        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//        pagerTab.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
//    }
//
//    public void showToolBar(){
//        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//        pagerTab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//    }

    private void setDrawerLayout(){
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    ArrayList<String> data = new ArrayList<>();
    private void viewpageInit(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        for(int i=0;i<5;i++){
            data.add("第 "+i+" 個");
        }
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        pagerTab = (PagerSlidingTabStrip)findViewById(R.id.pagertab);
        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //pagerTab.setTabPaddingLeftRight(0);
        //pagerTab.setTabPaddingLeftRight(metrics.widthPixels/5);
        //pagerTab.setAllCaps(true);
        pagerTab.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
                mTitle.setText(data.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(data.get(0));
    }

    public class Adapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.CustomTabProvider{

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
            //((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(data.get(position));
            SpannableStringBuilder sb = new SpannableStringBuilder(data.get(position)); // space added before text for convenience
            Drawable myDrawable = getDrawable(R.drawable.com_facebook_button_like_icon_selected);
            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(myDrawable);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
//            return data.get(position);
        }


        @Override
        public View getCustomTabView(ViewGroup viewGroup, int position) {
            ImageView imageView = new ImageView(viewGroup.getContext());
            switch (position){
                case 0:
                    imageView.setImageResource(R.drawable.select_0);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.select_1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.select_2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.select_3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.select_4);
                    break;
                default:
                    imageView.setImageResource(R.drawable.test_select);
                    break;
            }
            return imageView;
        }

        @Override
        public void tabSelected(View view) {
            view.setSelected(true);
            //view.setFocusable(true);
            //((ImageView)view).setImageResource(R.drawable.com_facebook_button_send_icon);
        }

        @Override
        public void tabUnselected(View view) {
            view.setSelected(false);
            //view.setFocusable(false);
            //((ImageView)view).setImageResource(R.drawable.com_facebook_button_like_icon_selected);
        }
    }
}
