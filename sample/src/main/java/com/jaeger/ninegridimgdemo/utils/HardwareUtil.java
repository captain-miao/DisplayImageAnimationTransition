package com.jaeger.ninegridimgdemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;


/**
 * 获取硬件信息
 */
public class HardwareUtil {

    private static int sStatusBarHeight;

    /**
     * get density of screen
     *
     * @param
     * @return
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * get screen width
     *
     * @param
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    /**
     * get screen height
     *
     * @param
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得DisplayMetrics：包含屏幕密度，宽，高等数据。
     *
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue float
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue float
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        if (sStatusBarHeight == 0) {
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sStatusBarHeight = resources.getDimensionPixelSize(resourceId);
                Log.i("HardwareUtil", "get the status bar height: " + sStatusBarHeight);
            }
        }
        if (sStatusBarHeight == 0) {
            sStatusBarHeight = (int) (resources.getDisplayMetrics().density * 25);
        }
        return sStatusBarHeight;
    }
}
