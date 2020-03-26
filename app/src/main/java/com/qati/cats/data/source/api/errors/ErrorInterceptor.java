package com.qati.cats.data.source.api.errors;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ErrorInterceptor implements Interceptor {


    private static final String ERROR_MESSAGE = "error_message";
    private static final String CODE = "code";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);

        String responseString = response.body().string();
        ServerError error = null;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has(ERROR_MESSAGE)) {
                error = new ServerError(jsonObject.getInt(CODE), jsonObject.getString(ERROR_MESSAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (error != null) {
            throwException(new ServerErrorException(error));
        }

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), responseString)).build();

    }

    private void throwException(ServerErrorException e) {
        throw e;
    }
}