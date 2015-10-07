package kai.module;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.TimeZone;

import flow.Flow;
import flow.FlowDelegate;
import flow.GsonParceler;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import flow.pathview.HandlesBack;
import flow.pathview.PageListener;

/**
 * Created by kevin on 15/9/2.
 */
public class FlowMain extends AppCompatActivity implements Flow.Dispatcher {

    public static FlowMain self;
    public static Flow flow;

    public FlowDelegate flowSupport;
    public PathContainerView container;
    public PageListener pageListener;
    public HandlesBack containerAsBackTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_main);
        getSupportActionBar().hide();
        self = this;
        initFlow(savedInstanceState);
        mainView();
        initData();
    }

    private void initFlow(Bundle savedInstanceState){
        container = (PathContainerView) findViewById(R.id.container);
        containerAsBackTarget = (HandlesBack) container;
        pageListener = (PageListener) container;
        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance();
        GsonParceler parceler = new GsonParceler(new Gson());
        History history = History.single(getFirstPage());
        flowSupport = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, history, this);
        flow = Flow.get(self);
    }

    static BottomMeun bottomMeun;
    static LeftMenu leftMenu;
    private void mainView(){
        bottomMeun = new BottomMeun(findViewById(R.id.include));
        leftMenu = new LeftMenu((DrawerLayout) findViewById(R.id.drw_layout), (ListView) findViewById(R.id.left_menu));
    }

    private void initData(){
        //Get("http://w0.i-part.com.tw/api/apps/public/interest/index.php?");
        //Post("http://w0.i-part.com.tw/api/apps/user/app_user_login.php?");
        IMEI();

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

    void Get(String url){
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
    }

    void Post(String url){
        RequestBody formBody = null;
        try {
            formBody = new FormEncodingBuilder()
                    .add("mId", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))
                    .add("uId", "pikai0819")
                    .add("nonce", "0806449")
                    .add("token", getMD5("pikai0819"+getMD5("1qaz2wsx")+"0806449"))
                    .add("x", "25.0871")
                    .add("y", "121.5273")
                    .add("push_token", "")
                    .add("mname", java.net.URLEncoder.encode(Build.MODEL, "UTF-8"))
                    .add("version", "490")
                    .add("AGW", "G")
                    .add("timezone", String.valueOf(TimeZone.getDefault().getRawOffset() / (long) 3600000))
                    .add("useDaylightTime", String.valueOf((TimeZone.getDefault().useDaylightTime()) ? "1" : "0"))
                    .add("lang", "zh_tw")
                    .build();
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
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

    public static String getMD5(String data_str) {
        byte[] source = data_str.getBytes();
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowSupport.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowSupport.onResume();
    }

    @Override
    protected void onPause() {
        flowSupport.onPause();
        super.onPause();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return flowSupport.onRetainNonConfigurationInstance();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        Object service = null;
        if (flowSupport != null) {
            service = flowSupport.getSystemService(name);
        }
        return service != null ? service : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowSupport.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (containerAsBackTarget.onBackPressed()) return;
        if (flowSupport.onBackPressed()) return;
        new AlertDialog.Builder(self).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("NO",null).show();
        //super.onBackPressed();
    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        invalidateOptionsMenu();
        container.dispatch(traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                callback.onTraversalCompleted();
            }
        });
    }

    public Path getFirstPage() {
        //TODO Use your own path.
        return new Screen1Path();
    }

    public void openScreen1Page(){
        if(!(flow.getHistory().top() instanceof Screen1Path)){
            flow.set(new Screen1Path());
        }
    }
}

