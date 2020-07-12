package com.movielist.movielist.feature.search.viewholder;

/**
 * //Jum 28-12-18 192337
 */

public interface CardMovieViewHolderContract {
    void setTitle(String title);
    void setYear(String year);
    void setMovieThumbnail(String poster);
    void resetViews();
}
