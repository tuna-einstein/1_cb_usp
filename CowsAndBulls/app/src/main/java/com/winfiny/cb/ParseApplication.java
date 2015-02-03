package com.winfiny.cb;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(
            this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
