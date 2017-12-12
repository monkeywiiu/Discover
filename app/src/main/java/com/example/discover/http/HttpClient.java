package com.example.discover.http;

import com.example.discover.bean.EyeBean;
import com.example.http.HttpUtils;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

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
    @GET("hot")
    Observable<EyeBean> getEyeDetail(@Query("start") int start, @Query("num") int num);

}
