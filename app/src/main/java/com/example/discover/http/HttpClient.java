package com.example.discover.http;

import com.example.http.HttpUtils;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public interface HttpClient {
    class Builder {
        public static HttpClient getEyeService() {
            return HttpUtils.getInstance().getEyeServer(HttpClient.class);
        }
    }


}
