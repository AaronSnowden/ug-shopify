package com.ugshopify.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import api.ugshopify.app.application.ViewHelper;
import api.ugshopify.app.application.WebFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends Activity {

  private LinearLayout root;
  private WebView webview;
  private Context context;
  private TextView title;
  private AutoCompleteTextView searchView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      search_ui();
      search_init();

    } catch (Exception e) {
      // handle errors
    }
  }

  private void search_ui() {
    context = getApplicationContext();
    
    Bundle bundle = getIntent().getExtras();
    String[] temp_names = bundle.getStringArray("namesArray");
    final List<String> names = new ArrayList<String>(Arrays.asList(temp_names));

   new ViewHelper(this.getWindow()).setStatusBarColor();

    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(params);

    title = new TextView(context);
    title.setText("Search");
    title.setPadding(5, 20, 15, 12);
    title.setTextSize(20);
    title.setBackgroundColor(Color.parseColor("#00c5cd"));
    searchView = new AutoCompleteTextView(context);
    searchView.setHint("Search Here");

    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, names);

    searchView.setAdapter(adapter);
    searchView.setThreshold(1);

    webview = new WebView(context);
    WebFragment webFragment = new WebFragment(context, webview);
    webFragment.setIndexPage("search.html");

    webview = webFragment.initializeWebFragment();

    root.addView(title);
    root.addView(searchView);
    root.addView(webview);

    setContentView(root);
  }

  private void search_init() {

    searchView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) parent.getItemAtPosition(position);
            queryItems(item);
          }
        });
  }

  public void queryItems(String input) {
/*
    webview.loadUrl("javascript:clearGrid()");

    new ConnectionClass(webview, "drawUi")
        .execute(
            "https://ug-shopify.firebaseio.com/ug-shopify-items.json?orderBy=\"name\"&equalTo=\""
                + input
                + "\"&print=pretty");
                */
  }
}
