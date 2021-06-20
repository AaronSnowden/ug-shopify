package com.ugshopify.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import api.ugshopify.app.application.ViewHelper;
import api.ugshopify.app.application.WebFragment;

public class LoginActivity extends Activity {

  private LinearLayout root;
  private WebView webview;
  private Context context;
 
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      login_ui();
      login_init();

    } catch (Exception e) {
      // handle errors
    }
  }


  private void login_ui() {
    context = getApplicationContext();
  
    new ViewHelper(this.getWindow()).setStatusBarColor();
    
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);

    webview = new WebView(context);
    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("intro.html");
    webview = webFragment.initializeWebFragment();

    root.addView(webview);

    setContentView(root);
  }

  private void login_init() {}
}
