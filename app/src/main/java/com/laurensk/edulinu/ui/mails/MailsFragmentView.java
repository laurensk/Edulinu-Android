package com.laurensk.edulinu.ui.mails;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.laurensk.edulinu.R;

public class MailsFragmentView extends Fragment {

    private WebView mailsWebView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mails, container, false);

        final String mailsUrl = "https://mail.google.com/mail/mu/mp/71/#tl/Posteingang";

        final ProgressDialog webViewProgressIndicator = ProgressDialog.show(getContext(), "", "Laden...",true);

        this.mailsWebView = view.findViewById(R.id.mailsWebView);

        WebSettings webSettings = mailsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mailsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(webViewProgressIndicator!=null && webViewProgressIndicator.isShowing())
                {
                    webViewProgressIndicator.dismiss();
                }
            }
        });

        mailsWebView.loadUrl(mailsUrl);

        return view;
    }

}
