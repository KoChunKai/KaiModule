package http;


import android.app.Application;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by kevin on 2016/3/8.
 */
public class EasyCookieJar implements CookieJar {

    //private final PersistentCookieStore cookieStore = new PersistentCookieStore();
    private final PersistentCookieStore cookieStore = null;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
