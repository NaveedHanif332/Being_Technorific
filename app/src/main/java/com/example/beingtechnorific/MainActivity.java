package com.example.beingtechnorific;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class MainActivity extends AppCompatActivity {
    public static WebView webView;
    BottomAppBar  bottomAppBar;
    FloatingActionButton  floatingActionButton;
    private String url="https://beingtechno.com/";
    public static SwipeRefreshLayout swipeRefreshLayout;
    static ProgressBar progressBar;
    static ProgressDialog progressDialog;
    LottieAnimationView lottieAnimationView;
    LinearLayout animation,main;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=findViewById(R.id.web_view);
        bottomAppBar=findViewById(R.id.app_bar);
        progressBar=findViewById(R.id.progress_bar);
        floatingActionButton=findViewById(R.id.floating_botton);
        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        animation=findViewById(R.id.animation_layout);
        main=findViewById(R.id.main_layout);
        animation.setVisibility(View.INVISIBLE);
        main.setVisibility(View.VISIBLE);

        lottieAnimationView=findViewById(R.id.animationView);
        lottieAnimationView.setVisibility(View.GONE);
     /*   Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

      */

        progressBar.setProgress(0);
        webView.getSettings().setJavaScriptEnabled(true);
        swipeRefreshLayout.setEnabled(false);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ! Please wait");

        if(savedInstanceState==null)
        {
            check_connection();
        }
        else {
            webView.restoreState(savedInstanceState);
        }
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.design_default_color_primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.next:
                webView.goForward();
                break;
            case R.id.previous:
                onBackPressed();
                break;
            case R.id.home:
                check_connection();
                break;
            case R.id.reload:
              webView.reload();
                break;
            default:
                break;
        }
        return true;
    }
});

bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Navigation_fragment bottomNavDrawerFragment = new Navigation_fragment();
        bottomNavDrawerFragment.show(getSupportFragmentManager(), bottomNavDrawerFragment.getTag());
    }
});
       floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        webView.loadUrl("https://beingtechno.com/contact/");
    }
});

        webView.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               // view.loadUrl(url);
                //for sharing
                if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false;

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
               progressDialog.show();
                setTitle("Loading...");
                if (progressBar.getProgress() == 100) {
                    progressDialog.cancel();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
    public  void check_connection()
    {
        BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean connected=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
                if(connected) {
                    animation.setVisibility(View.VISIBLE);
                    main.setVisibility(View.INVISIBLE);
                    lottieAnimationView.playAnimation();
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    animation.setVisibility(View.INVISIBLE);
                    main.setVisibility(View.VISIBLE);
                    webView.loadUrl(url);
                }
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }else
        {
            final AlertDialog.Builder b=new AlertDialog.Builder(this);
            b.setMessage("Are you sure want to exit");
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    b.setCancelable(true);
                }
            }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            }).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        webView.saveState(outState);
    }
}
