package com.ugshopify.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import api.ugshopify.app.application.ConnectionApi;
import api.ugshopify.app.application.Tab;
import api.ugshopify.app.application.User;
import api.ugshopify.app.application.ViewHelper;
import api.ugshopify.app.application.WebFragment;
import org.json.JSONObject;

public class ProfileActivity extends Activity implements View.OnClickListener {

  private ConnectionApi api;
  private LinearLayout root;
  private LinearLayout nav_bar;
  private TextView userTile;
  private TextView title;
  private StringBuilder user_info;
  private Drawable user_icon;
  private WebView webview;
  private Context context;
  private Tab home, add, req, edit, sentTab;
  private Drawable main, mail, useredit;
  private Drawable plus, sent;
  private JSONObject jsonObject;
  public String id;
  public String key;
  public String userObj;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      profile_ui();
      profile_init();

    } catch (Exception e) {
      Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }
  }

  private void profile_ui() {
    context = getApplicationContext();
    api = new ConnectionApi();

    User user = new User(context);
    final String userName = user.getUsername();
    final String userEmail = user.getUseremail();
    final String userPhone = user.getUsertel();
    id = user.getUserid();
    key = user.getUserkey();

    try {
      jsonObject = new JSONObject();
      jsonObject
          .put("user_id", id)
          .put("user_k", key)
          .put("user_n", userName)
          .put("user_e", userEmail)
          .put("user_p", userPhone);

      userObj = jsonObject.toString();
    } catch (Exception e) {

    }

    /**
     * jsonObject = new JSONObject();
     *
     * <p>try { jsonObject.put("total", totalItems).put("sold", soldItems); } catch (Exception e) {
     *
     * <p>}
     *
     * <p>user_data = jsonObject.toString();
     */

    // clear FLAG_TRANSLUCENT_STATUS flag:
    ViewHelper helper = new ViewHelper(this.getWindow());
    
    helper.setStatusBarColor();

    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);
nav_bar = new LinearLayout(context);
    nav_bar.setBackgroundColor(Color.parseColor("#00c5cd"));
    nav_bar.setLayoutParams(
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, helper.getStatusBarHeight()));

    nav_bar.setGravity(Gravity.LEFT);
    nav_bar.setOrientation(LinearLayout.HORIZONTAL);

    user_info = new StringBuilder();
    user_info
        .append("<span>")
        .append(userName)
        .append("</span>")
        .append("<span> |•| ")
        .append(userEmail)
        .append("</span>");

    title = new TextView(context);
    title.setBackgroundColor(Color.parseColor("#00c5cd"));
    title.setTextSize(16);
    title.setText(Html.fromHtml(user_info.toString()));
    title.setTypeface(Typeface.SERIF, Typeface.BOLD);
    title.setPadding(5, 20, 15, 12);

    userTile = new TextView(context);
    userTile.setBackgroundColor(Color.parseColor("#00c5cd"));
    userTile.setTextSize(14);
    userTile.setGravity(Gravity.CENTER);
    userTile.setTypeface(Typeface.SERIF, Typeface.BOLD);
    userTile.setPadding(0, 25, 0, 0);
    userTile.setCompoundDrawablePadding(8);
    user_icon = getResources().getDrawable(R.drawable.user_icon);
    user_icon.setBounds(0, 0, 180, 180);
    userTile.setCompoundDrawables(null, user_icon, null, null);
    userTile.setTextColor(Color.WHITE);
    userTile.setText(Html.fromHtml(user_info.toString()));

    webview = new WebView(context);
    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("profile.html");
    webFragment.executeJavascriptWithParameters("setUserKey", userObj);

    webview = webFragment.initializeWebFragment();

    /** * Custom TabView */
    final LinearLayout frame = new LinearLayout(context);
    frame.setLayoutParams(params);

    final HorizontalScrollView TabFrame = new HorizontalScrollView(context);

    TabFrame.setLayoutParams(
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    final LinearLayout TabHolder = new LinearLayout(context);
    TabHolder.setOrientation(LinearLayout.HORIZONTAL);
    TabHolder.setLayoutParams(
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    main = getResources().getDrawable(R.drawable.menu);
    home = new Tab(context, "Products", main);
    home.setId(0);
    home.init().setOnClickListener(this);

    plus = getResources().getDrawable(R.drawable.plus);
    add = new Tab(context, "Sell Item", plus);
    add.setId(1);
    add.init().setOnClickListener(this);

    mail = getResources().getDrawable(R.drawable.email);
    req = new Tab(context, "Requests", mail);
    req.setId(2);
    req.init().setOnClickListener(this);

    sent = getResources().getDrawable(R.drawable.sent);
    sentTab = new Tab(context, "Sent", sent);
    sentTab.setId(3);
    sentTab.init().setOnClickListener(this);

    useredit = getResources().getDrawable(R.drawable.useredit);
    edit = new Tab(context, "Details", useredit);
    edit.setId(4);
    edit.init().setOnClickListener(this);

    TabHolder.addView(home.init());
    TabHolder.addView(add.init());
    TabHolder.addView(req.init());
    TabHolder.addView(sentTab.init());
    TabHolder.addView(edit.init());

    TabFrame.addView(TabHolder);

    // frame.addView(TabFrame);
    // frame.addView(webview);
    // EOC for CTV

    root.addView(nav_bar);
    root.addView(TabFrame);
    root.addView(webview);

    // scrollview.addView(root);
    setContentView(root);
  }

  @Override
  public void onClick(View view) {

    switch (view.getId()) {
      case 0:
        setClick(
            home.getTabIcon(),
            req.getTabIcon(),
            sentTab.getTabIcon(),
            add.getTabIcon(),
            edit.getTabIcon());
        api.getItemsBy(id);
        break;

      case 1:
        setClick(
            add.getTabIcon(),
            req.getTabIcon(),
            sentTab.getTabIcon(),
            home.getTabIcon(),
            edit.getTabIcon());
        addItem();
        break;

      case 2:
        setClick(
            req.getTabIcon(),
            sentTab.getTabIcon(),
            add.getTabIcon(),
            home.getTabIcon(),
            edit.getTabIcon());
        break;

      case 3:
        setClick(
            sentTab.getTabIcon(),
            edit.getTabIcon(),
            home.getTabIcon(),
            req.getTabIcon(),
            add.getTabIcon());
        break;

      case 4:
        setClick(
            edit.getTabIcon(),
            sentTab.getTabIcon(),
            home.getTabIcon(),
            req.getTabIcon(),
            add.getTabIcon());
        showEditPage();
        break;

      default:
        setClick(home.getTabIcon(), req.getTabIcon(), edit.getTabIcon(), add.getTabIcon());
        api.getItemsBy(id);
        break;
    }
  }

  public void setClick(Drawable icon, Drawable... args) {

    icon.setColorFilter(
        new PorterDuffColorFilter(Color.parseColor("#088da5"), PorterDuff.Mode.SRC_IN));

    for (Drawable ic : args) {
      ic.setColorFilter(
          new PorterDuffColorFilter(Color.parseColor("#35424a"), PorterDuff.Mode.SRC_IN));
    }
  }

  public void addItem() {
    Intent intent = new Intent(context, AddItemActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void showEditPage() {

    webview.loadUrl("javascript:showUserEditPage()");
    webview.loadUrl("javascript:stopLoader()");
  }

  private void profile_init() {
    api.setUpdateView(webview);
    api.getItemsBy(id);
  }
}
