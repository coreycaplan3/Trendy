package caplan.innovations.trendy.receivers;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import caplan.innovations.trendy.model.NewsItem;
import caplan.innovations.trendy.receivers.NewsBroadcastReceiver;
import caplan.innovations.trendy.services.NewsIntentService;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: An interface used to transfer the received items from a broadcast to the
 * {@link NewsBroadcastReceiver}.
 */
public interface OnNewsItemsReceivedFromBroadcastListener {

    /**
     * Called when a broadcast is received from the {@link NewsIntentService}.
     *
     * @param isSuccessful True if the network operation was successful, false otherwise
     */
    void onNewsItemsReceivedFromBroadcast(boolean isSuccessful);

}
