package caplan.innovations.trendy.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.database.NewsDatabaseController;
import caplan.innovations.trendy.model.NewsItem;
import io.realm.RealmChangeListener;

/**
 * Created by Corey Caplan on 1/20/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To display the details of our news item
 */
public class NewsDetailsActivity extends BaseActivity implements RealmChangeListener<NewsItem> {

    private static final String TAG = NewsDetailsActivity.class.getSimpleName();

    private static final String KEY_NEWS_TITLE = "NEWS_TITLE";

    @BindDrawable(R.drawable.ic_cloud_off_black_48dp)
    Drawable mErrorDrawable;

    @BindString(R.string.full_article)
    String FULL_ARTICLE;

    @BindView(R.id.floating_action_button)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.news_details_image)
    ImageView mNewsImageView;

    @BindView(R.id.news_details_title_text_view)
    TextView mNewsTitleTextView;

    @BindView(R.id.news_details_author_text_view)
    TextView mNewsAuthorTextView;

    @BindView(R.id.news_details_description_text_view)
    TextView mNewsDescriptionTextView;

    @BindView(R.id.news_details_url_text_view)
    TextView mNewsUrlTextView;

    private String mNewsItemTitle;

    /**
     * @param newsItemTitle The title of the {@link NewsItem} that should be shown in this activity
     * @return An intent that can be used to start this activity.
     */
    public static Intent createIntent(String newsItemTitle) {
        Intent intent = new Intent(TrendyApplication.context(), NewsDetailsActivity.class);
        intent.putExtra(KEY_NEWS_TITLE, newsItemTitle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNewsItemTitle = savedInstanceState.getString(KEY_NEWS_TITLE);
        } else {
            mNewsItemTitle = getIntent().getStringExtra(KEY_NEWS_TITLE);
        }

        setupBackButton();
        // Register links so they are clickable
        mNewsUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());

        NewsItem item = NewsDatabaseController.getInstance()
                .getNewsByTitle(getRealm(), mNewsItemTitle);
        item.addChangeListener(this);

        bindNewsItem(item);
    }

    @Override
    int getContentView() {
        return R.layout.activity_news_details;
    }

    @SuppressWarnings("deprecation")
    private void bindNewsItem(NewsItem newsItem) {
        mNewsTitleTextView.setText(newsItem.getTitle());

        if (newsItem.getAuthor() != null) {
            mNewsAuthorTextView.setVisibility(View.VISIBLE);
            mNewsAuthorTextView.setText(newsItem.getAuthor());
        } else {
            mNewsAuthorTextView.setVisibility(View.GONE);
        }

        if (newsItem.getDescription() != null) {
            mNewsDescriptionTextView.setVisibility(View.VISIBLE);
            mNewsDescriptionTextView.setText(newsItem.getDescription());
        } else {
            mNewsDescriptionTextView.setVisibility(View.GONE);
        }

        if (newsItem.getUrlToArticle() != null) {
            mNewsUrlTextView.setVisibility(View.VISIBLE);
            Spanned html = Html.fromHtml(
                    "<a href=\"" + newsItem.getUrlToArticle() + "\">" + FULL_ARTICLE + "</a>"
            );
            mNewsUrlTextView.setText(html);
        } else {
            mNewsUrlTextView.setVisibility(View.GONE);
        }

        mErrorDrawable.setAlpha(68);

        Glide.with(this)
                .load(newsItem.getImageUrl())
                .error(mErrorDrawable)
                .into(mNewsImageView);

        bindFavorite(newsItem.isFavorite());
    }

    @Override
    public void onChange(NewsItem element) {
        Log.d(TAG, "onChange: Item changed, favorite: " + element.isFavorite());
        bindFavorite(element.isFavorite());
    }

    private void bindFavorite(boolean isFavorite) {
        if(isFavorite) {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border_red_24dp);
        }
    }

    @OnClick(R.id.floating_action_button)
    public void onFavoriteButtonClick() {
        NewsDatabaseController.getInstance()
                .toggleFavoriteWithTransaction(getRealm(), mNewsItemTitle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_NEWS_TITLE, mNewsItemTitle);
    }

}
