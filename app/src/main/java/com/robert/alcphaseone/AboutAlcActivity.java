package com.robert.alcphaseone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AboutAlcActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);

        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progressDialog.setCancelable(false);

        AlertDialog alertDialog = new AlertDialog.Builder(AboutAlcActivity.this).create();

        WebView about_alc = findViewById(R.id.wv_about_alc);
        SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // refresh
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            progressDialog.show();
            new Handler().postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
                about_alc.reload();
            }, 3000);
        });

        // set configurations
        about_alc.getSettings().setJavaScriptEnabled(true);
        about_alc.getSettings().setLoadWithOverviewMode(true);
        about_alc.getSettings().setUseWideViewPort(true);
        about_alc.getSettings().setPluginState(WebSettings.PluginState.ON);
        about_alc.loadUrl("http://andela.com/alc/");

        about_alc.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progressDialog.dismiss();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // ignore ssl error
                if (handler != null) handler.proceed();
                else super.onReceivedSslError(view, null, error);
            }

            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                    Toast.makeText(AboutAlcActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", (dialog, which) -> {
                    finish();
                    startActivity(getIntent());
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }
}
