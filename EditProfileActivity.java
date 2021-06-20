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

public class EditProfileActivity extends Activity {

  private LinearLayout root;
  private ScrollView scroll;
  private WebView webview;
  private Context context;
  private String userId,userName,userEmail, obj;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      editProfileUI();
      editProfileInit();

    } catch (Exception e) {
      // handle errors
    }
  }

  private void editProfileUI() {
    context = getApplicationContext();
    
    userId = getIntent().getStringExtra("id");
    userName = getIntent().getStringExtra("name");
    userEmail = getIntent().getStringExtra("email");
    try {
      JSONObject json = new JSONObject();
      json.put("id", userId);
      json.put("name", userName);
      json.put("email", userEmail);
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
    webFragment.setIndexPage("edit-user.html");
    webFragment.executeJavascriptWithParameters("loaduserInfo", obj);

    webview = webFragment.initializeWebFragment();


    root.addView(webview);
    scroll.addView(root);

    setContentView(scroll);
  }

  private void editProfileInit() {


  }
}
