package me.aaron.dao.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.aaron.base.util.Logger;
import me.aaron.dao.api.gson.ComingSoon;
import me.aaron.dao.api.gson.InTheater;
import me.aaron.dao.api.gson.Top;
import me.aaron.dao.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

import static me.aaron.base.util.Utils.checkNotNull;

/**
 * Created by aaronchan on 16/4/22.
 */
public class ApiImpl implements Api {

    private static final Logger logger = new Logger(ApiImpl.class);

    private ApiService mApiService;
    private OkHttpClient mOkHttpClient;

    public ApiImpl(ApiService apiService, OkHttpClient okHttpClient) {
        mApiService = checkNotNull(apiService, "apiService == null");
        mOkHttpClient = checkNotNull(okHttpClient, "okHttpClient == null");
    }

    @Override
    public Observable<InTheater> getInTheaters(String city, int start, int count) {
        checkNotNull(city, "city == null");
        return mApiService.getInTheaters(city, start, count);
    }

    @Override
    public Observable<Movie> getMovie(String id) {
        checkNotNull(id, "id == null");
        return mApiService.getMovie(id);
    }

    @Override
    public Observable<ComingSoon> getComingSoon(int start, int count) {
        return mApiService.getComingSoon(start, count);
    }

    @Override
    public Observable<Top> getTop(int start, int count) {
        return mApiService.getTop(start, count);
    }

    @Override
    public Observable<List<String>> getMoviePhotos(final String id, final int count) {
        checkNotNull(id, "id == null");

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String url = "https://movie.douban.com/subject/" + id + "/photos?type=S";

                try {
                    subscriber.onNext(parseUrl(url));
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(html -> {
            List<String> images = getImageLinks(html, count);
            return images;
        }).map(images -> {
            List<String> urls = new ArrayList<>();
            for (String image : images) {
                try {
                    String html = parseUrl(image);

                    String url = getImageUrl(html);
                    if (!TextUtils.isEmpty(url)) {
                        urls.add(getImageUrl(html));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Observable.error(e);
                }
            }

            logger.debug("urls size is " + urls.size());
            return urls;
        });
    }

    private String parseUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    @NonNull
    private List<String> getImageLinks(String html, int count) {
        List<String> images = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Element body = document.body();

        int total = 0;
        Elements hrefs = body.select("a[href^=https://movie.douban.com/photos/photo/]");
        for (Element href : hrefs) {
            if (total >= count) {
                break;
            }

            String image = href.attr("href");
            if (image.endsWith("/")) {
                images.add(image);
                total++;
            }
        }
        return images;
    }

    private String getImageUrl(String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();

        Elements images = body.select("img[src~=(https://img.*?doubanio.com/view/photo/photo/public/)]");

        return images.size() == 0 ? "" : images.get(0).attr("src");
    }

}
