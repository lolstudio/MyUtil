package com.lolstudio.base.photo;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * 图片的模型类，存放图片的信息.
 * @author Jack Tony
 * @date 2015/4/26
 */
public class SimplePhoto {

    /**
     * 图片的uri
     */
    public Uri uri;

    /**
     * 图片要旋转的角度。方向不正确的图像可以根据这个进行旋转操�?
     */
    public int degree;

    /**
     * 图像的bitmap
     */
    public Bitmap bitmap;
}
