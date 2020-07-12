package com.movielist.movielist.core.base;

import com.movielist.movielist.core.api.OMDbApiService;

/**
 * //Jum 28-12-18 185533
 */

public interface BasePresenter {
    void start();

    void stop();

    void loadApi(OMDbApiService service);
}
