package com.example.user.mysettingspreferencefragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bazinga on 9/24/2016.
 */
public class ActionBarTabTestSecond extends FragmentActivity implements ActionBar.TabListener, SimpleFragment.OnFragmentInteractionListener, SimpleFragmentSecond.OnFragmentInteractionListenerSecond {
    ActionBar actionBar;
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_tab_test_second);

        fragmentList.add(SimpleFragment.newInstance(0));
        fragmentList.add(SimpleFragment.newInstance(1));
        fragmentList.add(SimpleFragmentSecond.newInstance(2));
        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("ONE").setTabListener(this), true);
        actionBar.addTab(actionBar.newTab().setText("TWO").setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText("THREE").setTabListener(this), false);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        // ft.replace(android.R.id.content, fragmentList.get(tab.getPosition()));
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragmentList.get(tab.getPosition())).commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteractionSecond(Uri uri) {

    }
}
