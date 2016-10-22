package com.joker.allenmp3.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Administrator on 2016/9/12.
 */
public class PlayerState {
    public static final String STATEA = "MODE_LOOP";
    public static final String STATEB = "MODE_ONE";
    public static final String STATEC = "MODE_SHUFFLE";
    /**
     * 格式化时间，将毫秒转换为分:秒格式
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    //String id 为专辑id 获得专辑图片
    public  static String imgGet(Context context, String id){
        String[] mediaColumns1 = new String[] {MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.ALBUM};
        String album_art = " ";
        Cursor cursor1 = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, mediaColumns1,
                "_id=?", new String[]{id} ,
                null);
        if (cursor1 != null) {
            cursor1.moveToFirst();
            album_art =  cursor1.getString(0);
            cursor1.close();
        }
        return album_art==null?"":album_art;
    }
}
