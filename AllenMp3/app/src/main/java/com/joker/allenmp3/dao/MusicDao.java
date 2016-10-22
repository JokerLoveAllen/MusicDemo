package com.joker.allenmp3.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.joker.allenmp3.database.DatabaseHelper;
import com.joker.allenmp3.entity.Music;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class MusicDao {
    private Context context;
    private Dao<Music,Integer> MusicDao;
    public DatabaseHelper helper;
    public MusicDao(Context context){
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            MusicDao = helper.getDao(Music.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //加载新闻
    public void addMusic(Music nc){
        try {
            MusicDao.createOrUpdate(nc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //查找缓存
    public List<Music> searchAll(){
        try {
            return MusicDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //查找一个频道id的数据
    public List<Music> selectMusic(String chiId){
        try {
            return  MusicDao.queryBuilder()
                    .where()
                    .eq("musicId",chiId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){
        try {
            MusicDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
