package com.qiyi.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.sina.weibo.sdk.ApiUtils;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;

import java.io.File;

/**
 * Created by zhaozhenzhen on 2016/8/22.
 */
public class ShareSina {

	private final Activity mActivity;
	private final IWeiboShareAPI shareApi;

	public ShareSina(Activity activity) {
		this.mActivity = activity;
		String SINA_SHARE_APP_KEY;
		if (mActivity.getPackageName().equals("tv.pps.mobile")) {
			SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_PPS;
		} else {
			SINA_SHARE_APP_KEY = ShareConstans.SINA_SHARE_APP_KEY_IQY;
		}
		// 注册到sina App
		shareApi = WeiboShareSDK.createWeiboAPI(mActivity, SINA_SHARE_APP_KEY);
		shareApi.registerApp();
	}

	public void share(ShareBean shareBean) {
		if (null == mActivity) {
			return;
		}
		// bitmap图片控制器初始化
		switch (shareBean.getShareType()) {
		case 0:
			shareVideo(mActivity, shareBean);
			break;
		case 1:
//			shareWebPage(mActivity, shareBean);
			shareTextAndImage(mActivity, shareBean);
			break;
		case 2:
			shareText(mActivity, shareBean);
			break;
		case 3:
			shareImage(mActivity, shareBean);
			break;
		case 4:
			shareGif(mActivity, shareBean);
			break;
		default:
			break;
		}
		mActivity.finish();
	}
	
	
	private void shareImage(Activity activity, ShareBean shareBean){
		
		if(isSinaSupportAPI(activity)){
			
			TextObject textObject = new TextObject();
			textObject.text = getText(shareBean);
			
			String path = "";
			ImageObject imageObject = new ImageObject();
			imageObject.imageData = getImageBytes(path);
			
			if(imageObject.imageData == null){
				return;
			}
				
			shareMutiMessage(activity, imageObject, textObject, "Image");;
		}else {
			
			String path = "";
		
			ImageObject imageObject = new ImageObject();
			imageObject.imageData = getImageBytes(path);
			if(imageObject.imageData == null){
				return;
			}
				
			shareSingleMessage(activity, imageObject, "Image");
		}
	}
	
	private void shareGif(Activity activity, ShareBean shareBean){
		
		if(isSinaSupportAPI(activity)){
			TextObject textObject = new TextObject();
			textObject.text = getText(shareBean);
			
			String path = "";
			File file = new File(path);
			if(file.exists()){
				ImageObject imageObject = new ImageObject();
				imageObject.imagePath = path;
				
				shareMutiMessage(activity, imageObject, textObject, "Gif");
			}else {
				return;
			}
		}else {
			String path = "";
			File file = new File(path);
			if(file.exists()){
				ImageObject imageObject = new ImageObject();
				imageObject.imagePath = path;
				shareSingleMessage(activity, imageObject, "Gif");
			}else{
				return;
			}
		}
	}
	
	
	private void shareSingleMessage(Activity activity, ImageObject imageObject, String transType){
		WeiboMessage singleMessage = new WeiboMessage();
		singleMessage.mediaObject = imageObject;
			
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// 用transaction表示一个唯一的请求
		request.transaction = ShareUtils.buildTransaction(transType);
		request.message = singleMessage;
		sendRequestMessage(activity, request, transType);
	}
	
	
	private void shareMutiMessage(Activity activity, ImageObject imageObject, TextObject textObject, String transType){
		WeiboMultiMessage multiMessage = new WeiboMultiMessage();
		multiMessage.imageObject = imageObject;
		multiMessage.textObject = textObject;
		
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction表示一个唯一的请求
		request.transaction = ShareUtils.buildTransaction(transType);
		request.multiMessage = multiMessage;
		sendRequestMessage(activity, request, transType);
	}
	
	private String getText(ShareBean bean){
		String text = bean.getTitle();
		text = text.length() < ShareConstans.SINA_TITLE_MAX_LENGTH ? text
				: text.substring(0, ShareConstans.SINA_TITLE_MAX_LENGTH);
		
		return text;
	}
	
	private byte[] getImageBytes(String path){
		File file = new File(path);
		if(file.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			return ShareUtils.getBitmapBytes(bitmap, 85, ShareConstans.SINA_SHARE_IMAGE_MAX_LENGTH);
		}
		return null;
	}

