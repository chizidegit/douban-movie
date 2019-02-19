package com.aaron.doubanmovie.common;

import android.content.Context;
import android.widget.Toast;

import com.aaron.doubanmovie.R;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by aaronchan on 16/5/2.
 */
public class ExceptionHandler {

    public static void handleHttpException(Context context, HttpException exception) {
        int msg = exception.code() == 403 ? R.string.toast_api_limited : R.string.toast_server_error;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
