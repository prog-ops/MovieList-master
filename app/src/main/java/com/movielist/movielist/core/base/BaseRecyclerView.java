package com.movielist.movielist.core.base;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.movielist.movielist.core.utils.ContentState;

/**
 * //Jum 28-12-18 185834
 */

public class BaseRecyclerView extends RecyclerView {
    private View loadingView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (getAdapter() instanceof BaseAdapter) {
                BaseAdapter adapter = (BaseAdapter) getAdapter();
                if (loadingView == null) {
                    throw new ExceptionInInitializerError("Missing calling initLoadingStateView method");
                }

                if (adapter != null) {
                    switch (adapter.getContentState()) {
                        case BLANK:
                        case EMPTY:
                        case ERROR:
                        case HAS_RESULTS:
                            loadingView.setVisibility(View.GONE);
                            BaseRecyclerView.this.setVisibility(View.VISIBLE);
                            break;
                        case LOADING:
                            loadingView.setVisibility(View.VISIBLE);
                            BaseRecyclerView.this.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initLoadingStateView(View loadingView) {
        this.loadingView = loadingView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof BaseAdapter) {
            BaseAdapter castedAdapter = (BaseAdapter) adapter;
            castedAdapter.setContentState(ContentState.BLANK);
        } else {
            throw new IllegalStateException("You will need to use an instance of BaseAdapter");
        }
        adapter.registerAdapterDataObserver(emptyObserver);

        emptyObserver.onChanged();
    }

}