package com.lolstudio.base.util;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪切板工具类
 * @author http://blog.csdn.net/voiceofnet/article/details/7741259
 * @date 2015/8/6
 */
public class ClipBoardUtil {

    /**
     * 实现复制功能
     * @param context
     * @param text
     */
    public static void copy(Context context,String text) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     */
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}