package caplan.innovations.trendy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.model.NewsItem;

/**
 * Created by Corey Caplan on 1/20/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To display the details of our news item
 */
public class NewsDetailsActivity extends BaseActivity {

    private static final String TAG = NewsDetailsActivity.class.getSimpleName();

    private static final String KEY_NEWS = "NEWS";

    @BindString(R.string.full_article)
    String FULL_ARTICLE;

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

    private NewsItem mNewsItem;

    /**
     * @param newsItem The {@link NewsItem} that should be shown in this activity
     * @return An intent that can be used to start this activity.
     */
    public static Intent createIntent(NewsItem newsItem) {
        Intent intent = new Intent(TrendyApplication.context(), NewsDetailsActivity.class);
        intent.putExtra(KEY_NEWS, newsItem);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNewsItem = savedInstanceState.getParcelable(KEY_NEWS);
        } else {
            mNewsItem = getIntent().getParcelableExtra(KEY_NEWS);
        }

        setupBackButton();
        // Register links so they are clickable
        mNewsUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bindNewsItem();
    }

    @Override
    int getContentView() {
        return R.layout.activity_news_details;
    }

    @SuppressWarnings("deprecation")
    private void bindNewsItem() {
        mNewsTitleTextView.setText(mNewsItem.getTitle());

        if (mNewsItem.getAuthor() != null) {
            mNewsAuthorTextView.setVisibility(View.VISIBLE);
            mNewsAuthorTextView.setText(mNewsItem.getAuthor());
        } else {
            mNewsAuthorTextView.setVisibility(View.GONE);
        }

        if (mNewsItem.getDescription() != null) {
            mNewsDescriptionTextView.setVisibility(View.VISIBLE);
            mNewsDescriptionTextView.setText(mNewsItem.getDescription());
        } else {
            mNewsDescriptionTextView.setVisibility(View.GONE);
        }

        if (mNewsItem.getUrlToArticle() != null) {
            mNewsUrlTextView.setVisibility(View.VISIBLE);
            Spanned html = Html.fromHtml(
                    "<a href=\"" + mNewsItem.getUrlToArticle() + "\">" + FULL_ARTICLE + "</a>"
            );
            mNewsUrlTextView.setText(html);
        } else {
            mNewsUrlTextView.setVisibility(View.GONE);
        }

        //TODO get image from URL
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_NEWS, mNewsItem);
    }
}
