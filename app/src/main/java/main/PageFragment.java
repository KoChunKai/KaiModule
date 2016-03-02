package main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

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
    int prevPos = 0;
    FloatingActionButton fac;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str = getArguments() != null ? getArguments().getString("str") : "NULL";
    }
    /**为Fragment加载布局时调用**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", str);
        View view = inflater.inflate(R.layout.screen3, null);
        TextView tv = (TextView) view.findViewById(R.id.textView2);
        tv.setText("fragment+" + str);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.listView);
        fac = (FloatingActionButton) view.findViewById(R.id.fab);

        String[] str = new String[50];
        for(int i=0;i<str.length;i++){
            str[i]= "Index:" + i ;
        }
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, str));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > prevPos) {
                    //((IpairActivity)getActivity()).hideToolBar();
                    fac.hide(true);
                } else if (firstVisibleItem < prevPos) {
                    //((IpairActivity)getActivity()).showToolBar();
                    fac.show(true);
                }
                prevPos = firstVisibleItem;
            }
        });
        //fac.setShowProgressBackground(true);
        return view;
    }

    @Override
    public void onResume() {
        Log.d("onResume", str);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView", str);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy", str);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.d("onPause", str);
        super.onPause();
    }

    @Override
    public void onStart() {
        Log.d("onStart", str);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d("onStop", str);
        super.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("onActivityCreated", str);
        super.onActivityCreated(savedInstanceState);
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

    }

}
