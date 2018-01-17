package com.example.http;

import android.content.Context;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class HttpUtils {
    private static HttpUtils instance ;

    private boolean debug;
    private Context context;
    private Object eyeHttp;
    //天眼API
    private String EYE_API = "http://baobab.kaiyanapp.com/api/";


    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    return new HttpUtils();
                }
            }

        }
        return instance;
    }

    public <T> T getEyeServer(Class<T> a) {
        if (eyeHttp == null) {
            synchronized (HttpUtils.class) {
                if (eyeHttp == null) {
                    return getBuilder(EYE_API).build().create(a);
                }
            }
        }
        return (T) eyeHttp;
    }
    public  void init(Context context, boolean debug) {
        this.debug = debug;
        this.context = context;
    }

    public Retrofit.Builder getBuilder(String url) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder;
    }

    public OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client = getUnsafeOkHttpClient();
        return client;
    }

    /**
     * https请求由于证书验证问题,直接请求一般都会失败.
     * 一般是做证书验证处理或者忽略证书验证
     * 这里采用后者
     */
    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(getInterceptor());
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        /*if (debug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }*/
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
