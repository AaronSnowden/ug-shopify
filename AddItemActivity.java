package com.ugshopify.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import api.ugshopify.app.application.User;
import api.ugshopify.app.application.ViewHelper;
import api.ugshopify.app.application.WebFragment;
import org.json.JSONObject;

public class AddItemActivity extends Activity {

  private LinearLayout root;
  private TextView title;
  private WebView webview;
  private Context context;
  private SharedPreferences user_storage;
  private static String user_data;

  private ValueCallback<Uri> mUploadMessage;
  public ValueCallback<Uri[]> uploadMessage;
  public static final int REQUEST_SELECT_FILE = 100;
  private static final int FILECHOOSER_RESULTCODE = 1;

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      if (requestCode == REQUEST_SELECT_FILE) {
        if (uploadMessage == null) return;
        uploadMessage.onReceiveValue(
            WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
        uploadMessage = null;
      }
    } else if (requestCode == FILECHOOSER_RESULTCODE) {
      if (null == mUploadMessage) return;
      // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
      // Use RESULT_OK only if you're implementing WebView inside an Activity
      Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
      mUploadMessage.onReceiveValue(result);
      mUploadMessage = null;
    } else
      Toast.makeText(
              AddItemActivity.this.getApplicationContext(),
              "Failed to Upload Image",
              Toast.LENGTH_LONG)
          .show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      addItemUI();
      addItemInit();

    } catch (Exception e) {
      // handle errors
    }
  }

  private void addItemUI() {
    context = getApplicationContext();
    getUserInfo();

    new ViewHelper(this.getWindow()).setStatusBarColor();

    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);

    title = new TextView(context);
    title.setBackgroundColor(Color.parseColor("#00c5cd"));
    title.setTextSize(20);
    title.setText("Sell Item");
    title.setTypeface(Typeface.SERIF, Typeface.BOLD);
    title.setPadding(10, 0, 0, 0);

    webview = new WebView(this);
    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("add_item.html");
    webFragment.executeJavascriptWithParameters("loadUserInfo", user_data);

    webview = webFragment.initializeWebFragment();

    webview.setWebChromeClient(
        new WebChromeClient() {
          // For 3.0+ Devices (Start)
          // onActivityResult attached before constructor
          protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
          }

          // For Lollipop 5.0+ Devices
          public boolean onShowFileChooser(
              WebView mWebView,
              ValueCallback<Uri[]> filePathCallback,
              WebChromeClient.FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
              uploadMessage.onReceiveValue(null);
              uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
              startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
              uploadMessage = null;
              Toast.makeText(
                      AddItemActivity.this.getApplicationContext(),
                      "Cannot Open File Chooser",
                      Toast.LENGTH_LONG)
                  .show();
              return false;
            }
            return true;
          }

          // For Android 4.1 only
          protected void openFileChooser(
              ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(
                Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
          }

          protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
          }
        });

    root.addView(title);
    root.addView(webview);

    setContentView(root);
  }

  private void addItemInit() {}

  public void getUserInfo() {

    User user = new User(context);
    final String user_name = user.getUsername();
    final String user_email = user.getUseremail();
    final String user_id = user.getUserid();
    final String user_tel = user.getUsertel();

    JSONObject jsonObject = new JSONObject();

    try {
      jsonObject
          .put("user_id", user_id)
          .put("user_name", user_name)
          .put("user_email", user_email)
          .put("user_tel", user_tel);
    } catch (Exception e) {

    }

    user_data = jsonObject.toString();
  }
}
