package caplan.innovations.trendy.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;

import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.database.NewsDatabaseController;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.network.NewsNetwork;
import caplan.innovations.trendy.receivers.NewsBroadcastReceiver;
import io.realm.Realm;

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
     * from the network. Evaluates to a boolean, either true if successful, or false otherwise.
     */
    public static final String KEY_IS_NEWS_SUCCESSFUL = "IS_NEWS_SUCCESSFUL";

    public NewsIntentService() {
        super(TAG);
    }

    public static void getGoogleNews() {
        getNews(NewsItem.NEWS_GOOGLE);
    }

    public static void getBbcNews() {
        getNews(NewsItem.NEWS_BBC);
    }

    private static void getNews(@NewsItem.Type int newsType) {
        Intent intent = new Intent(TrendyApplication.context(), NewsIntentService.class);
        intent.putExtra(KEY_NEWS_TYPE, newsType);
        TrendyApplication.getInstance().startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        @NewsItem.Type
        int newsType = intent.getIntExtra(KEY_NEWS_TYPE, -1);
        //noinspection WrongConstant
        if (newsType == -1) {
            throw new IllegalArgumentException("Invalid news type, found: " + newsType);
        }

        JSONArray newsItemsAsJsonArray;
        switch (newsType) {
            case NewsItem.NEWS_GOOGLE:
                newsItemsAsJsonArray = NewsNetwork.getGoogleNewsAndBlock();
                intent = new Intent(NewsBroadcastReceiver.INTENT_FILTER_GOOGLE_NEWS);
                break;
            case NewsItem.NEWS_BBC:
                newsItemsAsJsonArray = NewsNetwork.getBbcNewsAndBlock();
                intent = new Intent(NewsBroadcastReceiver.INTENT_FILTER_BBC_NEWS);
                break;
            default:
                throw new IllegalArgumentException("Invalid news type, found: " + newsType);
        }

        if (newsItemsAsJsonArray != null) {
            Realm realm = Realm.getDefaultInstance();
            NewsDatabaseController.getInstance()
                    .insertNewsWithTransaction(realm, newsItemsAsJsonArray, newsType);
            realm.close();
        }

        intent.putExtra(KEY_IS_NEWS_SUCCESSFUL, newsItemsAsJsonArray != null);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(intent);
    }
}
