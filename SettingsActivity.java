package com.ugshopify.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.*;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import api.ugshopify.app.application.User;
import api.ugshopify.app.application.ViewHelper;

public class SettingsActivity extends Activity {

  private LinearLayout root;
  private TextView icon;
  private Drawable app_icon;
  private ListView list;
  private ArrayAdapter<String> adapter;
  private String[] list_data;
  private TextView terms;
  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    appGui();
    appInit();
  }

  public void appGui() {

    new ViewHelper(this.getWindow()).setStatusBarColor();

    context = getApplicationContext();
    String terms_cond = "";

    terms = new TextView(context);
    terms.setText(terms_cond);
    terms.setTextColor(Color.WHITE);

    root = new LinearLayout(context);
    root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    root.setOrientation(LinearLayout.VERTICAL);

    icon = new TextView(context);
    icon.setText("Ug-Shopify!");
    icon.setTextSize(28);
    icon.setGravity(Gravity.CENTER);
    icon.setTypeface(Typeface.SERIF, Typeface.BOLD);
    icon.setBackgroundColor(Color.parseColor("#00c5cd"));
    icon.setPadding(0, 25, 0, 30);
    icon.setCompoundDrawablePadding(8);
    app_icon = getResources().getDrawable(R.drawable.app_icon);
    app_icon.setBounds(0, 0, 180, 180);
    icon.setCompoundDrawables(null, app_icon, null, null);

    list_data =
        new String[] {
          "Customise",
          "Send Feedback",
          "About Ug-Shopify ",
          "About Developer",
          "App Licences",
          "Terms And Conditions",
          "Log out account",
          "Delete Account"
        };

    adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list_data);

    list = new ListView(context);
    list.setAdapter(adapter);

    root.addView(icon);
    root.addView(list);

    setContentView(root);
  }

  public void appInit() {
    list.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub

            switch (position) {
              case 0:
                // showColorDialog();
                break;
              case 1:
                showFeedbackDialog();
                break;
              case 2:
                showAboutApp();
                break;
              case 3:
                showAboutDeveloper();
                break;

              case 4:
                showAppLicenses();
                break;

              case 5:
                showTermsAndConditions();
                break;

              case 6:
                logoutUser();
                break;

              case 7:
                deleteUser();
                break;
            }
          }
        });
  }

  public void logoutUser() {
    // clear user storage

    User user = new User(context);
    try {
      user.setUserDataWithKey(null, null, null, null, null, null);
    } catch (Exception e) {
      Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    }
    // goto logg in page
    // clear activity stack

    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(
        Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
    finish();
  }

  public void deleteUser() {

    logoutUser();
    Toast.makeText(context, "Account will be deleted in 48 hours.", Toast.LENGTH_SHORT).show();
  }

  public void showFeedbackDialog() {

    String path = "https://theaaronandroiddeveloper.wordpress.com/contact/";

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(path));
    startActivity(intent);
  }

  public void showAboutApp() {
    String path = "https://theaaronandroiddeveloper.wordpress.com/2020/06/06/ug-shopify-app/";

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(path));
    startActivity(intent);
  }

  public void showAboutDeveloper() {

    String path = "https://theaaronandroiddeveloper.wordpress.com/about/";

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(path));
    startActivity(intent);
  }

  public void showAppLicenses() {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    // add a list
    String[] animals = {
      "Android Open Source Project - App Compat Library", "simplejson", "Apache Licences"
    };
    builder.setItems(
        animals,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            switch (which) {
              case 0:
              case 1:
              case 2:
              case 3:
              case 4:
                showLicence();
            }
          }
        });

    builder.create().show();
  }

  public void showLicence() {

    String path = "https://www.apache.org/licenses";

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(path));
    startActivity(intent);
  }

  public void showTermsAndConditions() {

    String path = "https://theaaronandroiddeveloper.wordpress.com/2020/06/06/ug-shopify-app/";

    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(path));
    startActivity(intent);
  }

  public void showColorDialog() {

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);

    // Set the alert dialog title
    builder.setTitle("Choose a color");

    // Initializing an array of flowers
    final String[] colors =
        new String[] {"Cyan", "Dahlia", "Day Lily", "Delphinium", "Desert Rose"};

    // Set a single choice items list for alert dialog
    builder.setSingleChoiceItems(
        colors, // Items list
        0, // Index of checked item (-1 = no selection)
        new DialogInterface.OnClickListener() // Item click listener
        {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            // Get the alert dialog selected item's text
            // String selectedItem = Arrays.asList(colors).get(i);

            // Push selected item to shared preference
            switch (i) {
              case 0:
              case 1:
              case 2:
              case 3:
              case 4:
              case 5:
            }
          }
        });

    // Set the a;ert dialog positive button
    builder.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            // Just dismiss the alert dialog after selection
            // Or do something now
          }
        });

    // Create the alert dialog
    AlertDialog dialog = builder.create();

    // Finally, display the alert dialog
    dialog.show();
  }
}
