package com.harjup_kdhyne.TravelApp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
    /**
    * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
    * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
    * derivative, which will keep every activity loaded fragment in memory. If this becomes too memory
    * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
    */
    AppSectionsPagerAdapter myAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager myViewPager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("lifeCycle", "MainActivityOnCreate run");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();


        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //By disabling all the information in the actionbar, it should no longer be drawn
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        myViewPager = (ViewPager) findViewById(R.id.pager);
        myViewPager.setAdapter(myAppSectionsPagerAdapter);
        myViewPager.setOffscreenPageLimit(3);
        myViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < myAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(myAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        myViewPager.setCurrentItem(tab.getPosition());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
