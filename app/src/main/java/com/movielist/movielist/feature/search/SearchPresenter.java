package com.movielist.movielist.feature.search;

import com.movielist.movielist.core.api.OMDbApiService;
import com.movielist.movielist.models.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * //Jum 28-12-18 193022
 */

public class SearchPresenter implements SearchContract.Presenter {  //, SearchContract.YearPresenter {

    private SearchContract.View view;
    private OMDbApiService apiService;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.hideKeyboard();
    }

    @Override
    public void stop() {
        //Nada
    }

    @Override
    public void loadApi(OMDbApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void onSearchedMovie(String title) {
//        view.cleanSearch();   //Sen 31-12-18 154629 commented
        view.hideKeyboard();
        view.showLoading();

        Call<SearchResponse> moviesCallback = apiService.getOMDbApi().searchByTitle(title);

        moviesCallback.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(
                    Call<SearchResponse> call,
                    Response<SearchResponse> response
            ) {
                SearchResponse movies = response.body();

                if (movies.getError() != null) {
                    view.showError();
                } else {
                    view.showMovies(movies.getResults());
                }
            }

            @Override
            public void onFailure(
                    Call<SearchResponse> call,
                    Throwable t) {
                view.showError();
            }
        });
    }

    @Override   //Sab 29-12-18 220716
    public void onYearSearch(String yearMovie) {
        view.cleanSearch();   //Min 30-12-18 222029 commented   //Min 30-12-18 224041 dont comment, it will be no result even if searching from edit text
//        view.hideKeyboard();
        view.showLoading();

//        Call<YearResponseDefault> moviesByYear = apiService.getOMDbApi().searchByNewestYear(yearMovie);
        Call<SearchResponse> moviesByYear = apiService.getOMDbApi().searchByNewestYear(yearMovie); //Min 30-12-18 212351

        moviesByYear.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(
                    Call<SearchResponse> call,
                    Response<SearchResponse> response
            ) {
                SearchResponse movies = response.body();

                if (movies.getError() != null) {
                    view.showError();
                } else {
                    view.showMovies(movies.getResults());
                }
            }

            @Override
            public void onFailure(
                    Call<SearchResponse> call,
                    Throwable t) {
                view.showError();
            }
        });
    }
}
