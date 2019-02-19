package me.aaron.base.util;

/**
 * Created by Chenll on 2016/10/18.
 */

public final class Utils {

    private Utils() {
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

}
