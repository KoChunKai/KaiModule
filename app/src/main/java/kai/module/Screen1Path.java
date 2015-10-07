package kai.module;

import flow.path.Path;
import flow.pathview.Layout;

/**
 * Created by kevin on 15/9/3.
 */
@Layout(R.layout.screen1)
public class Screen1Path extends Path {
}
/*
* new HttpLoader(AppConfig.HttpProtocol + AppConfig.HOST_DOMAIN + LoginAPI, handler, SuccessCode, FailCode)
                .set_paraData("mId", AndroidUniqueCode.getDeviceUuid(context))
                .set_paraData("uId", account)
                .set_paraData("nonce", "0806449")
                .set_paraData("token", RareFunction.getMD5(account.trim() + RareFunction.getMD5(password.trim()) + "0806449"))
                .set_paraData("x", userGPS[0])
                .set_paraData("y", userGPS[1])
                .set_paraData("push_token", RareFunction.getGCM_TOKEN(context))
                .set_paraData("mname", java.net.URLEncoder.encode(Build.MODEL, "UTF-8"))
                .set_paraData("version", AppConfig.VerNum)
                .set_paraData("AGW", "G")
                .set_paraData("timezone", String.valueOf(TimeZone.getDefault().getRawOffset() / (long) 3600000))
                .set_paraData("useDaylightTime", String.valueOf((TimeZone.getDefault().useDaylightTime()) ? "1" : "0"))
                .set_paraData("lang", RareFunction.getLoginLang(context))
                .setUpdateCookie().setPost().start();*/