第二种：测试更方便，配置接口公用属性，简单更改即可https://github.com/Jude95/RequestVolley
---------------------------------------------------------
使用方法
private void Postrequest(){
		String url="http://apis.haoservice.com/weather";
		VolleyRequest.getInstance().newGsonRequest(Method.POST, url, WuHan.class, getParams(), new Response.Listener<WuHan>() {

			@Override
			public void onResponse(WuHan response) {
				Toast.makeText(TestActivity.this, "222"+response.toString(), 1).show();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		});
	}
	
	public Map<String ,String> getParams(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("cityname", "%E5%8C%97%E4%BA%AC");
		map.put("key", "c03c1a42ff744c679be9fc9f0a5077e2");
		return map;
	}
		
	private void Getrequest() {
		String url="http://apis.haoservice.com/weather?cityname=%E5%8C%97%E4%BA%AC&key=c03c1a42ff744c679be9fc9f0a5077e2";
		VolleyRequest.getInstance().newGsonRequest(url ,WuHan.class, new Response.Listener<WuHan>() {

			@Override
			public void onResponse(WuHan response) {
				Toast.makeText(TestActivity.this, response.toString(), 1).show();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		});
	}
	
	
@Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "修改失败,请重试!", Toast.LENGTH_LONG).show();
    	if (error == null) return;
         if (error.getMessage() == null) return;
        byte[] htmlBodyBytes = error.networkResponse.data;
        Toast.makeText(this, new String(htmlBodyBytes), Toast.LENGTH_LONG).show();
    }
    
---------------------------------------------------------

package com.topview.http.util;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.topview.ARoadTourismApp;
import com.topview.Config;
import com.topview.util.CommonLog;
import com.topview.util.LogFactory;

public class Requester {
    private static final CommonLog log = LogFactory.createLog();

    private static void addRequest(int requestMethod, String tag,
                                   boolean shouldCache, boolean isForceRefresh, String url,
                                   final Response.Listener<String> listener,
                                   final Response.ErrorListener errorListener) {
        log.e("request url=" + url);
        StringRequest stringRequest = new StringRequest(requestMethod, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        if (arg0 != null)
                            log.e("response=" + arg0.toString());
                        if (listener != null)
                            listener.onResponse(arg0);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // if(error instanceof NetworkError) {
                //
                // } else if(error instanceof ServerError) {
                //
                // }
                // else if( error instanceof AuthFailureError) {
                //
                // } else if( error instanceof ParseError) {
                //
                // } else if( error instanceof NoConnectionError) {
                //
                // } else if( error instanceof TimeoutError) {
                //
                // }
                // }
                log.e("response error " + error);
                if (errorListener != null)
                    errorListener.onErrorResponse(error);
            }

        });
