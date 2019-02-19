package com.aaron.doubanmovie.util;


import java.util.List;

import me.aaron.dao.model.Celebrity;

/**
 * Created by OA on 2016/1/26.
 */
public class MovieParser {

    private static final String SEPARATOR = "/";

    public static String parseCelebrities(List<Celebrity> directors) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < directors.size(); i++) {
            sb.append(directors.get(i).getName());

            if (i != directors.size() - 1) {
                sb.append(SEPARATOR);
            }
        }

        return sb.toString();
    }

    public static String parseGenres(List<String> genres) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < genres.size(); i++) {
            sb.append(genres.get(i));

            if (i != genres.size() - 1) {
                sb.append(SEPARATOR);
            }
        }

        return sb.toString();
    }

}
