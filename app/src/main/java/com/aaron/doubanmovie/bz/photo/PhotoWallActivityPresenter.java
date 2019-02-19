package com.aaron.doubanmovie.bz.photo;

import com.aaron.doubanmovie.common.Presenter;
import com.aaron.doubanmovie.common.View;

import java.util.List;

/**
 * Created by Chenll on 2016/10/13.
 */

public interface PhotoWallActivityPresenter extends Presenter {

    /**
     * 获取所有的照片
     * @param id 电影Id
     */
    void fetchAllPhotos(String id);

    interface IView extends View {
        /**
         * 显示照片墙
         * @param photos 所有照片
         */
        void showAllPhotos(List<String> photos);
    }

}
