package kai.module;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.ClientCertRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends Activity {

    MainActivity self;

    ViewPager viewPager;
    CircleIndicator defaultIndicator;

    ScrollView scrollView;

    View include;

    boolean isViewPagerAnimation = false;

    String[] pic = {
            "http://40.media.tumblr.com/tumblr_m9nsmsUrnx1re8q44o1_540.jpg",
            "http://40.media.tumblr.com/56b32437c491870c919ac33d8cc5b24b/tumblr_nef55qvzZF1u1y6nto1_540.jpg",
            "http://40.media.tumblr.com/50184fa097a46a3c3bb2e1b3d62f68e2/tumblr_nr5n74hGEJ1qixltro1_540.jpg",
            "http://i.imgur.com/aeOpH2o.jpg",
            "http://41.media.tumblr.com/0e645bcb4ba66ebbc5f4ed6cf23acdaa/tumblr_nqxga4cNyZ1t107ayo1_540.jpg"};

    ImageLoader imageLoader = ImageLoader.getInstance();

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .showImageOnLoading(null)
            .showImageForEmptyUri(null)
            .showImageOnFail(null)
            .cacheInMemory(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

    GestureDetector gestureDetector;
    int width;
    ArrayList<String> data = new ArrayList<>();
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        data.add(pic[0]);
        data.add(pic[1]);
        data.add(pic[2]);
        data.add(pic[3]);
        data.add(pic[4]);

        gestureDetector = new GestureDetector(this, onGestureListener);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,width);
        viewPager.setLayoutParams(lp);
        ImageAdapter adapter = new ImageAdapter();
        viewPager.setAdapter(adapter);
        defaultIndicator.setViewPager(viewPager);

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                viewPager.setY(2 * scrollView.getScrollY() / 3);
                if (scrollView.getScrollY() + include.getHeight() >= findViewById(R.id.viewpage_include).getY()) {
                    findViewById(R.id.i2).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.i2).setVisibility(View.GONE);
                }

                int alpha = Math.min(255, scrollView.getScrollY() * 255 / Math.max(1, viewPager.getHeight() - include.getHeight() * 2));

                findViewById(R.id.viewpage_include)
                        .getBackground()
                        .setAlpha(alpha);
                findViewById(R.id.top)
                        .getBackground()
                        .setAlpha(alpha);
            }
        });
        findViewById(R.id.viewpage_include).getBackground().setAlpha(0);

        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        include = findViewById(R.id.top);
        include.getBackground().setAlpha(0);

        self = this;



        findViewById(R.id.top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntent();
            }
        });
        openIntent();
    }

    File file;
    private void openIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 7001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 7001){
            System.out.println("do 7001");
            if(resultCode==Activity.RESULT_OK){
                System.out.println("Activity.RESULT_OK");

            }
        }
    }

    public String getRealPath(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = self.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void Notification(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this,MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Bitmap largeIcon = BitmapFactory.decodeResource(
                getResources(), R.mipmap.appstart_icon);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setTicker("EFFECT")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Custom effect")
                .setContentIntent(appIntent);
        notificationManager.notify(0, builder.build());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }


    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceX)>10) return true;
            int scrollY = scrollView.getScrollY();
            if(e2.getRawY() > e1.getRawY() && scrollY==0){
                int height = Math.min(width*2,viewPager.getHeight() - (int)distanceY);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
                viewPager.setLayoutParams(lp);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getAction() == MotionEvent.ACTION_UP){
                if(viewPager.getHeight()>width) {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(viewPager.getMeasuredHeight(), width);
                    valueAnimator.setDuration(200);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int val = (int) animation.getAnimatedValue();
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, val);
                            viewPager.setLayoutParams(lp);
                        }
                    });
                    valueAnimator.start();
                }
            }
            return true;
        }
    };

    public class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pic.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView image = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setLayoutParams(lp);
            imageLoader.displayImage(pic[position], image, options);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),showphoto.class);
                    intent.putStringArrayListExtra("data",data);
                    intent.putExtra("index",position);
                    startActivity(intent);
                }
            });
            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }






















/*    private void showFullScreen(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ImageView iv = (ImageView)findViewById(R.id.test);
        int scaleX = width/iv.getWidth();
        int scaleY = height/iv.getWidth();
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(iv, View.X, iv.getX(), width / 2),
                ObjectAnimator.ofFloat(iv, View.Y, iv.getY(), height / 2),
                ObjectAnimator.ofFloat(iv, View.SCALE_X, 1, scaleY),
                ObjectAnimator.ofFloat(iv, View.SCALE_Y, 1, scaleY)
        );
        set.setDuration(500).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 888 && resultCode == 1){
            System.out.println("MainActivity User is connected! userName: " + data.getStringExtra("acc"));
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, Bitmap> {
        ProgressDialog mProgressDialog;
        String fileName=null;
        byte data[];
        Bitmap bitmap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set your ProgressBar Title
            mProgressDialog.setTitle("Downloads");
            //mProgressDialog.setIcon(R.drawable.dwnload);
            // Set your ProgressBar Message
            mProgressDialog.setMessage("Updating App Version, Please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show ProgressBar
            mProgressDialog.setCancelable(false);
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... sUrl) {
            try{
                URL url = new URL("http://www.personal.psu.edu/jul229/mini.jpg");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                urlConnection.disconnect();
                return bitmap;
            } catch(Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            mProgressDialog.dismiss();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            ((ImageView)findViewById(R.id.test)).setImageBitmap(result);
        }



        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the ProgressBar
            mProgressDialog.setProgress(progress[0]);
        }
    }

*/}
