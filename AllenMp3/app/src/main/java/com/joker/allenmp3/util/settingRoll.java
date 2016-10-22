package com.joker.allenmp3.util;

import android.content.ContentValues;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

import com.joker.allenmp3.entity.Music;

import java.io.File;

/**
 * Created by Administrator on 2016/9/14.
 */
public class settingRoll {

    public static void getRINGTONE(Context context, Music music) {
        //歌曲路径
        String path = music.getData();
        File file = new File(path.substring(4));
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, path);
        values.put(MediaStore.MediaColumns.TITLE, file.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(
                file.getAbsolutePath());
        Uri newUri = context.getContentResolver().insert(uri, values);
        RingtoneManager.setActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_RINGTONE, newUri);//可设置为闹钟，响铃，通知
    }
}
