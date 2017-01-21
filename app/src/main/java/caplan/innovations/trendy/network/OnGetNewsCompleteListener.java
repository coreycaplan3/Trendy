package caplan.innovations.trendy.network;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import caplan.innovations.trendy.model.NewsItem;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: A listener used to pass the results back of a Volley network call to the
 * caller on the UI thread.
 */
public interface OnGetNewsCompleteListener {

    /**
     * Called when the getting of the news operation is complete
     *
     * @param newsItems The news items that the network retrieved or null if an error occurred.
     *                  For simplicity's sake, we are not going to distinguish between the
     *                  different types of errors. Some errors include loss of network connection,
     *                  or invalid JSON being returned from the server.
     */
    void onGetNewsComplete(@Nullable ArrayList<NewsItem> newsItems);
}
