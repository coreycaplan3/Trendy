package caplan.innovations.trendy.recyclers;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.model.NewsItem;

/**
 * Created by Corey Caplan on 1/20/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To represent a single item of the news, in our news feed
 */
class NewsViewHolder extends RecyclerView.ViewHolder {

    /**
     * A listener used to pass click events from this holder to the adapter
     * <p></p>
     * This interface is useful for passing actions to many different adapters.
     */
    interface OnNewsActionListenerInternal {

        /**
         * Called when the base view holder is clicked
         *
         * @param position The position at which the click occurred.
         */
        void onNewsClickInternal(int position);

        /**
         * Called when the favorite button is clicked
         *
         * @param position The position at which the click occurred
         */
        void onFavoriteClick(int position);
    }

    @BindDrawable(R.drawable.ic_cloud_off_black_48dp)
    Drawable mErrorDrawable;

    @BindView(R.id.news_feed_image)
    ImageView mImageView;

    @BindView(R.id.news_feed_title_text_view)
    TextView mTitleTextView;

    @BindView(R.id.news_feed_author_text_view)
    TextView mAuthorTextView;

    @BindView(R.id.news_feed_favorite_button)
    ImageButton mImageButton;

    private OnNewsActionListenerInternal mListenerInternal;

    NewsViewHolder(View itemView, OnNewsActionListenerInternal listenerInternal) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mErrorDrawable.setAlpha(68);
        mListenerInternal = listenerInternal;
    }

    void bind(NewsItem newsItem, Activity activity) {
        RequestManager glideRequestManager = Glide.with(activity);
        bind(newsItem, glideRequestManager);
    }

    void bind(NewsItem newsItem, Fragment fragment) {
        RequestManager glideRequestManager = Glide.with(fragment);
        bind(newsItem, glideRequestManager);
    }

    private void bind(NewsItem newsItem, RequestManager glideRequestManager) {
        mTitleTextView.setText(newsItem.getTitle());
        mAuthorTextView.setText(newsItem.getAuthor());

        if (newsItem.isFavorite()) {
            mImageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            mImageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        if (newsItem.getImageUrl() != null) {
            glideRequestManager.load(newsItem.getImageUrl())
                    .error(mErrorDrawable)
                    .into(mImageView);
        } else {
            mImageView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.news_feed_container)
    void onNewsItemClick() {
        // Use the adapter position to get the position of the click
        int position = getAdapterPosition();
        mListenerInternal.onNewsClickInternal(position);
    }

    @OnClick(R.id.news_feed_favorite_button)
    void onFavoriteButtonClick() {
        // Use the adapter position to get the position of the click
        int position = getAdapterPosition();
        mListenerInternal.onFavoriteClick(position);
    }

}
