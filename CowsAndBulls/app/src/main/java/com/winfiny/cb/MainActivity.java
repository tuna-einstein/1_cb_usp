package com.winfiny.cb;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.winfiny.cb.smenu.SlidingMenuListFragment;


public class MainActivity extends Activity {

    private SlidingMenu mSMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSMenu = new SlidingMenu(this);
        mSMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSMenu.setMode(SlidingMenu.LEFT);
        mSMenu.setFadeDegree(0.35f);
        mSMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSMenu.setShadowDrawable(R.drawable.shadow);
        mSMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);

        mSMenu.setMenu(R.layout.s_menu_frame);

        getFragmentManager().beginTransaction()
                .replace(R.id.s_menu_frame, new SlidingMenuListFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onBackPressed() {
        if (mSMenu.isMenuShowing()) {
            mSMenu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    public void replaceContentFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.list_fragment, fragment).commit();
        mSMenu.showContent();
    }
}
