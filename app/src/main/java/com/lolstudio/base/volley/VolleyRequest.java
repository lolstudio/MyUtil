package com.lolstudio.base.volley;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.GsonRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.cache.BitmapImageCache;
import com.android.volley.cache.SimpleImageLoader;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.android.volley.ui.NetworkImageView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


/**
 * http://zhengxiaopeng.com/2014/12/05/Android-Volley%E8%87%AA%E5%AE%9A%E4%B9%89
 Request-JacksonRequest%E3%80%81GsonRequest/
 * https://github.com/zhengxiaopeng/AndroidUtilsblob
 /master/library/src/main/java/com/rocko/android/common/http/volley/VolleyRequest.java
 *  https://github.com/DWorkS/VolleyPlus
 *  注：可再扩展GsonRequest，方便post请求，先下载volleyplus代码，在更改GsonRequest，最后打包成jar
 *  http://www.cnblogs.com/freexiaoyu/p/4540122.html
 */
public class VolleyRequest {

	private static VolleyRequest instance = null;
	private RequestQueue volleyRequestQueue;
	private SimpleImageLoader mImageLoader;

	private VolleyRequest() {

	}

	public static VolleyRequest getInstance() {
		if (null == instance) {
			synchronized (VolleyRequest.class) {
				if (null == instance) {
					instance = new VolleyRequest();
				}
			}
		}
		return instance;
	}

	public void init(Context con) {
		volleyRequestQueue = Volley.newRequestQueue(con);
		mImageLoader = new SimpleImageLoader(volleyRequestQueue,
				BitmapImageCache.getInstance(null));
	}

	/**
	 * 创建发送一个StringRequest请求(GET)
	 *
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public StringRequest newStringRequest(String url,
										  Listener<String> listener, ErrorListener errorListener) {
		StringRequest request = new StringRequest(url, listener, errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个StringRequest请求，如果是post重写getParams()方法
	 *
	 * @param method
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public StringRequest newStringRequest(int method, String url,
										  Listener<String> listener, ErrorListener errorListener) {
		StringRequest request = new StringRequest(method, url, listener,
				errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个JsonObjectRequest请求(GET)
	 *
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public JsonObjectRequest newJsonObjectRequest(String url,
												  JSONObject jsonRequest, Listener<JSONObject> listener,
												  ErrorListener errorListener) {
		JsonObjectRequest request = new JsonObjectRequest(url, jsonRequest,
				listener, errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个JsonObjectRequest请求
	 *
	 * @param method
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public JsonObjectRequest newJsonObjectRequest(int method, String url,
												  JSONObject jsonRequest, Listener<JSONObject> listener,
												  ErrorListener errorListener) {
		JsonObjectRequest request = new JsonObjectRequest(method, url,
				jsonRequest, listener, errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个JsonArrayRequest请求(GET)
	 *
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public JsonArrayRequest newJsonArrayRequest(String url,
												Listener<JSONArray> listener, ErrorListener errorListener) {
		JsonArrayRequest request = new JsonArrayRequest(url, listener,
				errorListener);
		add(request);
		return request;
	}

	/**
	 * 通过ImageLoader获取图片(自动管理缓存)
	 *
	 * @param imageView
	 * @param imgViewUrl
	 * @param defaultImageResId
	 * @param errorImageResId
	 */
	public void newImageViewLoaderRequest(ImageView imageView,
										  String imgViewUrl, int defaultImageResId, int errorImageResId) {
		ImageListener listener = ImageLoader.getImageListener(imageView,
				defaultImageResId, errorImageResId);
		mImageLoader.get(imgViewUrl, listener);
	}

	/**
	 * networkImageView处理
	 *
	 * @param networkImageView
	 * @param imageUrl
	 * @param defaultImageResId
	 * @param errorImageResId
	 */
	public void newNetworkImageViewsRequest(NetworkImageView networkImageView,
											String imageUrl, int defaultImageResId, int errorImageResId) {
		networkImageView.setDefaultImageResId(defaultImageResId);
		networkImageView.setErrorImageResId(errorImageResId);
		networkImageView.setImageUrl(imageUrl, mImageLoader);
	}

	/**
	 * 创建发送一个 GsonRequest(GET)
	 *
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public <T> GsonRequest<T> newGsonRequest(String url, Class<T> clazz,
											 Listener<T> listener, ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener,
				errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个 GsonRequest(Get或者Post都行)
	 *
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public <T> GsonRequest<T> newGsonRequest(int method, String url,
											 Class<T> clazz, Map<String, String> params, Listener<T> listener,
											 ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(method, url, clazz, params,
				listener, errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个TypeToken的GsonRequest(GET)
	 *
	 * @param url
	 * @param typeToken
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public <T> GsonRequest<T> newGsonRequest(String url,
											 TypeToken<T> typeToken, Listener<T> listener,
											 ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(url, typeToken, listener,
				errorListener);
		add(request);
		return request;
	}

	/**
	 * 创建发送一个 TypeToken的GsonRequest(Get或者Post都行)
	 *
	 * @param url
	 * @param typeToken
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public <T> GsonRequest<T> newGsonRequest(int method, String url,
											 TypeToken<T> typeToken, Map<String, String> params,
											 Listener<T> listener, ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(method, url, typeToken,
				params, listener, errorListener);
		add(request);
		return request;
	}

	private <T> Request<T> add(Request<T> request) {
		return volleyRequestQueue.add(request);
	}

	/**
	 * 中断、取消所有请求
	 *
	 * @param tag
	 */
	public void cancelAll(Object tag) {
		volleyRequestQueue.cancelAll(tag);
	}

}
