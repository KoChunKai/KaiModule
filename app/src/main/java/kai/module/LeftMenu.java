package kai.module;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import flow.Flow;

/**
 * Created by kevin on 15/9/3.
 */
public class LeftMenu{

    private Activity self;
    private DrawerLayout drawerLayout;
    private ListView listView;
    public Adapter adapter;
    public ArrayList<String> data = new ArrayList<>();

    public LeftMenu(DrawerLayout drawerLayout, ListView listView){
        self = FlowMain.self;
        this.drawerLayout = drawerLayout;
        this.listView = listView;
        init();
    }

    private void init(){
        data.add("第一欄");
        data.add("第二欄");
        data.add("第三欄");
        data.add("第四欄");
        data.add("第五欄");
        data.add("第六欄");
        data.add("第七欄");
        data.add("登入");
        adapter = new Adapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //FlowMain.self.openScreen1Page();
                self.startActivity(new Intent(self,MainActivity.class));
                drawerLayout.closeDrawers();

            }
        });
    }

    private class Adapter extends BaseAdapter{


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder;
            if(view == null){
                view = makeView();
            }
            holder = (Holder) view.getTag();
            holder.textView.setText(data.get(position));
            return view;
        }

        View makeView(){
            Holder holder = new Holder();
            View view = LayoutInflater.from(FlowMain.self).inflate(R.layout.left_menu_item, null, false);
            holder.textView = (TextView) view.findViewById(R.id.textView3);
            view.setTag(holder);
            return view;
        }

        class Holder{
            TextView textView;
        }
    }
}
