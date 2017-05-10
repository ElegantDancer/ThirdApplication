package com.qiyi.video;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;

public class ShareUtils {
	
    public static final int CPU_COUNT = getCPUCount();

	
	public static boolean isNeedShowVV(String vv) {
		if (TextUtils.isEmpty(vv)) {
			return false;
		}
		if (vv.endsWith("亿")) {
			return true;
		}
		if (vv.endsWith("万")) {
			try {
				float f = Float.valueOf(vv.substring(0, vv.length() - 1));
				if ((int) f > 100) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isVVIsZero(String vv) {
		if (TextUtils.isEmpty(vv)) {
			return true;
		}
		
		if ("0".equals(vv)) {
			return true;
		}
		
		return false;
	}
	
	 public static boolean isEmpty(String str) {
	        if (null == str || "".equals(str) || "null".equals(str)) {
	            return true;
	        } else {
	            if (str.length() > 4) {
	                return false;
	            } else {
	                return str.equalsIgnoreCase("null");
	            }

	        }
	    }
	
    
    /**
     * 创建唯一标识
     *
     * @param type
     * @return 返回唯一标识
     */
    public static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    public static int getCPUCount() {
        int ret = Runtime.getRuntime().availableProcessors();
        ret = ret < 2 ? 2 : ret > 8 ? 8 : ret;
        return ret;
    }
    
    /**
	 *  得到如何要求的缩略图  和  分享出去的图片
	 * @param bitmap
	 * @param quality 图片质量  0 ~ 100
	 * @param maxSize 图片的最大size
     * @return
     */
	public static byte[] getBitmapBytes(Bitmap bitmap, int quality, double maxSize){
		// 取得突破字节流
		byte[] tempbytes = bmpToByteArray(bitmap, quality, false);
		// 取得突破大小
		int size = tempbytes.length / 1024;

		// 图片大小符合要求
		if (size < maxSize) {
			bitmap.recycle();
		}else {
			// 获取bitmap大小 是允许大小的多少倍
			double i = size / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
			// tempbitmap是新生成的图片，需要释放。
			tempbytes = bmpToByteArray(bitmap, quality, true);
		}

		return tempbytes;
	}
	
	/**
	 *  
	 * @param bmp 需要转换的图片
	 * @param size 压缩比率
	 * @param needRecycle 是否需要回收
	 * @return  byte 转换后的数据流
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp, int size, final boolean needRecycle)
	{

		if (bmp == null)
		{
			return null;
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, size % 100, output);
		if (needRecycle)
		{
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try
		{
			output.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 
	 * @param bgimage 需要转换的图片
	 *             
	 * @param newWidth 新的宽度
	 *             
	 * @param newHeight 新的高度
	 *           
	 * @return 转换后的图片
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight)
	{
		if (bgimage == null)
		{
			return null;
		}

		float width = bgimage.getWidth();
		float height = bgimage.getHeight();

		Matrix matrix = new Matrix();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}
	
	
	/**
	 * Drawable convert for Bitmap.
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable)
	{
		return null == drawable ? null : ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
	}

}
