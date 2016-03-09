package http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kevin on 2016/3/9.
 */
public class HttpLoader implements Runnable{

    public final static String EXECUTE_RESULT = "result";
    public final static String HTTP_STATUS_CODE = "http_status";
    private long startTime = 0;

    private Handler mHandler;
    private Bundle mBundle = new Bundle();
    private Message mMessage = new Message();
    private HashMap<String, Object> params = new HashMap<>();
    private HashMap<String, String> extraHeader = null;
    private byte HttpType = 1;
    private int successCode, failureCode;
    /**
     * 1 get
     * 2 post
     * 4 put
     * 8 delete
     * 16 have file
     * 32 keep alive
     */
    private String URL = null;
    StringBuilder sb = new StringBuilder();
    private Request.Builder builder = new Request.Builder();
    private long CacheVaildTime = 0;


    public HttpLoader(String URL, Handler handler, int successCode){
        instance(URL, handler, successCode, -9999);
    }

    public HttpLoader(String URL, Handler handler, int successCode, int failureCode) {
        instance(URL, handler, successCode, failureCode);
    }

    private void instance(String URL, Handler handler, int successCode, int failureCode){
        this.startTime = System.currentTimeMillis();
        if(URL != null && URL.indexOf("?")==-1){
            this.URL = URL+"?";
        }else{
            this.URL = URL;
        }
        this.mHandler = handler;
        this.successCode = successCode;
        this.failureCode = failureCode;
    }

    @Override
    public void run() {
        if (extraHeader != null) {
            Set<String> set = extraHeader.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = it.next();
                builder.header(key, extraHeader.get(key));
            }
        }
        if(CacheVaildTime > 0){
            builder.header("Cache-Control", "max-stale=" + CacheVaildTime);
        }
        if((HttpType & 32) > 0) {
            //keeplive
        }else if((HttpType & (4 | 16)) == (4 | 16)){
            //put
        }else if((HttpType & (4)) == (4)){
            //put
        }else if ((HttpType & (8)) == (8)) {
            //delete
        }else if ((HttpType & (18)) == (18)){
            //post
        }else if ((HttpType & (2)) == (2)) {
            //post
            connectPost();
        }else{
            //get
            connectGet();
        }
    }

    private Callback callback =  new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mMessage.what = failureCode;
            mHandler.sendMessage(mMessage);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            mBundle.putInt(HttpLoader.HTTP_STATUS_CODE, 200);
            mBundle.putString("URL", URL);
            mBundle.putByte("HttpType", HttpType);
            mBundle.putString(EXECUTE_RESULT, response.body().string());
            mMessage.setData(mBundle);
            mMessage.what = successCode;
            if (mHandler != null) {
                mHandler.sendMessage(mMessage);
            }
        }
    };

    private void connectGet(){
        String Para;
        if (params.size() > 0) {
            Set set = params.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                String key = (String) iterator.next();
                sb.append("&").append(key).append("=").append(params.get(key));
            }
            Para = sb.toString();
        } else {
            Para = "";
        }
        builder.url(URL + Para);
        new OkHttpClient
                .Builder()
                //.cookieJar(UserConfig.easyCookieJar)
                .build()
                .newCall(builder.build())
                .enqueue(callback);
    }

    private void connectPost(){
        builder.url(URL);
        FormBody.Builder builderForm = new FormBody.Builder();
        Set set = params.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            builderForm.add(key, (String) params.get(key));

        }
        RequestBody formBody = builderForm.build();
        new OkHttpClient
                .Builder()
                //.cookieJar(UserConfig.easyCookieJar)
                .build()
                .newCall(builder.post(formBody).build())
                .enqueue(callback);
    }

    public HttpLoader set_paraData(String key, Object value){
        if(value == null){
            params.put(key, "null");
        }else{
            params.put(key, ((String)value).trim());
        }
        return this;
    }

    public HttpLoader set_appendData(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public HttpLoader set_appendData(String key, Serializable Obj) {
        mBundle.putSerializable(key, Obj);
        return this;
    }

    public HttpLoader set_appendData(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public HttpLoader set_appendData(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public HttpLoader setGet() {
        HttpType = 1;
        return this;
    }

    public HttpLoader setPost() {
        HttpType |= 2;
        return this;
    }

    public HttpLoader setPut() {
        HttpType |= 4;
        return this;
    }

    public void start() {
        run();
    }

}
