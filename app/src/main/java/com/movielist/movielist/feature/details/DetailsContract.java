package com.movielist.movielist.feature.details;

import com.movielist.movielist.core.base.BasePresenter;
import com.movielist.movielist.core.base.BaseView;

/**
 * //Jum 28-12-18 190602
 */

public interface DetailsContract {

    interface View extends BaseView<Presenter> {

        void setPoster(String poster);

        void setTitle(String title);

        void setThumbnail(String poster);

        void setPlot(String plot);

        void setYearAndRuntime(String tahun, String waktuJalan);

        void setActors(String actors);

        void setDirector(String director);

        void setProduction(String production);

        void setWriters(String writers);

        void setIMDbRating(String imdbRating);

        void showError();

        void showLoading();

        void dismissLoading();

        }

    interface Presenter extends BasePresenter {
        void onSearchMovie(String movie);
    }
}