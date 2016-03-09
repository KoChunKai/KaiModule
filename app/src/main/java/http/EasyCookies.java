package http;


import java.io.Serializable;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by kevin on 2016/3/8.
 */
public class EasyCookies implements Serializable {

    private static final long serialVersionUID = 1L;
    public HttpUrl httpUrl;
    public String name;
    public String value;
    public long expiresAt;
    public String domain;
    public String path;
    public boolean secure;
    public boolean httpOnly;
    public boolean hostOnly;
    public boolean persistent;

    public EasyCookies(HttpUrl httpUrl, Cookie cookies){
        this.httpUrl = httpUrl;
        this.name = cookies.name();
        this.value = cookies.value();
        this.expiresAt = cookies.expiresAt();
        this.domain = cookies.domain();
        this.path = cookies.path();
        this.secure = cookies.secure();
        this.httpOnly = cookies.httpOnly();
        this.hostOnly = cookies.hostOnly();
        this.persistent = cookies.persistent();
    }
}
