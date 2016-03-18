package com.luckyaf.strongbox.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.codeBook.CodeBookFragment;
import com.luckyaf.strongbox.fragment.contact.ContactFragment;
import com.luckyaf.strongbox.fragment.diary.DiaryFragment;
import com.luckyaf.strongbox.fragment.file.FileMainFragment;
import com.luckyaf.strongbox.fragment.index.IndexFragment;
import com.luckyaf.strongbox.fragment.program.ProgramFragment;
import com.luckyaf.strongbox.fragment.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment _currentFragment;
    private Fragment _lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initData();
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
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case R.id.nav_index:
                _currentFragment = IndexFragment.newInstance(bundle);
                break;
            case R.id.nav_file:
                _currentFragment = FileMainFragment.newInstance(bundle);
                break;
            case R.id.nav_program:
                _currentFragment = ProgramFragment.newInstance(bundle);
                break;
            case R.id.nav_contact:
                _currentFragment = ContactFragment.newInstance(bundle);
                break;
            case R.id.nav_diary:
                _currentFragment = DiaryFragment.newInstance(bundle);
                break;
            case R.id.nav_code_book:
                _currentFragment = CodeBookFragment.newInstance(bundle);
                break;
            case R.id.nav_settings:
                _currentFragment = SettingsFragment.newInstance(bundle);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
