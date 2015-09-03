package kai.module;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import flow.Flow;
import flow.FlowDelegate;
import flow.GsonParceler;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import flow.pathview.HandlesBack;
import flow.pathview.PageListener;

/**
 * Created by kevin on 15/9/2.
 */
public class FlowMain extends ActionBarActivity implements Flow.Dispatcher {
    private FlowDelegate flowSupport;
    private PathContainerView container;
    private PageListener pageListener;
    private HandlesBack containerAsBackTarget;

    public static FlowMain self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_main);
        setActionBarHomeEnabled(false);
        self = this;

        container = (PathContainerView) findViewById(R.id.container);
        containerAsBackTarget = (HandlesBack) container;
        pageListener = (PageListener) container;

        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance();
        GsonParceler parceler = new GsonParceler(new Gson());
        History history = History.single(getFirstPage());
        flowSupport = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, history, this);
        mainView();
    }

    View vvv;
    private void mainView(){
        getSupportActionBar().hide();
        vvv = findViewById(R.id.textView2);
    }

    public void closebottom(){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o = ObjectAnimator.ofFloat(vvv, "translationY", vvv.getTranslationY(), vvv.getHeight());
        set.setDuration(1000);
        set.play(o);
        set.start();
    }

    public void openbottom(){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o = ObjectAnimator.ofFloat(vvv,"translationY",vvv.getTranslationY(),1.0f);
        set.setDuration(1000);
        set.play(o);
        set.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowSupport.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowSupport.onResume();
    }

    @Override
    protected void onPause() {
        flowSupport.onPause();
        super.onPause();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return flowSupport.onRetainNonConfigurationInstance();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        Object service = null;
        if (flowSupport != null) {
            service = flowSupport.getSystemService(name);
        }
        return service != null ? service : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowSupport.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (containerAsBackTarget.onBackPressed()) return;
        if (flowSupport.onBackPressed()) return;
        /*new AlertDialog.Builder(self).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("NO",null).show();*/
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        pageListener.onPrepareOptionsMenu(this, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        boolean canGoBack = traversal.destination.size() > 1;
        setActionBarHomeEnabled(canGoBack);
        invalidateOptionsMenu();
        container.dispatch(traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                callback.onTraversalCompleted();
            }
        });
    }

    private void setActionBarHomeEnabled(boolean enabled){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    public Path getFirstPage() {
        //TODO Use your own path.
        return new TestPath();
    }
}

