package com.movielist.movielist.core.api;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * //Jum 28-12-18 210812
 */

public class MockResponseInterceptor implements Interceptor {

    private final String TAG = MockResponseInterceptor.class.getSimpleName();

    private Context context;

    public MockResponseInterceptor(Context context) {
        this.context = context.getApplicationContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)  //Min 30-12-18 212144
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get resource ID for mock response file.
        String fileName = getFilename(chain.request());
        int resourceId = getResourceId(fileName);
        if (resourceId == 0) {
            Log.i(TAG, "Could not find res/raw/" + fileName +
                            ". Request will be done through the actual endpoint");

            Request request = chain.request().newBuilder().build();
            return chain.proceed(request);
        } else {
            // Get input stream and mime type for mock response file.
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
            if (mimeType == null) {
                mimeType = "application/json";
            }

            // Build and return mock response.
            return new Response.Builder()
                    .addHeader("content-type", mimeType)
                    .body(ResponseBody.create(MediaType.parse(mimeType), toByteArray(inputStream)))
                    .code(200)
                    .message("Mock response from res/raw/" + fileName)
                    .protocol(Protocol.HTTP_1_0)
                    .request(chain.request())
                    .build();
        }
    }

    private String getFilename(Request request) throws IOException {
        String requestedMethod = request.method();
        String filename = requestedMethod + request.url().url().getPath();
        filename = filename.replace("/", "_")
                        .replace("-", "_").toLowerCase();
        return filename;
    }

    private int getResourceId(String filename) {
        return context.getResources().getIdentifier(
                filename,
                "raw",
                context.getPackageName()
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)  //Jum 28-12-18 210907
    private static byte[] toByteArray(InputStream is) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] b = new byte[4096];
            int n;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        }
    }
}