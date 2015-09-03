package kai.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import flow.Flow;
import flow.path.Path;
import flow.pathview.PageListener;

/**
 * Created by kevin on 15/9/2.
 */
public class Flow_rl extends LinearLayout implements PageListener {

    Context context;

    public Flow_rl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Path.get(context);
    }


    @Override
    public void onPageStart(final Activity activity) {
        activity.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(activity).set(new Screen2Path());
                System.out.println("button");
            }
        });

        activity.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowMain.self.closebottom();
                System.out.println("button2");
            }
        });

        activity.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowMain.self.openbottom();
                System.out.println("button3");
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Activity activity, Menu menu) {

    }
}
