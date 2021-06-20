package com.ugshopify.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import api.ugshopify.app.application.WebFragment;
import org.json.JSONObject;

public class EditItemActivity extends Activity {

  private LinearLayout root;
  private ScrollView scroll;
  private WebView webview;
  private Context context;
  private String itemId, itemRef, obj;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      editUserUI();
      editUserInit();

    } catch (Exception e) {
      // handle errors
    }
  }

  private void editUserUI() {
    context = getApplicationContext();
    
    itemId = getIntent().getStringExtra("itemId");
    itemRef = getIntent().getStringExtra("itemRef");
    try {
      JSONObject json = new JSONObject();
      json.put("id", itemId);
      json.put("ref", itemRef);
      obj = json.toString();

    } catch (Exception e) {

    }
    
    
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    scroll = new ScrollView(context);
    scroll.setLayoutParams(params);

    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);

    webview = new WebView(context);
    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("edit.html");
    webFragment.executeJavascriptWithParameters("loadItemId", obj);

    webview = webFragment.initializeWebFragment();
    root.addView(webview);
    scroll.addView(root);

    setContentView(scroll);
  }

  private void editUserInit() {

    
  }
}
