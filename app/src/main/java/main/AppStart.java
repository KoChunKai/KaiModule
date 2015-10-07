package main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import kai.module.R;
import kai.module.observerPattern;

/**
 * Created by kevin on 15/9/11.
 */
public class AppStart extends Activity implements Observer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 300);
        preLoading();
    }

    float EventX,EventY;
    private void preLoading(){
        observerPattern.getInstance().addObserver(this);
        if(!observerPattern.getInstance().isRunning()){
            observerPattern.getInstance().startObserverPattern();
        }
        findViewById(R.id.tv_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observerPattern.getInstance().stopObserverPattern();
            }
        });

        findViewById(R.id.imageView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        EventX = event.getRawX();
                        EventY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setTranslationX(event.getRawX() - EventX );
                        v.setTranslationY(event.getRawY() - EventY );
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void update(Observable observable, final Object data) {
        Log.d("tag", "Observable產生變化" + (long) data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.tv_data)).setText("data:" + data);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observerPattern.getInstance().stopObserverPattern();
        observerPattern.getInstance().deleteObservers();
    }
}
