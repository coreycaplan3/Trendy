package caplan.innovations.trendy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;
import caplan.innovations.trendy.utilities.JsonExtractor;

/**
 * Created by Corey on 1/20/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To represent a simple news item in our app.
 */
public class NewsItem implements Parcelable {

    @IntDef({NEWS_GOOGLE, NEWS_BBC})
    public @interface NewsType {
    }

    public static final int NEWS_GOOGLE = 1;
    public static final int NEWS_BBC = 2;

    private final String mTitle;
    @Nullable
    private final String mAuthor;
    @Nullable
    private final String mUrlToArticle;
    @Nullable
    private final String mDescription;
    @Nullable
    private final String mImageUrl;
    private boolean mIsFavorite;

    public static NewsItem getDummy() {
        String title = "Android Course 101";
        String author = "Corey";
        String urlToArticle = "https://google.com";
        String description = TrendyApplication.context().getString(R.string.default_news_description);
        return new NewsItem(title, author, urlToArticle, description, null, true);
    }

    private NewsItem(String title, @Nullable String author, @Nullable String urlToArticle,
                    @Nullable String description, @Nullable String imageUrl, boolean isFavorite) {
        mTitle = title;
        mAuthor = author;
        mUrlToArticle = urlToArticle;
        mDescription = description;
        mImageUrl = imageUrl;
        mIsFavorite = isFavorite;
    }

    public String getTitle() {
        return mTitle;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);

        dest.writeByte((byte) (mAuthor != null ? 0x01 : 0x00));
        if (mAuthor != null) {
            dest.writeString(mAuthor);
        }

        dest.writeByte((byte) (mUrlToArticle != null ? 0x01 : 0x00));
        if (mUrlToArticle != null) {
            dest.writeString(mUrlToArticle);
        }

        dest.writeByte((byte) (mDescription != null ? 0x01 : 0x00));
        if (mDescription != null) {
            dest.writeString(mDescription);
        }

        dest.writeByte((byte) (mImageUrl != null ? 0x01 : 0x00));
        if (mImageUrl != null) {
            dest.writeString(mImageUrl);
        }

        dest.writeByte((byte) (mIsFavorite ? 0x01 : 0x00));
    }

    private NewsItem(Parcel in) {
        mTitle = in.readString();

        if (in.readByte() == 0x01) {
            mAuthor = in.readString();
        } else {
            mAuthor = null;
        }

        if(in.readByte() == 0x01) {
            mUrlToArticle = in.readString();
        } else {
            mUrlToArticle = null;
        }

        if(in.readByte() == 0x01) {
            mDescription = in.readString();
        } else {
            mDescription = null;
        }

        if(in.readByte() == 0x01) {
            mImageUrl = in.readString();
        } else {
            mImageUrl = null;
        }

        mIsFavorite = in.readByte() == 0x01;
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public static class JsonDeserializer extends AbstractJsonDeserializer<NewsItem> {

        public JsonDeserializer() {
        }

        @Override
        public NewsItem getObjectFromJson(JSONObject jsonObject) {
            JsonExtractor extractor = new JsonExtractor(jsonObject);
            String title = extractor.getString("title");
            String author = extractor.getString("author");
            String description = extractor.getString("description");
            String url = extractor.getString("url");
            String urlToImage = extractor.getString("urlToImage");

            if (title == null) {
                return null;
            }
            // Check the API didn't return an empty string
            if (isEmpty(author)) {
                author = null;
            }
            if(isEmpty(description)) {
                description = null;
            }
            if(isEmpty(url)) {
                url = null;
            }
            if(isEmpty(urlToImage)) {
                urlToImage = null;
            }
            return new NewsItem(title, author, url, description, urlToImage, false);
        }

        private static boolean isEmpty(String string) {
            return string == null
                    || string.trim().length() == 0
                    || string.trim().equalsIgnoreCase("null");
        }

    }

}
