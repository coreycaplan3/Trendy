package caplan.innovations.trendy.network;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

import caplan.innovations.trendy.application.TrendyApplication;

/**
 * Created by Corey on 1/21/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To wrap the {@link RequestQueue} in a singleton.
 */
public class TrendyRequestQueue {

    /**
     * 10 MB in size
     */
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

    private static TrendyRequestQueue sInstance;

    private static TrendyRequestQueue getInstance() {
<<<<<<< HEAD
        if (sInstance != null) {
=======
        if (sInstance == null) {
>>>>>>> 3303384bb66fbc61ffdf2326b4ad6da6f29840d2
            sInstance = new TrendyRequestQueue();
        }
        return sInstance;
    }

    private RequestQueue mRequestQueue;

    private TrendyRequestQueue() {
        File cacheDirectory = TrendyApplication.context().getCacheDir();
        DiskBasedCache cache = new DiskBasedCache(cacheDirectory, DISK_CACHE_SIZE);

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    /**
     * Adds the given request to the queue to be enacted.
     *
     * @param request The {@link Request} that should be performed.
     */
<<<<<<< HEAD
=======
    @SuppressWarnings("WeakerAccess")
>>>>>>> 3303384bb66fbc61ffdf2326b4ad6da6f29840d2
    public static void addToRequestQueue(Request request) {
        getInstance().mRequestQueue.add(request);
    }

    /**
     * Cancels all Volley operations currently in the request queue.
     */
    public static void cancelAll() {
        // A default instance in which we return true for all requests, meaning all get cancelled
        RequestQueue.RequestFilter filter = new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        };

        getInstance().mRequestQueue.cancelAll(filter);
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 3303384bb66fbc61ffdf2326b4ad6da6f29840d2
