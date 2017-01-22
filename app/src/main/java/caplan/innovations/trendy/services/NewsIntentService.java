package caplan.innovations.trendy.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.network.NewsNetwork;
import caplan.innovations.trendy.receivers.NewsBroadcastReceiver;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To provide a threading framework that lasts beyond the scope of the Activity
 * life cycle. This service can run in the background even if the user kills the app with the task
 * manager. This is what we want for long-running network operations. Otherwise, we run the risk
 * of having to cancel expensive network operations and waste the user's bandwidth.
 */
public class NewsIntentService extends IntentService {

    private static final String TAG = NewsIntentService.class.getSimpleName();
    private static final String KEY_NEWS_TYPE = "NEWS_TYPE";

    /**
     * The key used in this Intent Service's broadcast when news items are finished being retrieved
     * from the network.
     */
    public static final String KEY_NEWS_ITEMS = "NEWS_ITEMS";

    public NewsIntentService() {
        super(TAG);
    }

    public static void getGoogleNews() {
        getNews(NewsNetwork.NEWS_GOOGLE);
    }

    public static void getBbcNews() {
        getNews(NewsNetwork.NEWS_BBC);
    }

    private static void getNews(@NewsNetwork.NewsType int newsType) {
        Intent intent = new Intent(TrendyApplication.context(), NewsIntentService.class);
        intent.putExtra(KEY_NEWS_TYPE, newsType);
        TrendyApplication.getInstance().startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        int newsType = intent.getIntExtra(KEY_NEWS_TYPE, -1);
        if (newsType == -1) {
            throw new IllegalArgumentException("Invalid news type, found: " + newsType);
        }

        ArrayList<NewsItem> newsItems;
        switch (newsType) {
            case NewsNetwork.NEWS_GOOGLE:
                newsItems = NewsNetwork.getGoogleNewsAndBlock();
                intent = new Intent(NewsBroadcastReceiver.INTENT_FILTER_GOOGLE_NEWS);
                break;
            case NewsNetwork.NEWS_BBC:
                newsItems = NewsNetwork.getBbcNewsBlock();
                intent = new Intent(NewsBroadcastReceiver.INTENT_FILTER_BBC_NEWS);
                break;
            default:
                throw new IllegalArgumentException("Invalid news type, found: " + newsType);
        }

        intent.putExtra(KEY_NEWS_ITEMS, newsItems);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(intent);
    }
}
