package com.shyms.farendating.http;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public class AsyncHttp {

    // 超时时间
    private static final int TIME_OUT = 30000;
    private static AsyncHttpClient asyncHttpClient;

    /**
     * AsyncHttpClient 单例模式
     *
     * @return AsyncHttpClient
     */
    private synchronized static AsyncHttpClient getInstance() {
        if (null == asyncHttpClient) {
            asyncHttpClient = new AsyncHttpClient();
        }
        return asyncHttpClient;
    }

    // get重写
    public static void get(String url, final AsyncHttpCallBack asyncHttpCallBack) {
        get(-1, url, null, asyncHttpCallBack);
    }

    public static void get(String url, RequestParams params,
                           final AsyncHttpCallBack asyncHttpCallBack) {
        get(-1, url, params, asyncHttpCallBack);
    }

    public static void get(final int what, String url,
                           final AsyncHttpCallBack asyncHttpCallBack) {
        get(what, url, null, asyncHttpCallBack);
    }

    // post重写
    public static void post(String url,
                            final AsyncHttpCallBack asyncHttpCallBack) {
        post(-1, url, null, asyncHttpCallBack);
    }

    public static void post(final int what, String url,
                            final AsyncHttpCallBack asyncHttpCallBack) {
        post(what, url, null, asyncHttpCallBack);
    }

    public static void post(String url, RequestParams params,
                            final AsyncHttpCallBack asyncHttpCallBack) {
        post(-1, url, params, asyncHttpCallBack);
    }

    public static void post(String url, Header[] headers, RequestParams params,
                            final AsyncHttpCallBack asyncHttpCallBack) {
        post(-1, url, headers, params, asyncHttpCallBack);
    }

    public static void get(String url, Header[] headers, RequestParams params, final AsyncHttpCallBack asyncHttpCallBack) {
        get(-1, url, headers, params, asyncHttpCallBack);
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param asyncHttpCallBack
     */
    public static void get(final int what, String url, RequestParams params,
                           final AsyncHttpCallBack asyncHttpCallBack) {
        if (params == null) {
            params = new RequestParams();
        }
        getInstance().setTimeout(TIME_OUT);
        getInstance().get(url, params, new MyResponseHandler(asyncHttpCallBack, what));
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param asyncHttpCallBack
     */
    public static void post(final int what, String url, RequestParams params,
                            final AsyncHttpCallBack asyncHttpCallBack) {
        if (params == null) {
            params = new RequestParams();
        }
        getInstance().setTimeout(TIME_OUT);
        getInstance().post(url, params, new MyResponseHandler(asyncHttpCallBack, what));
    }

    /**
     * 需传header的get
     *
     * @param what
     * @param url
     * @param headers
     * @param params
     * @param asyncHttpCallBack
     */
    public static void get(final int what, String url, Header[] headers,
                           RequestParams params, final AsyncHttpCallBack asyncHttpCallBack) {
        if (params == null) {
            params = new RequestParams();
        }
        getInstance().setTimeout(TIME_OUT);
        if (headers != null) {
            for (Header header : headers) {
                getInstance().addHeader(header.getName(), header.getValue());
            }
        }
        getInstance().get(url, params, new MyResponseHandler(asyncHttpCallBack, what));
    }

    /**
     * 需传header的post
     *
     * @param what
     * @param url
     * @param headers
     * @param params
     * @param asyncHttpCallBack
     */
    public static void post(final int what, String url, Header[] headers,
                            RequestParams params, final AsyncHttpCallBack asyncHttpCallBack) {
        if (params == null) {
            params = new RequestParams();
        }
        getInstance().setTimeout(TIME_OUT);
        if (headers != null) {
            for (Header header : headers) {
                getInstance().addHeader(header.getName(), header.getValue());
            }
        }
        getInstance().post(url, params, new MyResponseHandler(asyncHttpCallBack, what));
    }

    /**
     * 使用HttpEntity作为参数传
     *
     * @param context
     * @param what
     * @param url
     * @param entity
     * @param headers
     * @param asyncHttpCallBack
     */
    public static void post(Context context, final int what, String url,
                            HttpEntity entity, Header[] headers,
                            final AsyncHttpCallBack asyncHttpCallBack) {

        getInstance().setTimeout(TIME_OUT);
        if (headers != null) {
            for (Header header : headers) {
                getInstance().addHeader(header.getName(), header.getValue());
            }
        }
        getInstance().post(context, url, entity, "application/json",
                new MyResponseHandler(asyncHttpCallBack, what));

    }

    public static void delete(final int what, String url, Header[] headers,
                              final AsyncHttpCallBack asyncHttpCallBack) {

        getInstance().setTimeout(TIME_OUT);
        if (headers != null) {
            for (Header header : headers) {
                getInstance().addHeader(header.getName(), header.getValue());
            }
        }
        getInstance().delete(url, new MyResponseHandler(asyncHttpCallBack, what));
    }

    public static void postJSON(Activity activity, String url, StringEntity stringEntity, AsyncHttpResponseHandler responseHandler) {
        getInstance().addHeader("Content-Type", "charset=utf-8");
        getInstance().post(activity, url, stringEntity, "application/json", responseHandler);
    }


    static class MyResponseHandler extends JsonHttpResponseHandler {

        AsyncHttpCallBack asyncHttpCallBack;
        int what;

        public MyResponseHandler(AsyncHttpCallBack asyncHttpCallBack, int what) {
            this.asyncHttpCallBack = asyncHttpCallBack;
            this.what = what;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            asyncHttpCallBack.onSuccess(what, statusCode, response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, JSONObject errorResponse) {
            asyncHttpCallBack.onFailure(what, statusCode, errorResponse);
        }

    }

    public static void cancelAllRequests() {
        getInstance().cancelAllRequests(true);
    }

    public static boolean emptyReference() {
        if (AsyncHttp.getInstance() != null) {
            return true;
        } else {
            return false;
        }

    }

    public static void clearCredentialsProvider() {

        AsyncHttp.getInstance().clearCredentialsProvider();
    }

    public static void recycle() {
        asyncHttpClient = null;
    }


}