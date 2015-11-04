package kai.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kai.library.opengallery.openGallery;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by kevin on 2015/10/8.
 */
public class practiveOkHttp extends Activity {

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

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);
        //IMEI();
    }

    public String encrypt(String s) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-256");
            sha.update(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return byte2hex(sha.digest());
    }

    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private void Notification(Bitmap bitmap){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this,MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Title")
                .setContentText("TEXT");
        //.setContentIntent(appIntent);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void openAlbum(){
        //Intent intent = new Intent(this,openGallery.class);
        //startActivityForResult(intent, 9);
        new AlertDialog.Builder(this)
                .setTitle("TITLE")
                .setMessage("MESSAGE")
                .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareTo();
                    }
                })
                .setNegativeButton("EXIT",null)
                .show();
    }

    private void shareTo(){
        Intent shareIntent = getOpenFacebookIntent(this);
        startActivity(shareIntent);
    }

    public Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/1639654996273560/videos/1655758314663228/?pnref=story"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/<user_name_here>"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                String path = "";
                path = data.getExtras().getString("path");
                imageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(path), imageView, options);
            }
        }
    }

    void IMEI(){
        try {
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            RequestBody formBody = new FormEncodingBuilder()
                    .add("imei", telephonyManager.getDeviceId())
                    .build();
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("X-Mashape-Key", "0vd2akpt86mshfoZSE0hzIRkMwiop1TaxW2jsnry4CesZeJrKv")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .url("https://ismaelc-imei-info.p.mashape.com/checkimei?login=&password=")
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    System.out.println("code:" + response.code());
                    System.out.println(response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
