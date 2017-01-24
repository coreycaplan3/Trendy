package caplan.innovations.trendy.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.fragments.BbcNewsFragment;
import caplan.innovations.trendy.fragments.GoogleNewsFragment;

/**
 * The main activity that is the entry-point for our app.
 */
public class MainActivity extends NavigationDrawerActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Keep in mind, BaseActivity calls ButterKnife#bind for us. However, in later situations
        // we will have to do it manually, like if we're working with a RecyclerView.

        PagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

        // #setupWithViewPager handles tab clicks and populating the tabs for us!
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    int getSelectedNavigationMenuItem() {
        return R.id.navigation_drawer_menu_news;
    }

    @LayoutRes
    @Override
    int getContentView() {
        return R.layout.activity_main;
    }

    /**
     * A private class that is used to
     */
    private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private static final int COUNT = 2;

        private MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GoogleNewsFragment();
                case 1:
                    return new BbcNewsFragment();
                default:
                    throw new IllegalArgumentException("Found illegal position: " + position);
            }
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Note, these titles are use to auto-populate the tabs' names
            Context context = TrendyApplication.context();
            switch (position) {
                case 0:
                    return context.getString(R.string.google_news);
                case 1:
                    return context.getString(R.string.bbc_news);
                default:
                    throw new IllegalArgumentException("Found illegal position: " + position);
            }
        }

    }

}
