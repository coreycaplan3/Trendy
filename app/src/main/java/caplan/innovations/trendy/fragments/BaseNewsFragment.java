package caplan.innovations.trendy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter;
import caplan.innovations.trendy.recyclers.NewsItemRecyclerAdapter.OnNewsItemActionListener;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A base class for retrieving the news. Sub classes will implement logic for
 * getting/refreshing data
 */
abstract class BaseNewsFragment extends Fragment implements OnNewsItemActionListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @SuppressWarnings("FieldCanBeLocal")
    private NewsItemRecyclerAdapter mAdapter;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        ArrayList<NewsItem> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(NewsItem.getDummy());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        /* Pass "this" since BaseNewsFragment implements OnNewsItemActionListener */
        mAdapter = new NewsItemRecyclerAdapter(items, this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onNewsItemClick(NewsItem item) {
        Intent intent = NewsDetailsActivity.createIntent(item);
        this.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // The view will be destroyed so let's unbind the views from the fragment
        // http://jakewharton.github.io/butterknife/
        mUnbinder.unbind();
    }
}
