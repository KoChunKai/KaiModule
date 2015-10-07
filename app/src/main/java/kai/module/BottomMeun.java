package kai.module;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import flow.Flow;
import flow.History;
import flow.path.Path;

/**
 * Created by kevin on 15/9/3.
 */
public class BottomMeun {

    View view,btn_1,btn_2,btn_3,btn_4,btn_5;
    boolean isplay = false, isBottomOpen = false;

    public BottomMeun(View view){
        this.view = view;
        initView();
    }

    private void initView(){
        btn_1 = view.findViewById(R.id.btm_menu_1);
        btn_2 = view.findViewById(R.id.btm_menu_2);
        btn_3 = view.findViewById(R.id.btm_menu_3);
        btn_4 = view.findViewById(R.id.btm_menu_4);
        btn_5 = view.findViewById(R.id.btm_menu_5);
        btn_1.setOnClickListener(listener);
        btn_2.setOnClickListener(listener);
        btn_3.setOnClickListener(listener);
        btn_4.setOnClickListener(listener);
        btn_5.setOnClickListener(listener);
        btn_1.setClickable(false);
    }

    public void close(){
        if(!isplay && isBottomOpen){
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator o = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), view.getHeight());
            set.setDuration(1000);
            set.play(o);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isplay = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isplay = false;
                    isBottomOpen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        }
    }

    public void open(){
        if(!isplay && !isBottomOpen){
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator o = ObjectAnimator.ofFloat(view,"translationY",view.getTranslationY(),1.0f);
            set.setDuration(1000);
            set.play(o);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isplay = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isplay = false;
                    isBottomOpen = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btn_1.setClickable(true);
            btn_2.setClickable(true);
            btn_3.setClickable(true);
            btn_4.setClickable(true);
            btn_5.setClickable(true);
            v.setClickable(false);
            Path path = null;
            switch (v.getId()){
                case R.id.btm_menu_1:
                    path = new Screen1Path();
                    break;
                case R.id.btm_menu_2:
                    path = new Screen2Path();
                    break;
                case R.id.btm_menu_3:
                    path = new Screen3Path();
                    break;
                case R.id.btm_menu_4:
                    path = new Screen4Path();
                    break;
                case R.id.btm_menu_5:
                    path = new Screen5Path();
                    break;
            }
            History history = History.single(path);
            Flow.get(FlowMain.self).setHistory(history, Flow.Direction.REPLACE);
        }
    };
}
