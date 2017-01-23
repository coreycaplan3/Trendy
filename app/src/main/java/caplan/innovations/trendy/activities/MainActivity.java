package caplan.innovations.trendy.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import caplan.innovations.trendy.R;
import caplan.innovations.trendy.model.NewsItem;

/**
 * The main activity that is the entry-point for our app.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.news_feed_title_text_view)
    TextView mTitleTextView;

    @BindView(R.id.news_feed_author_text_view)
    TextView mAuthorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Keep in mind, BaseActivity calls ButterKnife#bind for us. However, in later situations
        // we will have to do it manually, like if we're working with a RecyclerView.

        // Perform data binding using a dummy news item
        NewsItem newsItem = new NewsItem("Android Course 101", "Corey", null, null, null);
        mTitleTextView.setText(newsItem.getTitle());
        mAuthorTextView.setText(newsItem.getAuthor());
    }

    @LayoutRes
    @Override
    int getContentView() {
        return R.layout.activity_main;
    }

}
