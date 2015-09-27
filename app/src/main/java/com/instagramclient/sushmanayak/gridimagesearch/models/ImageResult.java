package com.instagramclient.sushmanayak.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Parcelable {
    private String fullURL;
    private String thumbURL;
    private String title;
    private String pageURL;
    private String content;
    private int height;
    private int width;

    public ImageResult(JSONObject imageJSON) {
        try {
            this.fullURL = imageJSON.getString("url");
            this.thumbURL = imageJSON.getString("tbUrl");
            this.title = imageJSON.getString("title");
            this.pageURL = imageJSON.getString("originalContextUrl");
            this.content = imageJSON.getString("contentNoFormatting");
            this.width = imageJSON.getInt("width");
            this.height = imageJSON.getInt("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFullURL() {
        return fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {

        ArrayList<ImageResult> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullURL);
        dest.writeString(this.thumbURL);
        dest.writeString(this.title);
        dest.writeString(this.pageURL);
        dest.writeString(this.content);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
    }

    private ImageResult(Parcel in) {
        this.fullURL = in.readString();
        this.thumbURL = in.readString();
        this.title = in.readString();
        this.pageURL = in.readString();
        this.content = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    public static final Creator<ImageResult> CREATOR = new Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }
        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
