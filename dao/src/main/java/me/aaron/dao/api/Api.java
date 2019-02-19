package me.aaron.dao.api;


import java.util.List;

import me.aaron.dao.api.gson.ComingSoon;
import me.aaron.dao.api.gson.InTheater;
import me.aaron.dao.api.gson.Top;
import me.aaron.dao.model.Movie;
import rx.Observable;

/**
 * Created by aaronchan on 16/4/22.
 */
public interface Api {

    Observable<InTheater> getInTheaters(String city, int start, int count);

    Observable<Movie> getMovie(String id);

    Observable<ComingSoon> getComingSoon(int start, int count);

    Observable<Top> getTop(int start, int count);

    Observable<List<String>> getMoviePhotos(String id, int count);

}
