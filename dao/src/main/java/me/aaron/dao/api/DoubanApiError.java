package me.aaron.dao.api;

import java.io.IOException;

/**
 * Created by Chenll on 2016/10/14.
 */
public class DoubanApiError extends IOException {

    public boolean isApiError() {
        return false;
    }

}
