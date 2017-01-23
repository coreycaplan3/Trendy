package caplan.innovations.trendy.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import caplan.innovations.trendy.application.TrendyApplication;

/**
 * To easily check different connection types such as GPS settings and  regular
 * device connectivity.
 */
@SuppressWarnings("unused")
public final class ConnectivityUtility {

    private ConnectivityUtility() {
    }

    /**
     * Checks if the given device is connected to any form of wireless network.
     *
     * @return True if it's connected to a network or false if it is not.
     */
    public static boolean hasConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) TrendyApplication.context()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
