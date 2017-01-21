package caplan.innovations.trendy.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.helpers.NavigationDrawerHelper;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A sub-class of BaseActivity that performs the necessary setup of having a
 * NavigationDrawer.
 */
abstract class NavigationDrawerActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private NavigationDrawerHelper mNavigationDrawerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigationDrawerHelper =
                new NavigationDrawerHelper(this, mDrawerLayout, mToolbar, mNavigationView);
        mNavigationDrawerHelper.setSelectedMenuItem(getSelectedNavigationMenuItem());
    }

    /**
     * @return The ID of the menu item in this navigation drawer that should be selected.
     */
    @IdRes
    abstract int getSelectedNavigationMenuItem();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mNavigationDrawerHelper != null) {
            mNavigationDrawerHelper.free();
            mNavigationDrawerHelper = null;
        }
    }
}
