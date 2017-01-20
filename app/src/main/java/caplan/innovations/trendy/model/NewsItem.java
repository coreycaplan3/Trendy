package caplan.innovations.trendy.model;

import android.support.annotation.Nullable;

/**
 * Created by Corey on 1/20/2017.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To represent a simple news item in our app.
 */
public class NewsItem {

    private final String mTitle;
    @Nullable
    private final String mAuthor;
    private final String mUrlToArticle;
    private final String mDescription;
    private final String mImageUrl;

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
}
