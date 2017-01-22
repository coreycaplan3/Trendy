package caplan.innovations.trendy.recyclers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import caplan.innovations.trendy.R;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.recyclers.NewsViewHolder.OnNewsActionListenerInternal;

/**
 * Created by Corey Caplan on 1/20/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To perform all data-binding for the main news item {@link NewsItem}
 */
public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> implements
        OnNewsActionListenerInternal {

    /**
     * A listener used to transfer actions from this adapter to the activity.
     */
    public interface OnNewsItemActionListener {

        /**
         * Called when a news item is clicked
         *
         * @param item The news item on which the user clicked.
         */
        void onNewsItemClick(NewsItem item);
    }

    @NonNull
    private ArrayList<NewsItem> mData;
    private OnNewsItemActionListener mListener;
    private Fragment mFragment;
    private Activity mActivity;

    /**
     * @param newsItems The news items instance that will serve as the data model.
     * @param listener  The {@link OnNewsItemActionListener} to send different actions back to the
     *                  implementor.
     * @param fragment  The {@link Fragment} in which the adapter operates. Used with the Glide
     *                  library for fine-tuning the image retrieval process.
     */
    public NewsItemRecyclerAdapter(@NonNull ArrayList<NewsItem> newsItems,
                                   OnNewsItemActionListener listener,
                                   Fragment fragment) {
        mData = newsItems;
        mListener = listener;
        mFragment = fragment;
    }

    /**
     * @param newsItems The news items instance that will serve as the data model.
     * @param listener  The {@link OnNewsItemActionListener} to send different actions back to the
     *                  implementor.
     * @param activity  The {@link Activity} in which the adapter operates. Used with the Glide
     *                  library for fine-tuning the image retrieval process.
     */
    public NewsItemRecyclerAdapter(@NonNull ArrayList<NewsItem> newsItems,
                                   OnNewsItemActionListener listener,
                                   Activity activity) {
        mData = newsItems;
        mListener = listener;
        mActivity = activity;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get an instance of LayoutInflater to inflate our view from XML
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View newsView = inflater.inflate(R.layout.news_feed_item, parent, false);
        return new NewsViewHolder(newsView, this);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsItem newsItem = mData.get(position);

        if (mFragment != null) {
            holder.bind(newsItem, mFragment);
        } else if (mActivity != null) {
            holder.bind(newsItem, mActivity);
        } else {
            throw new NullPointerException("Activity and Fragment cannot be null!");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onNewsClickInternal(int position) {
        NewsItem newsItem = mData.get(position);
        mListener.onNewsItemClick(newsItem);
    }

    @Override
    public void onFavoriteClick(int position) {
        NewsItem newsItem = mData.get(position);
        newsItem.setIsFavorite(!newsItem.isFavorite());
        notifyItemChanged(position);
    }

}
