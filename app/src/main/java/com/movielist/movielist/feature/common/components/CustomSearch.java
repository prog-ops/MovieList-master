package com.movielist.movielist.feature.common.components;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.movielist.movielist.R;
import com.movielist.movielist.core.base.BaseLayout;

/**
 * //Jum 28-12-18 190200
 */

public class CustomSearch extends BaseLayout {

    private SwitchCompat switchCompat;  //Sab 29-12-18 215135 before: in YearDefaultSearch
    private EditText inputEditText;
    private CustomSearchListener listener;

    public CustomSearch(Context context) {
        super(context);
    }
    public CustomSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)    //Sab 29-12-18 171853 requires
    public CustomSearch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void cleanSearch() {
        inputEditText.setText("");
    }

    @Override
    public void init(Context context) {
        View view = View.inflate(
                context,
                R.layout.view_custom_search, // view_custom_search is search edit text
                this);
//        View viewSwitch = View.inflate(
//                context,
//                R.layout.activity_search,
//                this);  //Sab 29-12-18 215524

        inputEditText = view.findViewById(R.id.et_input);
        switchCompat = view.findViewById(R.id.switch_compat); //Sab 29-12-18 215538

        inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (listener != null) {
                        // try show results per letter here
                        listener.onSearch(inputEditText.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (listener != null) {
//                        listener.onYearSearch("2018");  // no results
//                        listener.onYearSearch("2015");  //Min 30-12-18 194952
//                        listener.onYearSearch("2015&s=the");    //Min 30-12-18 215349
                        listener.onYearSearch(inputEditText.getText().toString());  //Sen 31-12-18 135741
//                        listener.onYearSearch(inputEditText.getText().toString() + "y=2015");  //Sen 31-12-18 145254  //Sen 31-12-18 145855 works but all years changed to 2015
                    }
                } else {    //Sen 31-12-18 150248
                    if (listener != null) {
                        listener.onSearch(inputEditText.getText().toString());
                    }
                }
            }
        });
    }

    public interface CustomSearchListener {
        void onSearch(String movie);    // title keyword of movie
        void onYearSearch(String year); //Sab 29-12-18 215755 try void here
    }

    public void setListener(CustomSearchListener listener) {
        this.listener = listener;
    }
}