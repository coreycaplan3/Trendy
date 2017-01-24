package caplan.innovations.trendy.helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import caplan.innovations.trendy.R;
import caplan.innovations.trendy.activities.FavoritesActivity;
import caplan.innovations.trendy.activities.MainActivity;
import caplan.innovations.trendy.application.TrendyApplication;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To simplify and wrap the setup code for NavigationDrawers in one class
 */
public class NavigationDrawerHelper implements OnNavigationItemSelectedListener, DrawerListener {

    private Activity mActivity;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    public NavigationDrawerHelper(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                                  NavigationView navigationView) {
        mActivity = activity;
        mDrawerLayout = drawerLayout;
        mNavigationView = navigationView;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /* Pass "this" since we implement these interfaces */
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(this);
    }

    private Intent mPendingIntent;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) {
            // The user reselected the current item, so we'll do nothing
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        switch (item.getItemId()) {
            case R.id.navigation_drawer_menu_news:
                mPendingIntent = new Intent(TrendyApplication.context(), MainActivity.class);
                mPendingIntent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.navigation_drawer_menu_favorites:
                mPendingIntent = new Intent(TrendyApplication.context(), FavoritesActivity.class);
                mPendingIntent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (mPendingIntent != null) {
            mActivity.startActivity(mPendingIntent);
            mPendingIntent = null;
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    /**
     * Sets the given item to a selected state and all others to unselected
     *
     * @param selectedItemId The ID of the menu item that should be selected
     */
    public void setSelectedMenuItem(@IdRes int selectedItemId) {
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = mNavigationView.getMenu().getItem(i);
            if (menuItem.getItemId() == selectedItemId) {
                menuItem.setChecked(true);
            } else {
                menuItem.setChecked(false);
            }
        }
    }

    /**
     * Sets all field variables to null so they can be garbage collected
     */
    public void free() {
        mActivity = null;
        mDrawerLayout = null;
        mNavigationView = null;
    }

}
