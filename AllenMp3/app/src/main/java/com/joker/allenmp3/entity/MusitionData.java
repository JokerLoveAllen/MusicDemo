package com.joker.allenmp3.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chen on 2016/4/20.
 */
public class MusitionData {

    /**
     * id : 995957322
     * title : 侯康：有温度的声音
     * cover_url : http://cdn.wawa.fm/group1/M00/02/4E/Cvtf3VcUmz6AWBeSAADE6rOmSRI291.jpg
     * musiccode : 20160331052705478
     * songname : 明天是否还能见到你
     * track_id : 21546
     * playcount : 1608
     */

    private List<ListBean> list;
    private List<ListBean> random;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ListBean> getRandom() {
        return random;
    }

    public void setRandom(List<ListBean> random) {
        this.random = random;
    }

    public static class ListBean implements Serializable {
        private String id;
        private String title;
        private String cover_url;
        private String musiccode;
        private String songname;
        private String track_id;
        private String playcount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getMusiccode() {
            return musiccode;
        }

        public void setMusiccode(String musiccode) {
            this.musiccode = musiccode;
        }

        public String getSongname() {
            return songname;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getTrack_id() {
            return track_id;
        }

        public void setTrack_id(String track_id) {
            this.track_id = track_id;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }
    }
}
