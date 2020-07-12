package com.movielist.movielist.core.base;

import android.content.Context;
import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

import com.movielist.movielist.core.utils.ContentState;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static com.movielist.movielist.core.base.BaseAdapter.ViewTypes.BLANK;
import static com.movielist.movielist.core.base.BaseAdapter.ViewTypes.EMPTY_VIEW;
import static com.movielist.movielist.core.base.BaseAdapter.ViewTypes.ERROR_VIEW;
import static com.movielist.movielist.core.base.BaseAdapter.ViewTypes.ITEM_VIEW;


/**
 * //Jum 28-12-18 185533
 */

public abstract class BaseAdapter<T> extends BaseRecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final int EMPTY_STATE_SIZE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BLANK, EMPTY_VIEW, ITEM_VIEW, ERROR_VIEW})
    public @interface ViewTypes {
        int BLANK = 0;
        int EMPTY_VIEW = 1;
        int ITEM_VIEW = 2;
        int ERROR_VIEW = 4;
    }

    protected ContentState contentState;

    protected Context context;

    private List<T> dataSource;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public T getCurrentItem(int position) {
        if (getItemViewType(position) == ViewTypes.ITEM_VIEW) {
            return getDataSource().get(position);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (contentState) {
            case HAS_RESULTS:
                return ViewTypes.ITEM_VIEW;
            case ERROR:
                return ViewTypes.ERROR_VIEW;
            case EMPTY:
                return ViewTypes.EMPTY_VIEW;
            default:
                return ViewTypes.BLANK;
        }
    }

    /**
     * Return a LayoutInflater instance from the assigned activity context instance
     * @return LayoutInflater instance
     */
    protected final LayoutInflater getLayoutInflater() {
        if (context != null) {
            return LayoutInflater.from(context);
        } else {
            throw new IllegalStateException("Cannot inflate views without an activity context! Please set this adapter's activity context to allow it to inflate views.");
        }
    }

    public void setContentState(ContentState state) {
        contentState = state;
    }

    public final ContentState getContentState() {
        return contentState;
    }

    public final List<T> getDataSource() {
        return dataSource;
    }

    public final void setDataSource(List<T> dataSource) {
        this.dataSource = dataSource;

        if (dataSource != null) {
            if (dataSource.size() == 0) {
                setContentState(ContentState.EMPTY);
            } else {
                setContentState(ContentState.HAS_RESULTS);
            }
        } else {
            setContentState(ContentState.BLANK);
        }
    }
}