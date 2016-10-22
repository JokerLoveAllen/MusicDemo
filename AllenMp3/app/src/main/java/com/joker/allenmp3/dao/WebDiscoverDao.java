package com.joker.allenmp3.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.joker.allenmp3.database.DatabaseHelper;
import com.joker.allenmp3.entity.WebDiscoverCache;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class WebDiscoverDao {
    private Context context;
    private Dao<WebDiscoverCache,Integer> MusicDao;
    public DatabaseHelper helper;
    public WebDiscoverDao(Context context){
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            MusicDao = helper.getDao(WebDiscoverCache.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //加载新闻
    public void addDiscover(WebDiscoverCache nc){
        try {
            MusicDao.createOrUpdate(nc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //查找缓存
    public List<WebDiscoverCache> searchAll(){
        try {
            return MusicDao.queryForAll();
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
