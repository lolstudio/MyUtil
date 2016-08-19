package com.lolstudio.base.manager;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * 获得缓存：CacheManger.sumcache(SettingActivity.this);
 * 清除缓存：CacheManger.clearCacheFolder(getCacheDir(), System.currentTimeMillis());
 //清楚ImageLoader缓存
 ImageLoader.getInstance().clearDiskCache();
 CacheManger.clearCacheFolder(ImageLoader.getInstance().getDiskCache().getDirectory(), System.currentTimeMillis());
 * 统计缓存清除缓存
 * @author Administrator
 *
 */
public class CacheManger {
    public static String sumcache(Context con) {
        // 计算缓存大小
        long fileSize = 0;
        //不统计files目录
//        File filesDir = con.getFilesDir();
        File cacheDir = con.getCacheDir();
//        File sdDir = new File(Environment.getExternalStorageDirectory()
//                + "/yilule/Cache");
        File sdDir = ImageLoader.getInstance().getDiskCache().getDirectory();

//        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        fileSize += getDirSize(sdDir);
        if (fileSize > 0) {
            return formatFileSize(fileSize);
        } else
            return "";
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param numDays 当前系统时间
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    //另一种方法
    /**
     *  new Thread(){
    @Override
    public void run() {
    super.run();
    CacheUtils.deleteCache(mContext);
    }
    }.start();
     Toast.makeText(mContext,R.string.clear_ok,Toast.LENGTH_SHORT).show();

     * @param context
     */
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
