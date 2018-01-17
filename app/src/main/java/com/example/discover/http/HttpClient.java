package com.example.discover.http;

import com.example.discover.bean.CategoryDetailBean.FindCategory;
import com.example.discover.bean.HotEyeBean;
import com.example.http.HttpUtils;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2017/12/9 0009.
 */

public interface HttpClient {
    class Builder {
        public static HttpClient getEyeService() {
            return HttpUtils.getInstance().getEyeServer(HttpClient.class);
        }
    }

    /**
     *
     * @param start 从多少开始，如从“0”开始
     * @param num   一次请求的数目（从start开始计算）
     * @return
     */
    @GET("v4/discovery/hot")
    Flowable<HotEyeBean> getEyeHot(@Query("start") int start, @Query("num") int num);

    /**
     *
     * @param id = 2创意4开胃6旅行8预告10动画12剧情14广告18运动20音乐22记录24时尚26萌宠28搞笑30游戏32科普34集锦36生活38综艺
     * @return
     */
    @GET("v3/categories/detail")
    Flowable<FindCategory> getEyeCateGory(@Query("id") int id);
}
