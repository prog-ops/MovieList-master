package com.movielist.movielist.core.api;

import android.content.Context;
import android.util.Log;

import com.movielist.movielist.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * //Min 30-12-18 214556
 */

public class OMDbApiService {
    private static final String TAG = OMDbApiService.class.getSimpleName();

    private static OMDbApiService instance;

    private Context applicationContext;
    private OMDbApi api;

    private static Retrofit retrofit;

    public OMDbApiService(Context applicationContext) {
        this.applicationContext = applicationContext;
        initRetrofit();
    }

    public static OMDbApiService getInstance(Context context) {
        if (instance == null) {
            instance = new OMDbApiService(context);
        }
        return instance;
    }

    public OMDbApi getOMDbApi() {
        return getInstance(applicationContext).api;
    }

    private void initRetrofit() {
        OkHttpClient client;
        if (BuildConfig.IS_API_MOCK_ENABLE) {
            client = initMockApi();
        } else {
            client = initApi();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.OMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getCustomGson()))
                .client(client)
                .build();

        api = retrofit.create(OMDbApi.class);
    }

    private OkHttpClient initApi() {
        OkHttpClient.Builder builder = getBuilder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", BuildConfig.API_KEY)
                        .addQueryParameter("type", "movie")
                        .addQueryParameter("y", "2015") //Min 30-12-18 214240 try
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return builder.build();
    }

    private OkHttpClient initMockApi() {
        OkHttpClient.Builder builder = getBuilder();
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            MockResponseInterceptor mock = new MockResponseInterceptor(applicationContext);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Log.d("MOCK REQUEST [%s]", chain.request().url().toString());
                    return chain.proceed(chain.request());
                }
            });
            builder.addInterceptor(mock);
        }

        return builder.build();
    }

    private OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder;
    }

    private Gson getCustomGson() {
        return new GsonBuilder().create();
    }

    public static Retrofit retrofit() {
        return retrofit;
    }
}
