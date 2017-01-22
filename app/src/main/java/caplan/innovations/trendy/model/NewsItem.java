package caplan.innovations.trendy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import caplan.innovations.trendy.R;
import caplan.innovations.trendy.application.TrendyApplication;

/**
 * Created by Corey on 1/20/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To represent a simple news item in our app.
 */
public class NewsItem implements Parcelable {

    private final String mTitle;
    @Nullable
    private final String mAuthor;
    private final String mUrlToArticle;
    private final String mDescription;
    private final String mImageUrl;

    public static NewsItem getDummy() {
        String title = "Android Course 101";
        String author = "Corey";
        String urlToArticle = "https://google.com";
        String description = TrendyApplication.context().getString(R.string.default_news_description);
        return new NewsItem(title, author, urlToArticle, description, null);
    }

    public NewsItem(String title, @Nullable String author, String urlToArticle, String description,
                    String imageUrl) {
        mTitle = title;
        mAuthor = author;
        mUrlToArticle = urlToArticle;
        mDescription = description;
        mImageUrl = imageUrl;
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
        dest.writeString(mUrlToArticle);
        dest.writeString(mDescription);
        dest.writeString(mImageUrl);
    }

    private NewsItem(Parcel in) {
        mTitle = in.readString();
        if (in.readByte() == 0x01) {
            mAuthor = in.readString();
        } else {
            mAuthor = null;
        }

        mUrlToArticle = in.readString();
        mDescription = in.readString();
        mImageUrl = in.readString();
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

}
