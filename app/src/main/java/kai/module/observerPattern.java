package kai.module;

import android.util.Log;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 15/9/16.
 */
public class observerPattern extends Observable {

    private static observerPattern instance = null;

    public static observerPattern getInstance(){
        if(instance == null){
            instance = new observerPattern();
        }
        return instance;
    }

    private ExecutorService executorService;
    private Runnable runnable;
    private Timer timer;
    private boolean isRunning = false;

    public void startObserverPattern(){
        Log.d("tag", "startObserverPattern");
        executorService = Executors.newSingleThreadExecutor();
        runnable = new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                long time = System.currentTimeMillis();
                Log.d("tag", "runnablue:" + time);
                setData(time);
                isRunning = false;
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isRunning) executorService.execute(runnable);
            }
        }, 0, 3000);
    }

    public void stopObserverPattern(){
        Log.d("tag", "stopObserverPattern");
        executorService.shutdown();
        timer.cancel();
        timer = null;
    }

    public boolean isRunning(){
        return isRunning;
    }

    long data = 0;
    public void setData(long data){
        Log.d("tag", "setData產生變化" + data);
        if(this.data != data){
            //假如資料有變動
            this.data = data;
            setChanged();
            //設定有改變
        }
        notifyObservers(data);
        //發送給觀察者
    }

}
