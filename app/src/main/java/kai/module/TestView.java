package kai.module;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import flow.path.Path;
import flow.pathview.PageListener;

/**
 * Created by kevin on 15/9/2.
 */
public class TestView extends LinearLayout implements PageListener {

    public TestView(Context context , AttributeSet attrs) {
        super(context, attrs);
        Path.get(context);
        //TestPresenter presenter = new TestPresenter(context);
    }

    @Override
    public void onPageStart(final Activity activity) {
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn2",Toast.LENGTH_LONG).show();

            }
        });

        findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn3",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Activity activity, Menu menu) {

    }
}
