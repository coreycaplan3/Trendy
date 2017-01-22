package caplan.innovations.trendy.fragments;

import caplan.innovations.trendy.database.NewsDatabaseController;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.receivers.NewsBroadcastReceiver;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A fragment that gets its news from BBC News
 */
public class BbcNewsFragment extends BaseNewsFragment {

    @Override
    int getNewsType() {
        return NewsItem.NEWS_BBC;
    }

    @Override
    String getNewsIntentFilter() {
        return NewsBroadcastReceiver.INTENT_FILTER_BBC_NEWS;
    }

    @Override
    RealmResults<NewsItem> getRealmResultsForAdapter(Realm realm) {
        return NewsDatabaseController.getInstance().getBbcNews(realm);
    }

}
