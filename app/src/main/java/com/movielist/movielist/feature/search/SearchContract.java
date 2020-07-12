package com.movielist.movielist.feature.search;

import com.movielist.movielist.core.base.BasePresenter;
import com.movielist.movielist.core.base.BaseView;
import com.movielist.movielist.models.Card;

import java.util.List;

/**
 * //Jum 28-12-18 192916
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Card> results);

        void cleanSearch();

        void hideKeyboard();

        void showError();

        void showLoading();

    }

    interface Presenter extends BasePresenter {
        void onSearchedMovie(String movie);
        void onYearSearch(String yearMovie);    //Sab 29-12-18 220441 because in feature.search.SearchActivity can not use two contracts
    }

//    interface YearPresenter extends BasePresenter { //Min 30-12-18 224344 try make its own year presenter
//        void onYearSearch(String yearMovie);
//    }

}