package com.ugshopify.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import api.ugshopify.app.application.ConnectionApi;
import api.ugshopify.app.application.TaskScheduler;
import api.ugshopify.app.application.User;
import api.ugshopify.app.application.ViewHelper;
import api.ugshopify.app.application.WebFragment;
import org.json.JSONObject;

public class MainActivity extends Activity {

  private LinearLayout root;
  private LinearLayout nav_bar;
  private WebView webview;
  private TextView title;
  private String user_data;
  private Context context;
  private JSONObject jsonObject;
  private LayoutParams params;
  private Drawable settings, category;
  private TextView menuBtn;
  private ViewHelper helper;
  private ConnectionApi api;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      main_ui();
      main_init();

    } catch (Exception e) {
      // handle errors
    }
  }

  private void main_ui() {
    context = getApplicationContext();

    helper = new ViewHelper(this.getWindow());
    helper.setStatusBarColor();

    User user = new User(context);
    final String user_id = user.getUserid();
    final String user_key = user.getUserkey();
    final String user_name = user.getUsername();
    final String user_email = user.getUseremail();
    final String user_phone = user.getUsertel();

    jsonObject = new JSONObject();

    try {
      jsonObject
          .put("Key", user_key)
          .put("Id", user_id)
          .put("Name", user_name)
          .put("Email", user_email)
          .put("Phone", user_phone);
    } catch (Exception e) {

    }

    user_data = jsonObject.toString();

    params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    params.setMargins(6, 6, 6, 6);

    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);

    nav_bar = new LinearLayout(context);
    nav_bar.setBackgroundColor(Color.parseColor("#00c5cd"));
    nav_bar.setLayoutParams(
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));

    nav_bar.setGravity(Gravity.LEFT);
    nav_bar.setOrientation(LinearLayout.HORIZONTAL);

    title = new TextView(context);
    title.setText("Ug-Shopify");
    title.setTextSize(20);
    title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    title.setTextColor(Color.parseColor("#42426f"));
    title.setTypeface(Typeface.SERIF, Typeface.BOLD);
    title.setPadding(5, 20, 15, 12);
    title.setCompoundDrawablePadding(8);
    title.setBackgroundColor(Color.parseColor("#00c5cd"));
    settings = getResources().getDrawable(R.drawable.menu);
    settings.setBounds(0, 0, 50, 50);
    title.setCompoundDrawables(null, null, settings, null);

    category = getResources().getDrawable(R.drawable.category);
    category.setBounds(0, 0, 50, 50);

    menuBtn = new TextView(context);
    menuBtn.setCompoundDrawables(null, null, category, null);
    menuBtn.setTextSize(20);
    menuBtn.setPadding(22, 20, 15, 12);
    menuBtn.setCompoundDrawablePadding(8);

    webview = new WebView(context);

    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("Template.html");
    webFragment.executeJavascriptWithParameters("AppMain", user_data);

    webview = webFragment.initializeWebFragment();

    // nav_bar.addView(menuBtn);
    // nav_bar.addView(title);

    root.addView(nav_bar);
    root.addView(webview);

    setContentView(root);
  }

  public void main_init() {

    api = new ConnectionApi();
    api.setUpdateView(webview);

    api.getAllItems();
    api.getItemsBy(new User(context).getUserid());
  }

  @Override
  public void onBackPressed() {

    try {

      new TaskScheduler(
              new TaskScheduler.StackListener() {
                @Override
                public void onTaskPopped(String task) {
                  if (task.equals("Done")) finish();
                  else webview.loadUrl("javascript:sub(" + task + ")");
                }
              })
          .popTask();

    } catch (Exception e) {
      Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
    }
  }
  
  public int getStatusBarHeight() {
  
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }
  
  
  
}
