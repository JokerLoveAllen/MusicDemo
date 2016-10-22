package com.joker.allenmp3.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.joker.allenmp3.database.DatabaseHelper;
import com.joker.allenmp3.entity.WebMainCache;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class WebMainDao {
    private Context context;
    private Dao<WebMainCache,Integer> mDao;
    public DatabaseHelper helper;
    public WebMainDao(Context context){
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            mDao = helper.getDao(WebMainCache.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //加载新闻
    public void addMain(WebMainCache nc){
        try {
            mDao.createOrUpdate(nc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //查找缓存
    public List<WebMainCache> searchAll() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){
        try {
            mDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