	private void shareText(Context context, ShareBean shareBean) {
		final String sendType = "text";
		if (isSinaSupportAPI(context)) {
			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			multiMessage.textObject = getTextObject(shareBean);
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction唯一表示一个请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = getTextObject(shareBean);
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
	}

	private TextObject getTextObject(ShareBean shareBean) {
		TextObject textObject = new TextObject();
		textObject.text = shareBean.getTitle();
		return textObject;
	}
	
	private void shareTextAndImage(final Context context, final ShareBean shareBean){
		
		if(null == context){
			return;
		}
		
		String title = shareBean.getTitle();
		String des = shareBean.getDesc();

		
		if(!des.contains("http")){
			des = des + shareBean.getUrl();
		}
		
		TextObject textObj = getTextObj(des);
		
//		sendTextAndImage(context, shareBean, textObj);
	}
	
	private TextObject getTextObj(String text){
		
		TextObject textObj = new TextObject();
		textObj.text = text;
		return textObj;
	}
	
//	private void sendTextAndImage(final Context context, final ShareBean shareBean, final TextObject textObj){
//
//		final Bitmap bitMap = ShareUtils.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId()));
//		String bitmapUrl = shareBean.getBitmapUrl();
//
//		if(!TextUtils.isEmpty(bitmapUrl)){
//
//			org.qiyi.basecore.imageloader.ImageLoader.getBitmapRawData(context, bitmapUrl, true, new org.qiyi.basecore.imageloader.ImageLoader.ImageListener() {
//	            @Override
//	            public void onSuccessResponse(Bitmap bitmap, String url, boolean isCached) {
//	    			sendTextImageToSina(context, bitmap, shareBean, textObj);
//	            }
//
//	            @Override
//	            public void onErrorResponse(int errorCode) {
//	    			sendTextImageToSina(context, bitMap, shareBean, textObj);
//	            }
//	        });
//
//		}else{
//			sendTextImageToSina(context, bitMap, shareBean, textObj);
//		}
//	}
	
	
	//如果没有 图片地址，则只发送 textObject的方法
	private void sendTextImageToSina(Context context, ShareBean shareBean, TextObject textObj){
		String sendType = "webpage";
		if (isSinaSupportAPI(context)) {

			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			multiMessage.textObject = textObj;
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction表示一个唯一的请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = textObj;
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
	}

	
	private void sendTextImageToSina(Context context, Bitmap bitmap, ShareBean shareBean, TextObject textObj){
		
		String sendType = "webpage";
		if (isSinaSupportAPI(context)) {

			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			multiMessage.mediaObject = getImageObj(bitmap);
			multiMessage.textObject = textObj;
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction表示一个唯一的请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = textObj;
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
		
	}
	
	
	private ImageObject getImageObj(Bitmap bitmap){
		
		ImageObject imageObj = new ImageObject();
		imageObj.imageData = ShareUtils.getBitmapBytes(bitmap, 85, ShareConstans.SINA_SHARE_BITMAP_MAX_LENGTH);
		return imageObj;
	}

	private void shareWebPage(final Context context, final ShareBean shareBean) {
		final String sendType = "webpage";
		if (context == null) {
			return;
		}
		final WebpageObject webpageObject = new WebpageObject();
		webpageObject.identify = Utility.generateGUID();
		// 获取webpage的URL
		webpageObject.actionUrl = shareBean.getUrl();

		String title = shareBean.getTitle();
		String des = shareBean.getDesc();

		webpageObject.title = title.length() < ShareConstans.SINA_TITLE_MAX_LENGTH ? title
				: title.substring(0, ShareConstans.SINA_TITLE_MAX_LENGTH);
		webpageObject.description = des.length() < ShareConstans.SINA_DES_MAX_LENGTH ? des
				: des.substring(0, ShareConstans.SINA_DES_MAX_LENGTH);
		// 因为新浪中显示webpage页面不友好 更改后，title会自动显示 所以将title更改为des
		
		webpageObject.title = des.length() < ShareConstans.SINA_DEFAULT_MAX_LENGTH ? des
				: des.substring(0, ShareConstans.SINA_DEFAULT_MAX_LENGTH);;
		
//		webpageObject.defaultText = webpageObject.title;
		String bitmapUrl = shareBean.getBitmapUrl();

		if (!TextUtils.isEmpty(bitmapUrl)) {

			sendShare(context, bitmapUrl, webpageObject, shareBean, sendType);
		}else{
//			onShareImagePrepared(webpageObject,
//					ShareUtils.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId())), context,
//					shareBean, sendType);
		}

	}

	private void sendShare(final Context context, String bitmapUrl, final WebpageObject webpageObject, final ShareBean shareBean, final String sendType){
//
//		final Bitmap bitMap = ShareUtils.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId()));
//		org.qiyi.basecore.imageloader.ImageLoader.getBitmapRawData(context, bitmapUrl, true, new org.qiyi.basecore.imageloader.ImageLoader.ImageListener() {
//            @Override
//            public void onSuccessResponse(Bitmap bitmap, String url, boolean isCached) {
//    			onShareImagePrepared(webpageObject, bitmap, context, shareBean, sendType);
//            }
//
//            @Override
//            public void onErrorResponse(int errorCode) {
//            	onShareImagePrepared(webpageObject,
//    					bitMap, context,
//    					shareBean, sendType);
//            }
//        });
	}
	
	
	private void sendShare(final Context context, String bitmapUrl,final VideoObject videoObject, final ShareBean shareBean, final String sendType){
		
//		org.qiyi.basecore.imageloader.ImageLoader.getBitmapRawData(context, bitmapUrl, true, new org.qiyi.basecore.imageloader.ImageLoader.ImageListener() {
//            @Override
//            public void onSuccessResponse(Bitmap bitmap, String url, boolean isCached) {
//    			onShareImagePrepared(videoObject, bitmap, context, shareBean, sendType);
//            }
//
//            @Override
//            public void onErrorResponse(int errorCode) {
//            	onShareImagePrepared(videoObject,
//    					ShareUtils.drawable2Bitmap(context.getResources().getDrawable(shareBean.getDfPicId())), context,
//    					shareBean, sendType);
//            }
//        });
	}

	private void shareVideo(final Context context, final ShareBean bean) {

		final String sendType = "video";
		if (null == context) {
			return;
		}
		final VideoObject videoObj = new VideoObject();
		videoObj.identify = Utility.generateGUID();

		// 设置播放地址url
		String actionUrl = bean.getUrl();
		if (TextUtils.isEmpty(actionUrl)) {
			videoObj.actionUrl = ShareConstans.getHtml5Host_URI();
		} else {
			videoObj.actionUrl = actionUrl;
		}

		// 设置视频标题
		String title = bean.getTitle();
		String des = bean.getDesc();

		videoObj.defaultText = videoObj.title;
		// 设置视频分享时显示的图片
		String bitmapUrl = bean.getBitmapUrl();
		if (!TextUtils.isEmpty(bitmapUrl)) {
			sendShare(context, bitmapUrl, videoObj, bean, sendType);
		}else{
//			onShareImagePrepared(videoObj,
//					ShareUtils.drawable2Bitmap(context.getResources().getDrawable(bean.getDfPicId())), context,
//					bean, sendType);
		}
	}

	private void sendRequestMessage(Context context, BaseRequest request, String sendType) {
		// 准备数据，启动分享activity
		if (shareApi.sendRequest((Activity) context, request)) {

		} else {

		}
	}

	private void onShareImagePrepared(BaseMediaObject mediaObject, Bitmap bitmap, Context context, ShareBean shareBean,
			String sendType) {
		if (null == mediaObject || null == bitmap || null == context || null == shareBean) {
			return;
		}

		// 对分享的imageObject照片要求和缩略图不一样
		ImageObject imageObject = null;
		if (shareBean.getShareType() == 1) {
			imageObject = new ImageObject();
			imageObject.imageData = ShareUtils.getBitmapBytes(bitmap, 85, ShareConstans.SINA_BITMAP_MAX_LENTH);
		}

		mediaObject.thumbData = ShareUtils.getBitmapBytes(bitmap, 25, ShareConstans.SINA_BITMAP_MAX_LENTH);
		/**
		 * 先进行API的判断
		 */
		if (isSinaSupportAPI(context)) {

			WeiboMultiMessage multiMessage = new WeiboMultiMessage();
			// 修复微博webpage页面分享出去后，没有缩略图的问题
			if (null != imageObject && shareBean.getShareType() == 1) {
				multiMessage.imageObject = imageObject;
				// ImageObject imageObject = new ImageObject();
				// imageObject.setImageObject(bitmap);
				// multiMessage.imageObject = imageObject;
			}
			multiMessage.mediaObject = mediaObject;
			// 初始化一个request
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			// 用transaction表示一个唯一的请求
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.multiMessage = multiMessage;
			sendRequestMessage(context, request, sendType);

		} else {
			WeiboMessage message = new WeiboMessage();
			message.mediaObject = mediaObject;
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = ShareUtils.buildTransaction(sendType);
			request.message = message;
			sendRequestMessage(context, request, sendType);
		}
	}

	/**
	 * 检查是否可以同时支持发送 文本、图片以及 （网页、视频、音频）中的一种 supportApi >= 10351
	 */
	private boolean isSinaSupportAPI(Context context) {
		return null != context && shareApi.getWeiboAppSupportAPI() >= ApiUtils.BUILD_INT_VER_2_2;
	}
}