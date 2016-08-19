package com.lolstudio.base.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lolstudio.base.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * <uses-permission android:name="android.permission.INTERNET" />
 <!-- Include next permission if you want to allow UIL to cache images on SD card -->
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 * http://blog.csdn.net/vipzjyno1/article/details/23206387
 * http://blog.csdn.net/xiaanming/article/details/26810303
 * http://www.bdqn.cn/news/201310/11541.shtml
 *
 * @author Administrator
 *
 */
public class ImageloaderManager {

	static DisplayImageOptions options;

	private static ImageloaderManager instance=null;

	private ImageloaderManager(){

	}

	public static ImageloaderManager getInstance(){
		if(null==instance){
			synchronized (ImageloaderManager.class){
				if(null==instance){
					instance=new ImageloaderManager();
				}
			}
		}
		return instance;
	}

	public  void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
				.memoryCacheSize(2 * 1024 * 1024)    // 线程池的大小
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) //缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs()  //打印log信息  Remove for release app
				.build();//开始构建

		ImageLoader.getInstance().init(config);
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));// init image loader
	}

	public static DisplayImageOptions getOptions() {
		if (options == null) {
			options = new DisplayImageOptions.Builder()
					.imageScaleType(ImageScaleType.EXACTLY)
					.showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
					.showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.mipmap.ic_launcher)//设置图片加载/解码过程中错误时候显示的图片
					.bitmapConfig(Bitmap.Config.RGB_565)  //设置图片的解码类型//
					.cacheInMemory(true)//设置下载的图片是否缓存在内存中
					.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
					.considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
					//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
					.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
					.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少 ,90度为圆形
					.displayer(new FadeInBitmapDisplayer(20))//是否图片加载好后渐入的动画时间
					.build();//构建完成
		}
		return options;
	}

	public  void DisplayImage(String imgUrl,ImageView view){
		ImageLoader.getInstance().displayImage(imgUrl, view);
	}
	public  void DisplayImage(String imgUrl,ImageView view,DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(imgUrl, view, options);
	}
	public void DisplayImage(String imgUrl,ImageView view,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(imgUrl, view, listener);
	}
	public void DisplayImage(String imgUrl,ImageView view,DisplayImageOptions options,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(imgUrl, view, options,listener);
	}
	public void DisplayImageProgress(String imgUrl,ImageView view,final ProgressBar progressBar,DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(imgUrl, view, options,new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				//加载失败
				String message = null;
				switch (arg2.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		},new ImageLoadingProgressListener() {

			@Override
			public void onProgressUpdate(String arg0, View arg1, int arg2, int arg3) {
				//加载进度
				progressBar.setMax(arg3);
				progressBar.setProgress(arg2);
			}
		});
	}

	public static void CleanCache(){
		ImageLoader.getInstance().clearDiscCache();
		ImageLoader.getInstance().clearDiskCache();
	}

}
