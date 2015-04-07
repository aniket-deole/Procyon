package com.procyon.procyon;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.procyon.procyon.article.ArticleFragment;
import com.procyon.procyon.mainlist.AbstractExpandableDataProvider;
import com.procyon.procyon.mainlist.ExampleExpandableDataProviderFragment;
import com.procyon.procyon.mainlist.MainListFragment;
import com.procyon.procyon.mainlist.SlidingTabLayout;
import com.procyon.procyon.navigationdrawer.NavigationDrawerFragment;


public class Intro extends ActionBarActivity implements IFC {

    private static String FRAGMENT_TAG_DATA_PROVIDER = "main_list_fragment";
    private static final String FRAGMENT_TAG_ARTICLE = "article_fragment";

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    private Drawer.Result drawerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        drawerResult = new Drawer().withActivity(this)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withStatusBarColor(Color.TRANSPARENT)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawerResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PageAdapter(getFragmentManager(), viewPager));

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.sliding_tab_layout, R.id.sliding_tab_text);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });
        slidingTabLayout.setViewPager(viewPager);

        getFragmentManager().beginTransaction()
                .add(new ExampleExpandableDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    public void sendMessage(String data, int cx, int cy) {
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_ARTICLE);

        ArticleFragment articleFragment = new ArticleFragment();
        transaction.addToBackStack(FRAGMENT_TAG_DATA_PROVIDER)
                .replace(R.id.intro_linear_layout, articleFragment)
                .commit();

        articleFragment.setTextView(data, cx, cy);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerResult.getActionBarDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    class PageAdapter extends FragmentPagerAdapter {

        private ViewPager vp;
        public int fragId;
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        public PageAdapter(FragmentManager fm, ViewPager vp) {
            super(fm);
            this.vp = vp;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment mainListFragment = registeredFragments.get(position);
            if (mainListFragment == null) {
                mainListFragment = new MainListFragment();
                fragId = mainListFragment.getId();
                registeredFragments.put(position, mainListFragment);
            }

            return mainListFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return "CATEGORIES";

        }
    }

    public AbstractExpandableDataProvider getDataProvider() {
        PageAdapter adapter = (PageAdapter) viewPager.getAdapter();
        final Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        return ((ExampleExpandableDataProviderFragment) fragment).getDataProvider();
    }

}
