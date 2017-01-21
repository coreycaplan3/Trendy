package caplan.innovations.trendy.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import caplan.innovations.trendy.R;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;

/**
 * A base class of {@link AppCompatActivity} used to do a standardized setup that should be
 * universal amongst all activities.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected ProgressDialog progressDialog;
    private String mProgressMessage;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    private static final String KEY_PROGRESS_SHOWING = "PROGRESS_SHOWING";
    private static final String KEY_PROGRESS_TEXT = "PROGRESS_TEXT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        progressDialog = new ProgressDialog(this) {
            @Override
            public void setMessage(CharSequence message) {
                super.setMessage(message);
                // Cache the value of message since the progress dialog doesn't have a getter for
                // the message field.
                mProgressMessage = (String) message;
            }
        };
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if (savedInstanceState != null) {
            progressDialog.setMessage(savedInstanceState.getString(KEY_PROGRESS_TEXT));
            if (savedInstanceState.getBoolean(KEY_PROGRESS_SHOWING)) {
                progressDialog.show();
            }
        }

        // Setup the views for the activity
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isProgressShowing) {
            progressDialog.show();
        }
    }

    /**
     * @return The layout resource ({@link LayoutRes}) that points to the content view for this
     * activity. This layout resource is used in {@link #onCreate(Bundle)} to set the activity's
     * content view. This method is imperative for the sake of automating activity setup.
     */
    @LayoutRes
    abstract int getContentView();

    /**
     * Sets up the back button to be shown in this activity
     */
    void setupBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        } else {
            throw new NullPointerException("SupportActionBar is null!");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Default to going to the home screen
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return false;
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    /**
     * @param title The title that should be set on the {@link ActionBar}.
     */
    @SuppressWarnings({"unused", "ConstantConditions"})
    protected final void updateTitle(@StringRes int title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Disables scroll flags on the {@link AppBarLayout}.
     */
    @SuppressWarnings("unused")
    protected final void disableScrollFlags() {
        if (mAppBarLayout != null) {
            for (int i = 0; i < mAppBarLayout.getChildCount(); i++) {
                View view = mAppBarLayout.getChildAt(i);
                ((AppBarLayout.LayoutParams) view.getLayoutParams()).setScrollFlags(0);
            }
        } else {
            Log.e(TAG, "disableScrollFlags: AppBarLayout was null!");
        }
    }

    /**
     * @param scrollFlags The scroll flags that should be enabled for all of the
     *                    {@link AppBarLayout}'s children. The scroll flags can be anything other
     *                    than {@link AppBarLayout.LayoutParams#SCROLL_FLAG_SCROLL} since that is
     *                    added by default.
     */
    @SuppressWarnings("unused")
    protected final void enableScrollFlags(int scrollFlags) {
        if (mAppBarLayout != null) {
            for (int i = 0; i < mAppBarLayout.getChildCount(); i++) {
                View view = mAppBarLayout.getChildAt(i);
                ((AppBarLayout.LayoutParams) view.getLayoutParams())
                        .setScrollFlags(SCROLL_FLAG_SCROLL | scrollFlags);
            }
        } else {
            Log.e(TAG, "enableScrollFlags: AppBarLayout was null!");
        }
    }

    /**
     * @return The root view of this activity after doing a {@code findViewById} on
     * {@link R.id#activity_container}
     */
    @SuppressWarnings("unused")
    public final View getRootView() {
        return ButterKnife.findById(this, R.id.activity_container);
    }

    private boolean isProgressShowing = false;

    @Override
    protected void onStop() {
        super.onStop();

        if (progressDialog.isShowing()) {
            isProgressShowing = true;
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_PROGRESS_SHOWING, isProgressShowing);
        outState.putString(KEY_PROGRESS_TEXT, mProgressMessage);


    }

}