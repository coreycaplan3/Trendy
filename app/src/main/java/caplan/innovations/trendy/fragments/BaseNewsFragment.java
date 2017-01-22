package caplan.innovations.trendy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.activities.NewsDetailsActivity;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.network.NewsNetwork;
import caplan.innovations.trendy.network.OnGetNewsCompleteListener;
import caplan.innovations.trendy.network.TrendyRequestQueue;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter.OnNewsItemActionListener;
import caplan.innovations.trendy.utilities.UiUtility;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A base class for retrieving the news. Sub classes will implement logic for
 * getting/refreshing data
 */
abstract class BaseNewsFragment extends Fragment implements OnNewsItemActionListener,
        OnRefreshListener, OnGetNewsCompleteListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsItemRecyclerAdapter mAdapter;

    @NonNull
    private ArrayList<NewsItem> mNewsItems = new ArrayList<>();

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        /* Pass "this" since BaseNewsFragment implements OnNewsItemActionListener */
        mAdapter = new NewsItemRecyclerAdapter(mNewsItems, this, this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onNewsItemClick(NewsItem item) {
        Intent intent = NewsDetailsActivity.createIntent(item);
        this.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        @NewsNetwork.NewsType int newsType = getNewsType();

        /* Pass "this" since we implement the OnGetNewsCompleteListener interface */
        switch (newsType) {
            case NewsNetwork.NEWS_GOOGLE:
                NewsNetwork.getGoogleNewsAsync(this);
                break;
            case NewsNetwork.NEWS_BBC:
                NewsNetwork.getBbcNewsAsync(this);
                break;
            default:
                throw new IllegalArgumentException("Invalid new type, found: " + newsType);
        }
    }

    /**
     * @return The type of news that the given fragment should display
     */
    @NewsNetwork.NewsType
    abstract int getNewsType();

    @Override
    public void onGetNewsComplete(@Nullable ArrayList<NewsItem> newsItems) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (newsItems == null) {
            UiUtility.noConnectionSnackbar(getView(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
            });
        } else {
            // Propagate the results to the adapter, to update the list
            mNewsItems.clear();
            mNewsItems.addAll(newsItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // The view will be destroyed so let's unbind the views from the fragment
        // http://jakewharton.github.io/butterknife/
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TrendyRequestQueue.cancelAll();
    }
}
