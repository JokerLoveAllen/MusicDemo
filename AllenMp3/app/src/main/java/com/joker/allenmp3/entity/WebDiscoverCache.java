package com.joker.allenmp3.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/9/21.
 */
@DatabaseTable(tableName = "DiscoverCache")
public class WebDiscoverCache {
    @DatabaseField
    public String data;
    @DatabaseField(id = true)
    public int id;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
