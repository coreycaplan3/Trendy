package caplan.innovations.trendy.model;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import caplan.innovations.trendy.utilities.JsonExtractor;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Corey on 1/20/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To represent a simple news item in our app.
 */
public class NewsItem extends RealmObject {

    public static final int NEWS_GOOGLE = 1;
    public static final int NEWS_BBC = 2;

    @IntDef({NEWS_GOOGLE, NEWS_BBC})
    public @interface Type {
    }

    @PrimaryKey
    private String mTitle;

    @Type
    private int mNewsType;

    private long mDate;

    @Nullable
    private String mAuthor;

    @Nullable
    private String mUrlToArticle;

    @Nullable
    private String mDescription;

    @Nullable
    private String mImageUrl;


    private boolean mIsFavorite;

    /**
     * Default empty constructor for use with Realm
     */
    public NewsItem() {
    }

    private NewsItem(String title, @Type int newsType, long date, @Nullable String author,
                     @Nullable String urlToArticle, @Nullable String description,
                     @Nullable String imageUrl, boolean isFavorite) {
        mTitle = title;
        mNewsType = newsType;
        mDate = date;
        mAuthor = author;
        mUrlToArticle = urlToArticle;
        mDescription = description;
        mImageUrl = imageUrl;
        mIsFavorite = isFavorite;
    }

    public String getTitle() {
        return mTitle;
    }

    @SuppressWarnings("unused")
    @Type
    public int getNewsType() {
        return mNewsType;
    }

    /**
     * @return The date, in millis since the UNIX Epoch, at which the item was originally
     * entered into the DB.
     */
    public long getDate() {
        return mDate;
    }

    /**
     * @param date The date, in millis since the UNIX Epoch, at which the item was originally
     *             entered into the DB.
     */
    public void setDate(long date) {
        mDate = date;
    }

    @Nullable
    public String getAuthor() {
        return mAuthor;
    }

    public String getUrlToArticle() {
        return mUrlToArticle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewsItem)) {
            return false;
        }
        NewsItem otherItem = (NewsItem) obj;

        return isEqual(mTitle, otherItem.mTitle)
                && mNewsType == otherItem.mNewsType
                && isEqual(mAuthor, otherItem.mAuthor)
                && isEqual(mUrlToArticle, otherItem.mUrlToArticle)
                && isEqual(mDescription, otherItem.mDescription)
                && isEqual(mImageUrl, otherItem.mImageUrl)
                && isEqual(mIsFavorite, otherItem.mIsFavorite);
    }

    @SuppressWarnings("RedundantIfStatement")
    private static boolean isEqual(@Nullable Object o1, @Nullable Object o2) {
        if (o1 != null && !o1.equals(o2)) {
            return false;
        } else if (o1 == null && o2 != null) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unused")
    public static class Columns {

        public static final String TITLE = "mTitle";
        public static final String NEWS_TYPE = "mNewsType";
        public static final String DATE = "mDate";
        public static final String AUTHOR = "mAuthor";
        public static final String URL = "mUrlToArticle";
        public static final String DESCRIPTION = "mDescription";
        public static final String IMAGE_URL = "mImageUrl";
        public static final String IS_FAVORITE = "mIsFavorite";

    }

    public static class JsonDeserializer extends AbstractJsonDeserializer<NewsItem> {

        private static final String TAG = JsonDeserializer.class.getSimpleName();

        @Type
        private int mNewsType;

        public JsonDeserializer(@Type int newsType) {
            mNewsType = newsType;
        }

        @Override
        public NewsItem getObjectFromJson(JSONObject jsonObject) {
            JsonExtractor extractor = new JsonExtractor(jsonObject);
            String title = extractor.getString("title");
            long date = extractor.getLong("my_date");
            String author = extractor.getString("author");
            String description = extractor.getString("description");
            String url = extractor.getString("url");
            String urlToImage = extractor.getString("urlToImage");

            if (title == null || date == -1) {
                Log.e(TAG, "getObjectFromJson: Invalid JSON object");
                return null;
            }
            // Check the API didn't return an empty string
            if (isEmpty(author)) {
                author = null;
            }
            if (isEmpty(description)) {
                description = null;
            }
            if (isEmpty(url)) {
                url = null;
            }
            if (isEmpty(urlToImage)) {
                urlToImage = null;
            }
            return new NewsItem(title, mNewsType, date, author, url, description, urlToImage, false);
        }

        private static boolean isEmpty(String string) {
            return string == null
                    || string.trim().length() == 0
                    || string.trim().equalsIgnoreCase("null");
        }

    }

}
