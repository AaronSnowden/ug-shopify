package com.ugshopify.app;

//import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import api.ugshopify.app.application.User;

public class SplashActivity extends Activity {

  private LinearLayout root;
  private ImageView appTitle;
  private Context context;
  private LayoutParams params;

  /** Duration of wait * */
  private final int SPLASH_DISPLAY_LENGTH = 2000;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    try {
      splash_gui();
      splash_init();
    } catch (Exception e) {
      Toast(e.toString());
    }
  }

  public void splash_gui() {

    context = getApplicationContext();

    params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    root = new LinearLayout(this);
    root.setGravity(Gravity.CENTER);
    root.setOrientation(LinearLayout.VERTICAL);

    appTitle = new ImageView(context);
    appTitle.setImageResource(R.drawable.app_icon);
    appTitle.setLayoutParams(params);

    root.addView(appTitle);

    setContentView(root);
  }

  public void splash_init() {

    /* New Handler to start the Menu-Activity
     * and close this Splash-Screen after some seconds.*/
    new Handler()
        .postDelayed(
            new Runnable() {
              @Override
              public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                // check conn

                try {

                  if (!isConnected(context)) {
                    Toast("Connection Not Available");
                  }

                  if (isLoggedIn()) {
                    SplashActivity.this.startActivity(
                        new Intent(SplashActivity.this, MainActivity.class));
                  } else {
                    SplashActivity.this.startActivity(
                        new Intent(SplashActivity.this, LoginActivity.class));
                  }

                } catch (Exception e) {

                  Toast(e.toString());
                }
                SplashActivity.this.finish();
              }
            },
            SPLASH_DISPLAY_LENGTH);
  }

  public void Toast(String val) {

    Toast.makeText(context, val, Toast.LENGTH_LONG).show();
  }

  public boolean isLoggedIn() {

    User user = new User(context);
    final String user_id = user.getUserid();
    final String user_name = user.getUsername();
    final String user_email = user.getUseremail();

    if (user_id == null || user_name == null || user_email == null) {
      return false;
    } else {
      return true;
    }
  }

  public boolean isConnected(Context context) {
    ConnectivityManager connectivity =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity == null) {

      return false;
    }
    // get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
    NetworkInfo[] info = connectivity.getAllNetworkInfo();
    // make sure that there is at least one interface to test against
    if (info != null) {
      // iterate through the interfaces
      for (int i = 0; i < info.length; i++) {
        // check this interface for a connected state
        if (info[i].getState() == NetworkInfo.State.CONNECTED) {

          return true;
        }
      }
    }
    return false;
  }
}
