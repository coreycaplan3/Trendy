package caplan.innovations.trendy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

    private static final String KEY_NEWS = "NEWS";

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
    }

    @Override
    int getContentView() {
        return R.layout.activity_news_details;
    }

}
