package me.aaron.dao.api.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.aaron.dao.model.Movie;

/**
 * Created by aaronchan on 16/4/30.
 */
public class InTheater extends BaseResponse {
    @SerializedName("subjects")
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
