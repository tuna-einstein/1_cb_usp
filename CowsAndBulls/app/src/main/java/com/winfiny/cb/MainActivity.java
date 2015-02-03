package com.winfiny.cb;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.list_fragment) == null) {
            GamesListFragment list = new GamesListFragment();
            fm.beginTransaction().add(R.id.list_fragment, list).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


//        if (id == R.id.action_logout) {
//            ParseUser.logOut();
//            startActivity(new Intent(this, LoginDispatchActivity.class));
//            finish();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
