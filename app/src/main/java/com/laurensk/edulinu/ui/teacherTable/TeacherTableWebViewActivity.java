package com.laurensk.edulinu.ui.teacherTable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.laurensk.edulinu.R;

public class TeacherTableWebViewActivity extends AppCompatActivity {

    public static String teacherUrl;
    public static String teacherName;

    private WebView teacherPanelWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherwebview);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(teacherName);

        final ProgressDialog webViewProgressIndicator = ProgressDialog.show(this, "", "Laden...",true);

        this.teacherPanelWebView = findViewById(R.id.teacherWebView);

        WebSettings webSettings = teacherPanelWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        teacherPanelWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(webViewProgressIndicator!=null && webViewProgressIndicator.isShowing())
                {
                    webViewProgressIndicator.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webViewProgressIndicator.dismiss();

                view.destroy();
                webViewLoadingError();
            }

        });

        teacherPanelWebView.loadUrl(teacherUrl);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.teacherPanelWebView.canGoBack()) {
            this.teacherPanelWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void webViewLoadingError() {

        AlertDialog.Builder webViewLoadingErrorAlertBuilder = new AlertDialog.Builder(this);
        webViewLoadingErrorAlertBuilder.setTitle("Fehler");
        webViewLoadingErrorAlertBuilder.setMessage("Es konnte keine Verbindung zum Server hergestellt werden. Stelle sicher, dass du eine aktive Internetverbindung hast und versuche es dann erneut.");
        webViewLoadingErrorAlertBuilder.setCancelable(true);

        webViewLoadingErrorAlertBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog webViewLoadingErrorAlert = webViewLoadingErrorAlertBuilder.create();
        webViewLoadingErrorAlert.show();

    }

}