package caplan.innovations.trendy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter.OnNewsItemActionListener;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To show the news articles that the user has selected to be a "favorite"
 */
public class FavoritesActivity extends NavigationDrawerActivity implements OnNewsItemActionListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @SuppressWarnings("FieldCanBeLocal")
    private NewsItemRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTitle(R.string.favorites);

        ArrayList<NewsItem> items = new ArrayList<>();
        items.add(NewsItem.getDummy());

        /* Pass "this" since this activity implements OnNewsItemActionListener */
        mAdapter = new NewsItemRecyclerAdapter(items, this, this);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    int getSelectedNavigationMenuItem() {
        return R.id.navigation_drawer_menu_favorites;
    }

    @Override
    int getContentView() {
        return R.layout.activity_favorites;
    }

    @Override
    public void onNewsItemClick(NewsItem item) {
        Intent intent = NewsDetailsActivity.createIntent(item);
        startActivity(intent);
    }

}
