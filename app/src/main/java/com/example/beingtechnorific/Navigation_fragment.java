package com.example.beingtechnorific;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import static com.example.beingtechnorific.MainActivity.progressBar;
import static com.example.beingtechnorific.MainActivity.progressDialog;
import static com.example.beingtechnorific.MainActivity.webView;

public class Navigation_fragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_navigation_fragment, container, false);
        NavigationView navigationView=view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  switch (item.getItemId())
                  {
                      case R.id.freelancing: {
                          webView.setWebChromeClient(new WebChromeClient(){
                              @Override
                              public void onProgressChanged(WebView view, int newProgress) {
                                  progressBar.setVisibility(View.VISIBLE);
                                  progressBar.setProgress(newProgress);
                                  progressDialog.show();
                                  progressDialog.setTitle("Loading...");
                                  if (progressBar.getProgress() == 100) {
                                      progressDialog.cancel();
                                      progressBar.setVisibility(View.INVISIBLE);
                                  }
                              }
                          });
                          webView.loadUrl("https://beingtechno.com/freelancing/");
                          break;
                      }
                      case R.id.motivation:
                      {
                          webView.setWebChromeClient(new WebChromeClient(){
                              @Override
                              public void onProgressChanged(WebView view, int newProgress) {
                                  progressBar.setVisibility(View.VISIBLE);
                                  progressBar.setProgress(newProgress);
                                  progressDialog.show();
                                  progressDialog.setTitle("Loading...");
                                  if (progressBar.getProgress() == 100) {
                                      progressDialog.cancel();
                                      progressBar.setVisibility(View.INVISIBLE);
                                  }
                              }
                          });
                          webView.loadUrl("https://beingtechno.com/motivation/");
                          break;
                      }

                      case R.id.how_to:
                      {
                          webView.setWebChromeClient(new WebChromeClient(){
                              @Override
                              public void onProgressChanged(WebView view, int newProgress) {
                                  progressBar.setVisibility(View.VISIBLE);
                                  progressBar.setProgress(newProgress);
                                  progressDialog.show();
                                  progressDialog.setTitle("Loading...");
                                  if (progressBar.getProgress() == 100) {
                                      progressDialog.cancel();
                                      progressBar.setVisibility(View.INVISIBLE);
                                  }
                              }
                          });
                          webView.loadUrl("https://beingtechno.com/how-to/");
                          break;
                      }

                      case R.id.about:
                      {
                          webView.setWebChromeClient(new WebChromeClient(){
                              @Override
                              public void onProgressChanged(WebView view, int newProgress) {
                                  progressBar.setVisibility(View.VISIBLE);
                                  progressBar.setProgress(newProgress);
                                  progressDialog.show();
                                  progressDialog.setTitle("Loading...");
                                  if (progressBar.getProgress() == 100) {
                                      progressDialog.cancel();
                                      progressBar.setVisibility(View.INVISIBLE);
                                  }
                              }
                          });
                          webView.loadUrl("https://beingtechno.com/about/");
                          break;
                      }
                      default:
                          throw new IllegalStateException("Unexpected value: " + item.getItemId());
                  }
                return true;
            }
        });
        return view;
    }
}