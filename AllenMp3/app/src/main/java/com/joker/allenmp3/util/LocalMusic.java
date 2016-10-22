package com.joker.allenmp3.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.joker.allenmp3.entity.Music;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class LocalMusic {
    private Context context;
    private List<Music> musicList;
    private SimpleDateFormat format;
    private Date date;
    private Music music;
    public LocalMusic(Context context){
        this.context = context;
        format = new SimpleDateFormat("mm:ss");
        musicList = new ArrayList<>();
        music = new Music();
    }
    public List<Music> readMusic(){
        String str[]  ={
                MediaStore.Audio.Media.DISPLAY_NAME
                ,MediaStore.Audio.Media.ARTIST
                ,MediaStore.Audio.Media.DURATION
                ,MediaStore.Audio.Media.ALBUM
                ,MediaStore.Audio.Media.DATA
                ,MediaStore.Audio.Media.ALBUM_ID

        };
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        str
                        ,"duration > ?"
                        ,new String[]{"3000"},null);
        while (cursor.moveToNext()){
            music = new Music();//每一个都要新建一个对象。
            music.setDisplayName(cursor.getString(0));
            if (cursor.getString(1).equals("<unknown>")){
                music.setArtist("未知");
            } else {
                music.setArtist(cursor.getString(1));
            }
            date = new Date(Long.parseLong(cursor.getString(2)));
            music.setDuration(format.format(date));
            music.setAlbum(cursor.getString(3));
            music.setData(cursor.getString(4));
            String AlbumId = PlayerState.imgGet(context,cursor.getString(5));
            music.setImageUrl(AlbumId == null?"":AlbumId);
            musicList.add(music);
        }
        return musicList;
    }
}
