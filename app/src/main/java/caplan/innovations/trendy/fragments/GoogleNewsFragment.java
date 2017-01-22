package caplan.innovations.trendy.fragments;

import caplan.innovations.trendy.network.NewsNetwork;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A fragment that retrieves its news from Google News
 */
public class GoogleNewsFragment extends BaseNewsFragment {

    @Override
    int getNewsType() {
        return NewsNetwork.NEWS_GOOGLE;
    }

}