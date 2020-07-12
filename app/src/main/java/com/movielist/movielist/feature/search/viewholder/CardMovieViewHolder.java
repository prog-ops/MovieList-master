package com.movielist.movielist.feature.search.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movielist.movielist.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * //Jum 28-12-18 190721
 */

public class CardMovieViewHolder extends RecyclerView.ViewHolder implements CardMovieViewHolderContract {

    protected LinearLayout cardContainerLinearLayout;
    private ImageView thumbnailImageView;
    private TextView movieTitleTextView;
    private TextView yearTextView;

    private Context context;

    public LinearLayout getCardContainerLinearLayout() {
        return cardContainerLinearLayout;
    }

    public CardMovieViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        cardContainerLinearLayout = itemView.findViewById(R.id.ll_card_container);
        thumbnailImageView = itemView.findViewById(R.id.iv_thumbnail);
        movieTitleTextView = itemView.findViewById(R.id.tv_movie_title);
        yearTextView = itemView.findViewById(R.id.tv_year);
    }

    @Override
    public void setTitle(String title) {
        movieTitleTextView.setText(title);
    }

    @Override
    public void setYear(String year) {
        yearTextView.setText(year);
    }

    @Override
    public void setMovieThumbnail(final String poster) {
        // give setIndicatorsEnabled true for check from where the image is loaded

//        Picasso.with(context).load(poster).fit().into(thumbnailImageView);

        /*
        new Picasso.Builder(context)    //Min 30-12-18 172658 with caching
                .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                .build()
                .load(poster)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .into(thumbnailImageView);
        */

        /*
        //Min 30-12-18 180057
        Picasso.with(context).load(poster).networkPolicy(NetworkPolicy.OFFLINE).fit().into(
                thumbnailImageView,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i(poster.toString(), "onSuccess");
                    }

                    @Override
                    public void onError() {
                        // try again online if cache failed
                        Picasso.with(context).load(poster).error(R.drawable.ic_search).into(
                                thumbnailImageView,
                                new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i(poster.toString(), "onSuccess: online because cache failed");
                                    }

                                    @Override
                                    public void onError() {
                                        Log.v(poster.toString(), "onError: couldn't fetch image");
                                    }
                                }
                        );
                    }
                }
        );
        */

        /*
        PicassoCache.getPicassoInstance(context).load(poster).fit().into(thumbnailImageView);   //Min 30-12-18 182500 ga bisa
        */

        /*
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().header("Cache-Control", "max-age=" + (60 * 60 * 24 * 365)).build();
                    }
                }
        );
//        okHttpClient.setCache(new Cache(context.getCacheDir(), Integer.MAX_VALUE));   // asli
//        okHttpClient.cache().maxSize(); // try
        */

        /*
        //Min 30-12-18 192135 okHttpClient.setCache is in okhttp not okhttp3
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(new Cache(context.getCacheDir(), 100 * 1024 * 1024));
        OkHttpDownloader downloader = new OkHttpDownloaderDiskCacheFirst(okHttpClient);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);
        Picasso built = builder.build();
        Picasso.setSingletonInstance(built);
        */

//        makeImageRequest(thumbnailImageView, 0, poster);  // target must not be null
        makeImageRequest(thumbnailImageView, R.id.iv_thumbnail, poster);
    }

    //Min 30-12-18 193547
    public void makeImageRequest(final View parentView, final int id, final String imageUrl) {
        final int defaultImageResId = R.drawable.ic_launcher_foreground;
        final ImageView imageView = parentView.findViewById(id);
        Picasso.get()
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso","fetch image success in first time.");
                    }

                    @Override
                    public void onError(Exception e) {
                        //Try again online if cache failed
                        Log.v("Picasso","Could not fetch image in first time...");
                        Picasso.get()
                                .load(imageUrl)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .error(defaultImageResId)
                                .into(imageView, new Callback() {

                                    @Override
                                    public void onSuccess() {
                                        Log.v("Picasso","fetch image success in try again.");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v("Picasso","Could not fetch image again...");

                                    }

                                });
                    }
                });

    }

    @Override
    public void resetViews() {
        thumbnailImageView.setImageBitmap(null);
        movieTitleTextView.setText("");
        yearTextView.setText("");
    }

}