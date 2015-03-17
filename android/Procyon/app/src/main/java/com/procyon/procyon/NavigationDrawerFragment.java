package com.procyon.procyon;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {


    public static final String PREP_FILE_NAME = "navigationDrawerPrepFile";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private boolean userLearnedDrawer;
    private boolean fromSavedInstanceState;
    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLearnedDrawer = Boolean.valueOf(readFromSharedPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            fromSavedInstanceState = true;
        }


    }

    public void setUp(int fragment_navigation_drawer, DrawerLayout drawerLayout, final Toolbar toolbar) {
        this.drawerLayout = drawerLayout;

        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!userLearnedDrawer) {
                    userLearnedDrawer = true;
                    saveToSharedPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, userLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

        };

        containerView = getActivity().findViewById(fragment_navigation_drawer);

        if (!userLearnedDrawer && !fromSavedInstanceState) {
            drawerLayout.openDrawer(containerView);
        }


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawerLayout.post(new Runnable() {
                              @Override
                              public void run() {
                                  actionBarDrawerToggle.syncState();
                              }
                          }
        );
    }

    public static void saveToSharedPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromSharedPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREP_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
