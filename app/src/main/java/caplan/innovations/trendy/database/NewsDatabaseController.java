package caplan.innovations.trendy.database;


import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import caplan.innovations.trendy.model.NewsItem;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Corey on 1/22/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A singleton wrapper around {@link Realm} that will serve as the method through
 * which to access to database for our news sources.
 */
public class NewsDatabaseController {

    private static final String TAG = NewsDatabaseController.class.getSimpleName();

    private static int MAX_NEWS_FEED_SIZE = 100;

    private static NewsDatabaseController sInstance;

    public static NewsDatabaseController getInstance() {
        if (sInstance == null) {
            sInstance = new NewsDatabaseController();
        }
        return sInstance;
    }

    private NewsDatabaseController() {
    }

    public RealmResults<NewsItem> getGoogleNews(Realm realm) {
        return realm.where(NewsItem.class)
                .equalTo(NewsItem.Columns.NEWS_TYPE, NewsItem.NEWS_GOOGLE)
                .findAllSorted(NewsItem.Columns.DATE, Sort.DESCENDING);
    }

    public RealmResults<NewsItem> getBbcNews(Realm realm) {
        return realm.where(NewsItem.class)
                .equalTo(NewsItem.Columns.NEWS_TYPE, NewsItem.NEWS_BBC)
                .findAllSorted(NewsItem.Columns.DATE, Sort.DESCENDING);
    }

    public RealmResults<NewsItem> getFavorites(Realm realm) {
        return realm.where(NewsItem.class)
                .equalTo(NewsItem.Columns.IS_FAVORITE, true)
                .findAllSorted(NewsItem.Columns.DATE, Sort.DESCENDING);
    }

    public NewsItem getNewsByTitle(Realm realm, String title) {
        return realm.where(NewsItem.class)
                .equalTo(NewsItem.Columns.TITLE, title)
                .findFirst();
    }

    public void insertNewsWithTransaction(Realm realm,
                                          final JSONArray newsItemsJsonArrayFromNetwork,
                                          final @NewsItem.Type int newsType) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // We will insert each item now. We need to be careful about overwriting some values
                // so we will perform a query first to check for the item's existence. Reason being,
                // we may accidentally overwrite an item's is_favorite attribute, since that
                // attribute doesn't come from the network.

                for (int i = 0; i < newsItemsJsonArrayFromNetwork.length(); i++) {
                    NewsItem newsItemFromNetwork = getNewsItemFromJsonArray(i);
                    if (newsItemFromNetwork == null) {
                        continue;
                    }

                    NewsItem oldNewsItem = realm.where(NewsItem.class)
                            .equalTo(NewsItem.Columns.TITLE, newsItemFromNetwork.getTitle())
                            .findFirst();

                    if (oldNewsItem != null) {
                        // The item already exists. So, we need to be careful to not overwrite the
                        // "is_favorite" column

                        // Update the new item's "is_favorite" field with the old item's value
                        newsItemFromNetwork.setDate(oldNewsItem.getDate());
                        newsItemFromNetwork.setIsFavorite(oldNewsItem.isFavorite());
                        realm.insertOrUpdate(newsItemFromNetwork);
                    } else {
                        // The item doesn't exist yet. Let's create it.
                        realm.insert(newsItemFromNetwork);
                    }
                }
            }

            @Nullable
            private NewsItem getNewsItemFromJsonArray(int position) {
                try {
                    JSONObject jsonObject = newsItemsJsonArrayFromNetwork.getJSONObject(position);
                    NewsItem.JsonDeserializer deserializer = new NewsItem.JsonDeserializer(newsType);
                    return deserializer.getObjectFromJson(jsonObject);
                } catch (JSONException e) {
                    Log.e(TAG, "getNewsItemFromJsonArray: ", e);
                    return null;
                }
            }
        });
    }

    public void toggleFavoriteWithTransaction(Realm realm, final String newsItemTitle) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NewsItem newsItem = realm.where(NewsItem.class)
                        .equalTo(NewsItem.Columns.TITLE, newsItemTitle)
                        .findFirst();
                newsItem.setIsFavorite(!newsItem.isFavorite());
                realm.insertOrUpdate(newsItem);
            }
        });
    }

    /**
     * @param newsItems The {@link RealmResults} whose size should be found
     * @return The size of the realm results, capped at {@link #MAX_NEWS_FEED_SIZE}.
     */
    public static int getRealmResultsSize(RealmResults<NewsItem> newsItems) {
        return Math.min(newsItems.size(), MAX_NEWS_FEED_SIZE);
    }

}
