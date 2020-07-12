package com.movielist.movielist.feature.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.movielist.movielist.R;
import com.movielist.movielist.core.utils.ContentState;
import com.movielist.movielist.feature.common.components.CustomSearch;
import com.movielist.movielist.feature.details.DetailsActivity;
import com.movielist.movielist.models.Card;

import com.movielist.movielist.core.api.OMDbApiService;
import com.movielist.movielist.core.base.BaseActivity;
import com.movielist.movielist.core.base.BaseRecyclerView;

import java.util.List;

/**
 * //Jum 28-12-18 192704
 */
public class SearchActivity extends BaseActivity implements
        SearchContract.View,
//        YearDefaultContract.View,   //Sab 29-12-18 213227

        CustomSearch.CustomSearchListener,
//        YearDefaultSearch.YearDefaultSearchListener,    //Sab 29-12-18 212933

        SearchAdapter.SearchAdapterListener {
    private SearchContract.Presenter presenter;
//    private SearchContract.YearPresenter yearPresenter;
//    private YearDefaultContract.Presenter yearPresenter;

//    protected SwitchCompat switchCompat;    //Sab 29-12-18 175405 //Sen 31-12-18 154341 commented

    protected CustomSearch customSearch;
//    protected YearDefaultSearch yearDefaultSearch;  //Sab 29-12-18 212628
    protected BaseRecyclerView recyclerView;
    protected SearchAdapter adapter;
    protected View loading;
    protected RecyclerView.LayoutManager layoutManager;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SearchPresenter(this);
        presenter.loadApi(OMDbApiService.getInstance(this));
//        yearPresenter = new YearDefaultPresenter(this); //Sab 29-12-18 213329
//        yearPresenter.loadApi(OMDbApiService.getInstance(this)); //Sab 29-12-18 213329

        setContentView(R.layout.activity_search);

//        switchCompat = findViewById(R.id.switch_compat);    //Sab 29-12-18 185758
        customSearch = findViewById(R.id.cs_search);

        recyclerView = findViewById(R.id.rv_card_movies);
        loading = findViewById(R.id.loading);

        layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );

        adapter = new SearchAdapter(getBaseContext(), this);
        recyclerView.initLoadingStateView(loading);
        recyclerView.setDrawingCacheEnabled(true);  //Sen 31-12-18 154509
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);   //Sen 31-12-18 154512
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        customSearch.setListener(this);

        presenter.start();

//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    yearPresenter.start();
//                }
//            }
//        });  //Sab 29-12-18 205341 OnCheckedChangeListener

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public void showMovies(List<Card> results) {
        adapter.setDataSource(results);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void cleanSearch() {
        customSearch.cleanSearch();
    }

    @Override
    public void showError() {
        adapter.setContentState(ContentState.ERROR);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
//    @Override   //Sab 29-12-18 214256
//    public void setPresenter(YearDefaultContract.Presenter yearPresenter) {
//        this.yearPresenter = yearPresenter;
//    }

    @Override
    public void onSearch(String movie) {
        hideKeyboard();
        presenter.onSearchedMovie(movie);
    }
    @Override
    public void onYearSearch(String yearMovie) {
//        hideKeyboard();   // not using this, so user still can use the edit text (CustomSearch)
        presenter.onYearSearch(yearMovie);    // not sure yet
//        yearPresenter.onYearSearch(yearMovie);    //Min 30-12-18 225144 null reference in CustomSearch listener.onYearSearch("2015")
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showLoading() {
        adapter.setContentState(ContentState.LOADING);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClicked(String imdbId) {
        Intent intent = DetailsActivity.newIntent(this, imdbId);
        startActivity(intent);
    }
}