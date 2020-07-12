package com.movielist.movielist.core.api;

import com.movielist.movielist.models.Movie;
import com.movielist.movielist.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * //Sab 29-12-18 193152
 */

public interface OMDbApi {

    @GET("/")
    Call<SearchResponse> searchByTitle(@Query("s") String title);   // to feature.search.SearchPresenter

    @GET("/")   //Min 30-12-18 213319
//    @GET("/&")   //Sen 31-12-18 141256
//    @GET("/y=2015")   //Sen 31-12-18 135443   //Sen 31-12-18 140243 null reference
//    @GET("/y=2015")   //Sen 31-12-18 141131
//    @GET("/&y=2015")   //Sen 31-12-18 140243
//    Call<SearchResponse> searchByNewestYear(@Query("y") String year);   // if without its own GET, the error will be: java.lang.IllegalArgumentException: HTTP method annotation is required (e.g., @GET, @POST, etc.) for method OMDbApi.searchByNewestYear
//    Call<SearchResponse> searchByNewestYear(@Query("s=the&y") String year);
    Call<SearchResponse> searchByNewestYear(@Query("y") String year);

    @GET("/")
    Call<Movie> searchByOMDbId(@Query("i") String omdbId);  // to feature.details.DetailsPresenter

    //Sab 29-12-18 192825 open app directly load by year
//    @GET("/")
//    Call<YearResponseDefault> searchByNewestYear(@Query("y") String year);    // no result
}