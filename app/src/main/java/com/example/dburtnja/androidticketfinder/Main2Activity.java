package com.example.dburtnja.androidticketfinder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
import com.google.gson.Gson;


public class Main2Activity extends AppCompatActivity {
    private WebView         webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String              cookie;
        Intent              intent;

        intent = getIntent();
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        cookie = intent.getStringExtra("cookie");
        //if (ticket.haveTicket)
            webView.loadUrl(showCart(cookie));
//        else
//            //webView.postUrl("https://booking.uz.gov.ua/mobile/train_search", ticket.getSearchParamMobile());
//            webView.loadUrl("https://httpbin.org/post/?" + ticket.getSearchParamMobile());

    }

    private String showCart(String cookie){
        ClipboardManager    clipboardManager;
        ClipData            clipData;
        CookieManager       cookieManager;
        String              url;

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("cookie", cookie);
        clipboardManager.setPrimaryClip(clipData);
        url = "https://booking.uz.gov.ua/mobile/cart/";
        cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("https://booking.uz.gov.ua/", cookie);
        return (url);
    }
}
