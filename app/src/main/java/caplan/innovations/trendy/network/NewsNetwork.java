package caplan.innovations.trendy.network;

import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.model.NewsItem.JsonDeserializer;
import caplan.innovations.trendy.utilities.JsonExtractor;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class:
 */
public class NewsNetwork {

    private static final String TAG = NewsNetwork.class.getSimpleName();

    private static final String BASE_URL = "https://newsapi.org/v1/articles";
    private static final String PARAM_API_KEY = "apiKey";
    private static final String PARAM_SOURCE = "source";

    /**
     * In the JSON object response from the api --> maps to an array of JSON objects
     */
    private static final String RESPONSE_ARTICLES = "articles";

    private static final String SOURCE_GOOGLE = "google-news";
    private static final String SOURCE_BBC = "bbc-news";

    /**
     * Gets the news from BBC on a separate thread and reports the results back.
     *
     * @param listener The {@link OnGetNewsCompleteListener} used to transfer the results back to
     *                 the UI thread after completing
     */
    public static void getBbcNewsAsync(OnGetNewsCompleteListener listener) {
        getNews(listener, NewsItem.NEWS_BBC);
    }

    /**
     * Gets the news from Google on a separate thread and reports the results back.
     *
     * @param listener The {@link OnGetNewsCompleteListener} used to transfer the results back to
     *                 the UI thread after completing
     */
    public static void getGoogleNewsAsync(OnGetNewsCompleteListener listener) {
        getNews(listener, NewsItem.NEWS_GOOGLE);
    }

    private static void getNews(final OnGetNewsCompleteListener listener, @NewsItem.NewsType int newsType) {
        String url = buildUrl(newsType);
        Listener<JSONObject> successListener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: Received response: " + response.toString());

                JSONArray jsonArray = new JsonExtractor(response)
                        .getJsonArray(RESPONSE_ARTICLES);
                if (jsonArray == null) {
                    listener.onGetNewsComplete(null);
                }

                JsonDeserializer deserializer = new JsonDeserializer();
                ArrayList<NewsItem> items =
                        JsonDeserializer.getArrayFromJson(jsonArray, deserializer);

                listener.onGetNewsComplete(items);
            }
        };

        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: Received response: ", error);
                listener.onGetNewsComplete(null);
            }
        };

        Log.d(TAG, "getNews: Sending a request to: " + url);
        JsonObjectRequest request = new JsonObjectRequest(url, null, successListener, errorListener);
        TrendyRequestQueue.addToRequestQueue(request);
    }

    private static String buildUrl(@NewsItem.NewsType int newsType) {
        String apiKey = TrendyApplication.context().getString(R.string.news_api_key);
        String url = String.format("%s?%s=%s", BASE_URL, PARAM_API_KEY, apiKey);
        switch (newsType) {
            case NewsItem.NEWS_GOOGLE:
                url = String.format("%s&%s=%s", url, PARAM_SOURCE, SOURCE_GOOGLE);
                break;
            case NewsItem.NEWS_BBC:
                url = String.format("%s&%s=%s", url, PARAM_SOURCE, SOURCE_BBC);
                break;
            default:
                throw new IllegalArgumentException("Invalid newsType, found: " + newsType);
        }
        return url;
    }

}
