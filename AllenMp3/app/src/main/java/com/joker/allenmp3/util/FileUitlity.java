package com.joker.allenmp3.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUitlity {


	public final static String USER_HAED="head";

	public static String ROOT_CACHE;
	public static FileUitlity instance = null;
	private FileUitlity() {
	}
	public static FileUitlity getInstance(Context context,String root) {
		if (instance == null) {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				ROOT_CACHE = (Environment.getExternalStorageDirectory() + "/"
						+ root + "/");
			} else {
				ROOT_CACHE = (context.getFilesDir().getAbsolutePath() + "/"+root+"/");
			}
			File dir = new File(ROOT_CACHE);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			instance = new FileUitlity();
		}
		return instance;
	}
	public File makeDir(String dir) {
		File fileDir = new File(ROOT_CACHE + dir);
		if (fileDir.exists()) {
			return fileDir;
		} else {
			fileDir.mkdirs();
			return fileDir;
		}
	}

	
}