package main;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import kai.module.R;

/**
 * Created by kevin on 2016/2/5.
 */
public class PageFragment extends Fragment {

    public static PageFragment newInstance(String str){
        PageFragment pageFragment = new PageFragment();
        Bundle data = new Bundle();
        data.putString("str", str);
        pageFragment.setArguments(data);
        return pageFragment;
    }

    String str = null;
    int i=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str = getArguments() != null ? getArguments().getString("str") : "NULL";
    }
    /**为Fragment加载布局时调用**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen3, null);
        TextView tv = (TextView) view.findViewById(R.id.textView2);
        tv.setText("fragment+" + str);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        return view;
    }

    private void showNotification(){
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//        Notification notification = new NotificationCompat.Builder(getActivity())
//                .setSmallIcon(R.drawable.com_facebook_button_like_icon_selected)
//                .setContentTitle("setContentTitle:" + i)
//                .setContentText("setContentText:" + i)
//                .setContentInfo("setContentInfo:" + i)
//                .setWhen(System.currentTimeMillis())
////                .setGroup("KAI")
////                .setGroupSummary(true)
////                .setStyle(new Notification.InboxStyle()
////                        .addLine("LINE1")
////                        .addLine("LINE2")
////                        .setBigContentTitle("setBigContentTitle")
////                        .setSummaryText("setSummaryText"))
//                .setNumber(i++)
//                .build();
//        notificationManager.notify("KAI", 0, notification);
        //        bundle.putString(HttpLoader.EXECUTE_RESULT, );
        new AlertDialog.Builder(getActivity()).setMessage("剪影全解鎖無隱藏，感興趣/留言/徵約會/心情貼/聊天等功能開放使用；交友暢所無阻\\\n\\\n追愛成效保證方案\\\n1. 追愛成效保證方案每個帳號僅能購買一次。\\\n2. 當您購買此方案後，若在6個月內沒有找到合適的物件，且每個月有達成以下幾項任務，iPair將免費送您6個月的服務：\\\n(1)檔案要維持公開\\\n(2)需上傳個人形象照\\\n(3)登入 5 天以上\\\n(4) 和 5個人留言互動\\\n(5) 對100人感興趣\\\n3. 追愛成效保證方案無法在使用途中轉送他人。\\\n4. 贈送的6個月服務無法退費或轉換現金。\\\n5.iPair保留修改追愛成效保證方案使用規則之權利。".replaceAll("\\n","\n").replaceAll("\\","")).show();

    }

}
