package caplan.innovations.trendy.fragments;

import caplan.innovations.trendy.network.NewsNetwork;
import caplan.innovations.trendy.receivers.NewsBroadcastReceiver;
import caplan.innovations.trendy.model.NewsItem;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A fragment that retrieves its news from Google News
 */
public class GoogleNewsFragment extends BaseNewsFragment {

    @Override
    int getNewsType() {
        return NewsItem.NEWS_GOOGLE;
    }

    @Override
    String getNewsIntentFilter() {
        return NewsBroadcastReceiver.INTENT_FILTER_GOOGLE_NEWS;
    }

}
