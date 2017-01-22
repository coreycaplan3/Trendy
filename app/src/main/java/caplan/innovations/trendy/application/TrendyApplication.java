package caplan.innovations.trendy.application;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Corey on 1/19/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class:
 */
public class TrendyApplication extends Application {

    private static TrendyApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        Realm.init(this);
    }

    /**
     * @return The global {@link Application} instance for this application
     */
    public static TrendyApplication getInstance() {
        return sApplication;
    }

    /**
     * @return The application context. Should not be used for anything related to activity life
     * cycle events, since this context is bound to the life cycle of the application.
     */
    public static Context context() {
        return sApplication.getApplicationContext();
    }

}
