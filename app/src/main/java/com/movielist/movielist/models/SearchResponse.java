package com.movielist.movielist.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * //Jum 28-12-18 193103
 */

public class SearchResponse implements Parcelable {
    @SerializedName("Search")
    private List<Card> results;
    @SerializedName("Error")
    private String error;

    protected SearchResponse(Parcel in) {
        results = in.createTypedArrayList(Card.CREATOR);
        error = in.readString();
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel in) {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
        dest.writeString(error);
    }

    public List<Card> getResults() {
        return results;
    }

    public void setResults(List<Card> results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}