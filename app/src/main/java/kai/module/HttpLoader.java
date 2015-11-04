package kai.module;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by kevin on 2015/10/8.
 */
public class HttpLoader extends Thread {

    String URL = null;

    private int success_return = 1, fail_return = -1, thread_num = -1;
    private Stack<String> m_params = new Stack<>();
    private byte HttpType = 1;
    /*
         *    1 GET
         *    2 POST
         *    4 PUT
         *    8 DELETE
         *    16 Have File
         *    32 Keep-Alive
         * */

    public HttpLoader(String URL, Handler handler, int success_return, int fail_return){
        Log.d("HttpLoader", "new()" + URL);
        this.URL = URL;
    }


    @Override
    public void run() {
        Log.d("HttpLoader", "run()" + URL);
    }


}