//		 RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, 0, 1.0f);
//		 stringRequest.setRetryPolicy(retryPolicy);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(tag);
        stringRequest.setShouldCache(shouldCache);
        if (shouldCache) {
            if (isForceRefresh)
                forceRefresh(url);
        }
        ARoadTourismApp.mVolleyQueue.add(stringRequest);
    }

    public static void cancel(String tag) {
        ARoadTourismApp.mVolleyQueue.cancelAll(tag);
    }

    //

    /**
         * 强制刷新
         *
         * @param url
         */
        public static void forceRefresh(String url) {
            final Cache.Entry entry = ARoadTourismApp.mVolleyQueue.getCache().get(url);
            if (entry != null && entry.data != null && entry.data.length > 0)
                if (!entry.isExpired()) {
                    ARoadTourismApp.mVolleyQueue.getCache().invalidate(url, true);
                }
        }

        // 广告 请求
        public static void Advs(String tag, boolean shouldCache, boolean isForceRefresh, int count, int siteId, int p, Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Advs";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("count", "" + count);
            builder.appendQueryParameter("siteId", "" + siteId);
            builder.appendQueryParameter("p", "" + p);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //听说列表请求
        public static void ListenList(String tag, boolean shouldCache, boolean isForceRefresh,
                                      int dataId, int pageIndex, int pageSize,
                                      Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Listen";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("dataId", "" + dataId);
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            builder.appendQueryParameter("pageSize", "" + pageSize);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            }else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }

        }

        //听说点赞与取消赞请求
        public static void ListenZan(String tag, boolean shouldCache, boolean isForceRefresh, String listenId, boolean isZan, Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Listen";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("listenId", listenId);
            builder.appendQueryParameter("isZan", "" + isZan);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);

        }

        //听说详情请求
        public static void Listen(String tag, boolean shouldCache, boolean isForceRefresh, String listenId, Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Listen";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("listenId", listenId);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //听说详情评论列表请求
        public static void Review(String tag, boolean shouldCache, boolean isForceRefresh, String listenId, int pageSize, int pageIndex, Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Review";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("listenId", listenId);
            builder.appendQueryParameter("pageSize", "" + pageSize);
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            }else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }
        }

        //听说发表评论
        public static void sendReview(String tag, boolean shouldCache, boolean isForceRefresh, String userId, String type, String listenId, String content, Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Review";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("userId", "" + userId);
            builder.appendQueryParameter("type", type);
            builder.appendQueryParameter("listenId", listenId);
            builder.appendQueryParameter("content", content);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //子景点列表请求
        public static void TourData(String tag, boolean shouldCache, boolean isForceRefresh, int tid, Response.Listener<String> listener,
                                    Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("tid", "" + tid);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //首页地图景点列表请求
        public static void TourData(String tag, boolean shouldCache, boolean isForceRefresh, int pageSize, int pageIndex,
                                    int parentId, Response.Listener<String> listener,
                                    Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            builder.appendQueryParameter("pageSize", "" + pageSize);
            builder.appendQueryParameter("parentId", "" + parentId);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            }else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }
        }

        //中奖名单请求
        public static void Activity(String tag, boolean shouldCache, boolean isForceRefresh, int activityId, int pageSize, int pageIndex,
                                    Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Activity";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("activityId", "" + activityId);
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            builder.appendQueryParameter("pageSize", "" + pageSize);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            }else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }
        }

        //一路乐登陆
        public static void User(String tag, boolean shouldCache, boolean isForceRefresh, String username, String userpassword, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/User";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("email", username);
            builder.appendQueryParameter("password", "" + userpassword);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //第三方登录
        public static void User(String tag, boolean shouldCache, boolean isForceRefresh, String id, String openId, String nickName, boolean isnew, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/User/" + id;
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("openId", openId);
            builder.appendQueryParameter("nickName", "" + nickName);
            builder.appendQueryParameter("isnew", "" + isnew);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //留言列表请求
        public static void Comments(String tag, boolean shouldCache, boolean isForceRefresh, int pid, int pageSize, int pageIndex, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Comments";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("pid", "" + pid);
            builder.appendQueryParameter("pageSize", "" + pageSize);
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            }else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }
        }

        //消息列表请求
        public static void Message(String tag, boolean shouldCache, boolean isForceRefresh, String idCode, String accId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Message";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("idCode", "" + idCode);
            builder.appendQueryParameter("accId", "" + accId);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //读取未读消息
        public static void Message(String tag, boolean shouldCache, boolean isForceRefresh, String messageId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Message";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("messageId", messageId);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //趣味题目
        public static void Questions(String tag, boolean shouldCache, boolean isForceRefresh, int id, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Questions/" + id;
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, url, listener, errorListener);
        }

        //提交答题
        public static void Questions(String tag, boolean shouldCache, boolean isForceRefresh, String nickName, int locationId, int score,
                                     String accountId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Questions/";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("name", nickName);
            builder.appendQueryParameter("locationId", "" + locationId);
            builder.appendQueryParameter("masks", "" + score);
            if (!TextUtils.isEmpty(accountId))
                builder.appendQueryParameter("accountId", "" + accountId);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //注册
        public static void User(String tag, boolean shouldCache, boolean isForceRefresh, String userName, String email, String password, String confirmPassword, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/User";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("userName", userName);
            builder.appendQueryParameter("email", email);
            builder.appendQueryParameter("password", password);
            builder.appendQueryParameter("confirmPassword", confirmPassword);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //景区详情请求
        public static void SingleRequest(String tag, boolean shouldCache, boolean isForceRefresh, int lid, int count, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/SingleRequest";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("lid", "" + lid);
            builder.appendQueryParameter("count", "" + count);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //获取热门搜索
        public static void TourHotData(String tag, boolean shouldCache, boolean isForceRefresh, int total, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("total", "" + total);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        // 搜索
        public static void TourData(String tag, boolean shouldCache, boolean isForceRefresh, int count, String keyword, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("count", "" + count);
            builder.appendQueryParameter("keyword", keyword);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //发表评论
        public static void Comments(String tag, boolean shouldCache, boolean isForceRefresh, String lid, String scope, String accountId, String content, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Comments";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("lid", lid);
            builder.appendQueryParameter("scope", scope);
            builder.appendQueryParameter("accountId", accountId);
            builder.appendQueryParameter("content", content);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //景点列表
        public static void TourData(String tag, boolean shouldCache, boolean isForceRefresh, int pageSize, int pageIndex, double lat,
                                    double lng, int parentId, int order, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("pageSize", "" + pageSize);
            builder.appendQueryParameter("pageIndex", "" + pageIndex);
            builder.appendQueryParameter("lat", "" + lat);
            builder.appendQueryParameter("lng", "" + lng);
            builder.appendQueryParameter("parentId", "" + parentId);
            builder.appendQueryParameter("order", "" + order);
            if (pageIndex >= 2) {
                addRequest(Request.Method.GET, tag, false, isForceRefresh, builder.toString(), listener, errorListener);
            } else {
                addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
            }
        }
        //订购列表
        public static void RequestPayForServer(String tag,boolean shouldCache,boolean isForceRefresh,int lid,int pageIndex,int pageSize, Response.Listener<String> listener, Response.ErrorListener errorListener){
            String url=Config.SERVER_ADDR+"api/Line/GetLineGroupList";
            Uri.Builder builder=Uri.parse(url).buildUpon();
            builder.appendQueryParameter("lid",""+lid);
            builder.appendQueryParameter("pageIndex",""+pageIndex);
            builder.appendQueryParameter("pageSize",""+pageSize);
            System.err.println("payurl>>>" + builder.toString());
            if(pageIndex>=1){
                addRequest(Request.Method.GET,tag,false,isForceRefresh,builder.toString(), listener, errorListener);
            }else{
                addRequest(Request.Method.GET,tag,shouldCache,isForceRefresh,builder.toString(), listener, errorListener);
            }
        }

        // 景区其它
        public static void TourDataOther(String tag, boolean shouldCache, boolean isForceRefresh, int id, String name, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData/" + id;
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("name", name);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //景点详情请求
        public static void SingleRequestOld(String tag, boolean shouldCache, boolean isForceRefresh, int sid, int count, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/SingleRequest";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("sid", "" + sid);
            builder.appendQueryParameter("count", "" + count);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        // 景点详情游戏信息
        public static void Activity(String tag, boolean shouldCache, boolean isForceRefresh, int lid, String idCode, String accountId, int activityType, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Activity";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("lid", "" + lid);
            builder.appendQueryParameter("idCode", idCode);
            builder.appendQueryParameter("accountId", accountId);
            if (activityType > 0) {
                builder.appendQueryParameter("activityType", "" + activityType);
            }
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

       /* //获取在线导览图xml文件
        public static void TourMap(String tag, boolean shouldCache, boolean isForceRefresh, int locationid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourMap";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("dataid", "" + locationid);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }*/

        //获取在线导览图xml文件
        public static void TourMap(String tag, boolean shouldCache, boolean isForceRefresh, int attractionId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourMap/" + attractionId;
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, url, listener, errorListener);
        }

        //获取消息未读数
        public static void MessageNum(String tag, boolean shouldCache, boolean isForceRefresh, String phoneidCode, String accountId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Message";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("phoneidCode", phoneidCode);
            builder.appendQueryParameter("accountId", accountId);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //验证key是否是http还是key跳转
        public static void CheckKeyBound(String tag, boolean shouldCache, boolean isForceRefresh, String id, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("id", id);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //验证打包key(景点详情)
        public static void verifySomeKey(String tag, boolean shouldCache, boolean isForceRefresh, String key, String idCode, String uid, int useLocationId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Key";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("key", key);
            builder.appendQueryParameter("idCode", idCode);
            builder.appendQueryParameter("uid", uid);
            builder.appendQueryParameter("useLocationId", "" + useLocationId);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //验证KEY (POST)（新版本）
        public static void CheckKeyURL(String tag, boolean shouldCache, boolean isForceRefresh, String key, int locationId, String idCode, String uid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Key";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("key", key);
            builder.appendQueryParameter("locationId", "" + locationId);
            builder.appendQueryParameter("idCode", idCode);
            builder.appendQueryParameter("uid", uid);
            addRequest(Request.Method.POST, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        // 判断Key的类型 并获得Key
        public static void DecideKey(String tag, boolean shouldCache, boolean isForceRefresh, String key, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/Key";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("key", key);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //获得景点城市数据
        public static void getCityData(String tag, boolean shouldCache, boolean isForceRefresh, int type, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/TourData";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("scaleType", "" + type);
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

        //设置百度推送用户与Tag绑定
        public static void setPushTag(String tag, boolean shouldCache, boolean isForceRefresh, String locationId, String baiduUserId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            String url = Config.SERVER_ADDR + "api/PushMsg";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("locationId", locationId);
            builder.appendQueryParameter("baiduUserId", baiduUserId);
            builder.appendQueryParameter("device", "3");
            addRequest(Request.Method.GET, tag, shouldCache, isForceRefresh, builder.toString(), listener, errorListener);
        }

    }
