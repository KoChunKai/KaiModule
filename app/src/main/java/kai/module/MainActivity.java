package kai.module;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivityForResult(new Intent(this, GooglePlusClient.class), 888);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFullScreen();
                //new DownloadFile().execute();

            }
        });
    }

    private void showFullScreen(){
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
}
