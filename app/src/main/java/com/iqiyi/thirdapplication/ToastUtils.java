package com.iqiyi.thirdapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangqi on 2017/2/23.`
 */

public class ToastUtils {

    private static Toast baseToast;

    /**
     * 推荐使用的, 最简单化的  底部中间的 统一半透明圆角提示
     */
    public static void deafultToast(Context context, String content) {
        deafultToast(context, content, Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40);
    }

    /**
     * 推荐使用的, 最简单化的  底部中间的 统一半透明圆角提示
     */
    public static void deafultToast(Context context, int toastResId) {
        deafultToast(context, context.getResources().getText(toastResId).toString(), Toast.LENGTH_SHORT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40);
    }

    /**
     * 推荐使用的, 最简单化的  底部中间的 统一半透明圆角提示
     */
    public static void deafultToast(Context context, String content, int duration) {
        deafultToast(context, content, duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40);
    }

    /**
     * 推荐使用的, 最简单化的  底部中间的 统一半透明圆角提示
     */
    public static void deafultToast(Context context, int toastResId, int duration) {
        deafultToast(context, context.getResources().getText(toastResId).toString(), duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40);
    }


    /**
     * 推荐使用的, 最简单化的 半透明圆角提示
     * toastCustomView2
     */
    public static void deafultToast(Context context, String content, int duration, int nGravity, int xOffset, int yOffset) {
        baseToast(context, "qiyi_sdk_player_common_toast_layout", 0, 0, "", content, "content", duration, nGravity, xOffset, yOffset);
    }

    /***
     * @param context
     * @param toastMsg
     * @param duration 原 ToastShowUtils.init方法 显示位置 屏幕底部   水平center  尖角不透明
     */
    public static void init(Context context, String toastMsg, int duration) {
        baseToast(context, "phone_my_setting_plugin_toast", 0, 0, "", toastMsg, "plugin_toast_msg", duration, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40);
    }

    public static void toast(Context context, Object msg) {
        toast(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * 大正方形半透明背景的Toast  屏幕中间显示  圆角 String 提示
     *
     * @param context     上下文
     * @param stringResId 字符串id
     */
    public static void toastWithBigBackground(Context context, int stringResId) {
        String tipStr = context.getString(stringResId);
        toastWithBigBackground(context, tipStr);
    }

    /**
     * 由于安卓系统的限制 Toast 显示需要handler，所以只能在有looper的线程调用 ,这里做了逻辑控制，如果toast需要在子线程中调,用请在业务调用的子线程加上looper 并 执行loop
     * <p>
     * 大正方形半透明背景的Toast  屏幕中间显示  圆角 String 提示  仅显示内容
     *
     * @param context 上下文
     * @param tipStr  内容
     */

    public static void toastWithBigBackground(final Context context, final String tipStr) {
        if (Looper.myLooper() == null) {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doToastWithBigBackground(context, tipStr);
                    }
                });
            }
        } else {
            doToastWithBigBackground(context, tipStr);
        }
    }

    private static void doToastWithBigBackground(Context context, String tipStr) {
        TextView contentView = (TextView) View.inflate(context.getApplicationContext(), R.layout.activity_main, null);
        contentView.setText(tipStr);
        Toast bigBgTextToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        bigBgTextToast.setGravity(Gravity.CENTER, 0, 0);
        bigBgTextToast.setView(contentView);
        bigBgTextToast.show();
    }


    /**
     * 感叹号  大正方形半透明背景的Toast  屏幕中间显示   网络未连接 toast
     */
    public static void toastCustomView(Context context, int time) {
        toastCustomView(context, -1, null, time);
    }

    /**
     * 感叹号  大正方形半透明背景的Toast  屏幕中间显示   网络未连接 toast
     */
    public static void toastCustomView(Context context, int centerImgId, String toastStr, int time) {
        baseToast(context, "phone_custom_view_toast_template_widget", 0, centerImgId, "phone_custom_toast_img", toastStr, "phone_custom_toast_text", time, Gravity.CENTER, 0, 0);
    }

    /**
     * 不透明  黑色小长方形  toast
     * 原来的 UIUtils.toastCustomView3
     */
    public static void littleBlackBottomToast(Context context, String content, int duration, int nGravity, int xOffset, int yOffset) {
        baseToast(context, "toast_recommend_video_remove_tip", 0, 0, "", content, "content", duration, nGravity, xOffset, yOffset);
    }

    /**
     * toast 新增加 duration 参数，目前仅支持 duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG
     *
     * @param context
     * @param msg
     * @param duration
     */
    public static void toast(final Context context, final Object msg, final int duration) {
        if (Looper.myLooper() == null) {
            Handler sHandler = new Handler(Looper.getMainLooper());
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    realToast(context, msg, duration);
                }
            });
        } else {
            realToast(context, msg, duration);
        }
    }

    private static void realToast(Context context, Object msg, int duration) {
        if (null == context) {
            return;
        }
        if (!(duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG)) {
            duration = Toast.LENGTH_LONG;
        }
        Toast textToast = Toast.makeText(context.getApplicationContext(), "", duration);
        try {
            if (msg instanceof Integer) {
                textToast.setText(((Integer) msg));
                textToast.show();
            } else if (msg instanceof ImageView) {
                textToast.setView((ImageView) msg);
                textToast.setGravity(Gravity.CENTER, 0, 0);
                textToast.show();
            } else {
                textToast.setText(String.valueOf(msg));
                textToast.show();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Vip下载提示
     */
    public static void toastCustomViewVipDownload(Context context, int offsetx, int offsety) {
        baseToast(context, "qiyi_sdk_player_model_download_vip_toast_layout_widget", 0, 0, "", "", "", Toast.LENGTH_LONG, Gravity.BOTTOM, offsetx, offsety);
    }

    /***
     * 由于安卓系统的限制 Toast 显示需要handler，所以只能在有looper的线程调用 ,这里做了逻辑控制，如果toast需要在子线程中调,用请在业务调用的子线程加上looper 并 执行loop
     */
    public static void baseToast(final Context context, final String baseViewName, final int backGroundImageId, final int centerImageId, final String centerImageName, final String content, final String contentResId, final int duration, final int nGravity, final int xOffset, final int yOffset) {
        if (Looper.myLooper() == null) {
            Handler sHandler = new Handler(Looper.getMainLooper());
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    doBaseToast(context, baseViewName, backGroundImageId, centerImageId, centerImageName, content, contentResId, duration, nGravity, xOffset, yOffset);
                }
            });
        } else {
            doBaseToast(context, baseViewName, backGroundImageId, centerImageId, centerImageName, content, contentResId, duration, nGravity, xOffset, yOffset);
        }
    }

    /**
     * @param context
     * @param baseViewName      toast 背景view
     * @param backGroundImageId toast 背景imageId
     * @param centerImageId     toast 文字上方图片Id
     * @param content           toast 文字内容
     * @param contentResId      toast 文字资源Id
     * @param duration          toast 显示时长
     * @param nGravity          toast 位置
     * @param xOffset           toast x偏移量  正值 向右
     * @param yOffset           toast y偏移量   正值向下
     */

    private static void doBaseToast(Context context, String baseViewName, int backGroundImageId, int centerImageId, String centerImageName, String content, String contentResId, int duration, int nGravity, int xOffset, int yOffset) {
//        if (null == context) {
//            return;
//        }
//
//        Context cxt = context.getApplicationContext();
//        if (baseToast == null) {
//            baseToast = new Toast(cxt);
//        }
//
//        View baseView = LayoutInflater.from(cxt).inflate(R.layout.activity_main, null);
//        //设置显示文字
//        if (baseView != null && !TextUtils.isEmpty(contentResId) && !TextUtils.isEmpty(content)) {
//        }
//        // 如果含有中心背景图片的ID 那么就设置中心图片的背景
//        if (centerImageId > 0 && !TextUtils.isEmpty(centerImageName)) {
//            baseView.findViewById(ResourcesTool.getResourceIdForID(centerImageName)).setBackgroundResource(centerImageId);
//        }
//        // 如果含有整体背景图片的ID 那么就设置整体图片的背景
//        if (backGroundImageId > 0) {
//            baseView.setBackgroundResource(backGroundImageId);
//        }
//        baseToast.setView(baseView);
//        baseToast.setGravity(nGravity, xOffset, yOffset);
//        baseToast.setDuration(duration);
//        baseToast.show();
    }

}
