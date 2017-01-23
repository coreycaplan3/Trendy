package caplan.innovations.trendy.application;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * The global application class. Used mainly get static access to the application {@link Context}
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
