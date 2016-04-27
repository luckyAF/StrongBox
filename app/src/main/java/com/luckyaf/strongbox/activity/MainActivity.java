package com.luckyaf.strongbox.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.fragment.codeBook.CodeBookFragment;
import com.luckyaf.strongbox.fragment.contact.ContactFragment;
import com.luckyaf.strongbox.fragment.diary.DiaryFragment;
import com.luckyaf.strongbox.fragment.file.FileMainFragment;
import com.luckyaf.strongbox.fragment.index.IndexFragment;
import com.luckyaf.strongbox.fragment.program.ProgramFragment;
import com.luckyaf.strongbox.fragment.settings.SettingsFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment _currentFragment;
    private Fragment _lastFragment;
    private Toolbar _mToolbar;

    private static long DOUBLE_CLICK_TIME = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initData();
    }


    @Override
    protected void initToolbar() {

    }

    public void initWidget(){
        _mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(_mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, _mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initData(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        Fragment indexFragment = IndexFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.frame_main,indexFragment).commit();
        _lastFragment = indexFragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(onBackPress()){
                super.onBackPressed();
            }
        }
    }



    protected int getStatusBarColor() {
        return getColorPrimary();
    }

    private int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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



    public void showSnackBar(String msg) {
        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String title = getString(R.string.app_name);;
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case R.id.nav_index:
                _currentFragment = IndexFragment.newInstance(bundle);
                title = getString(R.string.menu_index);
                break;
            case R.id.nav_file:
                _currentFragment = FileMainFragment.newInstance(bundle);
                title = getString(R.string.menu_file);
                break;
            case R.id.nav_program:
                _currentFragment = ProgramFragment.newInstance(bundle);
                title = getString(R.string.menu_program);
                break;
            case R.id.nav_contact:
                _currentFragment = ContactFragment.newInstance(bundle);
                title = getString(R.string.menu_contact);
                break;
            case R.id.nav_diary:
                _currentFragment = DiaryFragment.newInstance(bundle);
                title = getString(R.string.menu_diary);
                break;
            case R.id.nav_code_book:
                _currentFragment = CodeBookFragment.newInstance(bundle);
                title = getString(R.string.menu_code_books);
                break;
            case R.id.nav_settings:
                _currentFragment = SettingsFragment.newInstance(bundle);
                title = getString(R.string.menu_settings);
                break;
            default:
                _currentFragment = _lastFragment;
                break;
        }


        if(!_currentFragment.isAdded()){
            fragmentTransaction.hide(_lastFragment).add(R.id.frame_main,_currentFragment).commit();
        }else{
            fragmentTransaction.hide(_lastFragment).show(_currentFragment).commit();
        }
        _lastFragment = _currentFragment;
        _mToolbar.setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onBackPress() {
        if (System.currentTimeMillis() - DOUBLE_CLICK_TIME > 2000) {
            DOUBLE_CLICK_TIME = System.currentTimeMillis();
            this.showSnackBar("再按一次退出...");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
