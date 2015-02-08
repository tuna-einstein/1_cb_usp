package com.winfiny.cb;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.winfiny.cb.R;

public class NewGameActivity extends Activity implements
        KeyboardFragment.OnKeyboardFragmentInteractionListener,
        NewGameFragment.OnNewGameFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new NewGameFragment())
                    .commit();

            getFragmentManager().beginTransaction()
                    .replace(R.id.keyboardContainer, new KeyboardFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onKeyPressed(int primaryCode) {

        NewGameFragment fragment = (NewGameFragment) getFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onButtonPressed(primaryCode);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
