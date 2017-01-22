package caplan.innovations.trendy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.services.NewsIntentService;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To receive result broadcasts from the {@link LocalBroadcastManager} after the
 * {@link NewsIntentService} broadcasts a result.
 * <p></p>
 * This receiver should be registered in the activity/fragment's #onCreate and unregistered in the
 * corresponding #onDestroy
 */
public class NewsBroadcastReceiver extends BroadcastReceiver {

    private OnNewsItemsReceivedFromBroadcastListener mListener;

    public static final String INTENT_FILTER_GOOGLE_NEWS = "NEWS_BROADCAST_RECEIVER_GOOGLE";
    public static final String INTENT_FILTER_BBC_NEWS = "NEWS_BROADCAST_RECEIVER_BBC";

    public NewsBroadcastReceiver(OnNewsItemsReceivedFromBroadcastListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<NewsItem> newsItems = getNewsItemsFromIntent(intent);
        mListener.onNewsItemsReceivedFromBroadcast(newsItems);
    }

    private static ArrayList<NewsItem> getNewsItemsFromIntent(Intent intent) {
        return intent.getParcelableArrayListExtra(NewsIntentService.KEY_NEWS_ITEMS);
    }

}
