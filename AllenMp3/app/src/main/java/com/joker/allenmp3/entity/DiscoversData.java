package com.joker.allenmp3.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chen on 2016/4/20.
 */
public class DiscoversData {

    /**
     * title : 侯康：有温度的声音
     * description : 在音乐中找一片净土，让心扎根长出自由。
     * image : http://cdn.wawa.fm/group1/M00/02/4E/Cvtf3VcUmz6AWBeSAADE6rOmSRI291.jpg
     * id : 995957322
     * musiccode : 20160331052705478
     * trackname : 明天是否还能见到你
     * play_count : 1501
     */

    private MusicianBean musician;
    /**
     * mid : 276
     * mcode : Q20160419210
     * resourcecode : 201408250605,20160315043306,201511044009303,201511041138276,20160128125152,201408250783,201408250505,201408250846,201408251032,20160317044316,
     * mnum : 131
     * mname : 人人人人
     * mdesc : 一转眼又来到了
     那个温暖和拥挤的地方
     什么都不必想
     紧张、自然
     脑袋是别人的
     动作是前辈的


     封面 //Mario Purisic
     * mphoto : http://cdn.wawa.fm/group1/M00/02/50/Cvtf3VcV6HSAFdsiAAKdgl_BQak966.jpg
     * play_count : 1906
     */

    private AlbumBean album;
    /**
     * id : 117
     * image : http://cdn.wawa.fm/group1/M00/02/42/Cvtf3VcMifmAOxW0AABX_rHpBI8936.jpg
     * flag : 0
     * link : http://wawa.fm/webview/article.html?type=banner&id=117
     * create_date : 1460456070
     */

    private List<AdBean> ad;

    public MusicianBean getMusician() {
        return musician;
    }

    public void setMusician(MusicianBean musician) {
        this.musician = musician;
    }

    public AlbumBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumBean album) {
        this.album = album;
    }

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public static class MusicianBean {
        private String title;
        private String description;
        private String image;
        private String id;
        private String musiccode;
        private String trackname;
        private String play_count;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMusiccode() {
            return musiccode;
        }

        public void setMusiccode(String musiccode) {
            this.musiccode = musiccode;
        }

        public String getTrackname() {
            return trackname;
        }

        public void setTrackname(String trackname) {
            this.trackname = trackname;
        }

        public String getPlay_count() {
            return play_count;
        }

        public void setPlay_count(String play_count) {
            this.play_count = play_count;
        }
    }

    public static class AlbumBean implements Serializable {
        private String mid;
        private String mcode;
        private String resourcecode;
        private String mnum;
        private String mname;
        private String mdesc;
        private String mphoto;
        private String play_count;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMcode() {
            return mcode;
        }

        public void setMcode(String mcode) {
            this.mcode = mcode;
        }

        public String getResourcecode() {
            return resourcecode;
        }

        public void setResourcecode(String resourcecode) {
            this.resourcecode = resourcecode;
        }

        public String getMnum() {
            return mnum;
        }

        public void setMnum(String mnum) {
            this.mnum = mnum;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMdesc() {
            return mdesc;
        }

        public void setMdesc(String mdesc) {
            this.mdesc = mdesc;
        }

        public String getMphoto() {
            return mphoto;
        }

        public void setMphoto(String mphoto) {
            this.mphoto = mphoto;
        }

        public String getPlay_count() {
            return play_count;
        }

        public void setPlay_count(String play_count) {
            this.play_count = play_count;
        }
    }

    public static class AdBean {
        private String id;
        private String image;
        private String flag;
        private String link;
        private String create_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
    }
}
