package caplan.innovations.trendy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.database.NewsDatabaseController;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter.OnNewsItemActionListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To show the news articles that the user has selected to be a "favorite"
 */
public class FavoritesActivity extends NavigationDrawerActivity implements OnNewsItemActionListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTitle(R.string.favorites);

        RealmResults<NewsItem> newsItems = NewsDatabaseController.getInstance()
                .getFavorites(getRealm());

        /* Pass "this" since this activity implements OnNewsItemActionListener */
        NewsItemRecyclerAdapter adapter =
                new NewsItemRecyclerAdapter(getRealm(), newsItems, this, this, false);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    public void onNewsItemClick(NewsItem item, ImageView imageView) {
        Intent intent = NewsDetailsActivity.createIntent(item.getTitle());
        String transitionName = getString(R.string.transition_image);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, transitionName);
        this.startActivity(intent, optionsCompat.toBundle());
    }

}
