package DataObersver;

import android.app.Activity;
import android.util.Log;

import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2015/11/6.
 */
public class ServerObserver extends Observable {

    private static ServerObserver instance = null;

    private ExecutorService executorService;
    private Runnable runnable;
    private boolean isRunning = false;

    public static ServerObserver getInstance(Activity activity){
        if(instance == null){
            instance = new ServerObserver();
        }
        return instance;
    }

    private ServerObserver(){

    }

    public void startObserverPattern(){
        Log.d("Observer", "startObserverPattern");
        executorService = Executors.newSingleThreadExecutor();
        runnable = new Runnable() {
            @Override
            public void run() {
                getData();
            }
        };
        executorService.execute(runnable);
    }

    private void getData(){

    }

    private void setData(){
        Log.d("Observer", "setData");
        notifyObservers();
    }
}
