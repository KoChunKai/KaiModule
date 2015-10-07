package kai.module;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by kevin on 15/9/25.
 */
public class profile_my_photoeditor extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_my_editor);

        String[] photo = getIntent().getStringArrayExtra("photo");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        for(String s:photo){
            ImageView iv = new ImageView(this);
            ViewGroup.LayoutParams laap = new ViewGroup.LayoutParams(width/2, width/2);
            iv.setLayoutParams(laap);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LoadImage.getInstance(this).imageLoader.displayImage(s,iv);
            ll.addView(iv);
        }
    }
}
