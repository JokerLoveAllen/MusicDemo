package com.joker.allenmp3.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.joker.allenmp3.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderUtil {

	private static DisplayImageOptions options =new DisplayImageOptions.Builder()
			                            .showImageOnLoading(R.mipmap.banner_01)
										.showImageOnFail(R.mipmap.banner_01)
										.showImageForEmptyUri(R.mipmap.banner_01)
										.cacheInMemory(true)
										.cacheOnDisk(true)
										.bitmapConfig(Bitmap.Config.RGB_565)
										.resetViewBeforeLoading(true)
										.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
										.displayer(new FadeInBitmapDisplayer(200))
										.build();
	private static DisplayImageOptions optionsBig = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.mipmap.banner_02)
			.showImageOnFail(R.mipmap.banner_02)
			.showImageForEmptyUri(R.mipmap.banner_02)
			.cacheInMemory(true).cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.displayer(new FadeInBitmapDisplayer(200)).build();
	private static DisplayImageOptions headerOptioins = new DisplayImageOptions.Builder()
										.showImageOnLoading(R.mipmap.default_head)
										.showImageOnFail(R.mipmap.default_head)
										.showImageForEmptyUri(R.mipmap.default_head)
										.cacheInMemory(true)
										.cacheOnDisk(true)
										.bitmapConfig(Bitmap.Config.RGB_565)
										.resetViewBeforeLoading(true)
										.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
										.displayer(new FadeInBitmapDisplayer(200))
										.build();

	public static void display(String uri,ImageView imageView){
		ImageLoader.getInstance().displayImage(uri, imageView, options);
	}
	public static void displayBigPhoto(String uri,ImageView imageView){

		ImageLoader.getInstance().displayImage(uri, imageView, optionsBig);
	}

}
