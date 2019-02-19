package com.aaron.doubanmovie.bz.detail;

import com.aaron.doubanmovie.common.Presenter;
import com.aaron.doubanmovie.common.View;

import java.util.List;

/**
 * Created by Chenll on 2016/10/13.
 */

public interface MovieDetailActivityPresenter extends Presenter {

    /**
     * 获取电影详情
     * @param id 电影Id
     */
    void fetchMovieDetail(String id);

    /**
     * 获取电影剧照
     * @param id 电影Id
     * @param count 数量
     */
    void fetchMoviePhotos(String id, int count);

    interface IView extends View {

        /**
         * 展开剧情梗概
         */
        void expandSummaryText();

        /**
         * 缩起剧情梗概
         */
        void collapseSummaryText();

        /**
         * 设置剧情梗概
         * @param summary 剧情梗概
         */
        void setSummary(String summary);

        /**
         * 显示空视图
         */
        void showEmptyView();

        /**
         * 刷新剧照
         * @param photos
         */
        void refreshPhotos(List<String> photos);

        /**
         * 设置背景图
         * @param photo 背景图
         */
        void loadBackDrop(String photo);

    }

}
