package com.joker.allenmp3.entity;

import java.util.List;

/**
 * Created by Chen on 2016/4/13.
 */
public class SplashData {

    /**
     * err_code : 0
     * msg :
     * data : {"img_host":"http://7xkszy.com2.z0.glb.qiniucdn.com","mp3_host":"http://luoo-mp3.kssws.ks-cdn.com","website_host":"http://www.luoo.net","version":{"upToDate":"否","latestVersion":"5.3.0","description":"新增：\r\n- 在线视频栏目，给你活生生的音乐人！\r\n- 单曲列表，支持音乐风格筛选、热门排序\r\n- 落网作者，点进头像一次性看文章作者的所有作品\r\n\r\n优化：\r\n- 文章详情，文中插入歌曲直接听起来\r\n- 缓存页面有播放器显示了，妈妈再也不用担心我还要返回到首页才能暂停\r\n\r\n修复\r\n- 定时关闭无反应","description_en":"Added:\r\n- Video in discover. Give you a live musician！\r\n- Single list. Add style of music and top sorting.\r\n- Author. Click avatar to read all the articles.\r\n\r\nModified:\r\n- Add songs in paragraph in article.\r\n- There are player display in cache page\r\n\r\nFixed:\r\n- Timer didn\u2019t work","updateDate":"2016-04-13 08:35:55","downloadUrl":"http://luoo-dl.kssws.ks-cdn.com/luoo_fm/Luoo-latest.apk","lyric_status":"0"},"push":{"push_comment":"1","push_at":"1","push_vote":"1","push_msg":"1","push_follow":"1"},"had_unread_msg":"0","vol_ids":["833","751","657","651","642","633"],"single_ids":["12304","12448","11433","12100","11931","11938","362","11594","11576","11577","11574","11573","11079"],"essay_ids":["680"],"place_ids":[],"event_ids":[],"startup":{"id":"308","img":"http://7xkszy.com2.z0.glb.qiniucdn.com/site/201604/570cd4b1e9953.jpg","title":"今年，和我们一起出发！","end":"2016-04-24"},"cache_number":"50","media_hosts":[{"id":"1","host":"http://luoo-mp3.kssws.ks-cdn.com","status":"1"}]}
     */

    private int err_code;
    private String msg;
    /**
     * img_host : http://7xkszy.com2.z0.glb.qiniucdn.com
     * mp3_host : http://luoo-mp3.kssws.ks-cdn.com
     * website_host : http://www.luoo.net
     * version : {"upToDate":"否","latestVersion":"5.3.0","description":"新增：\r\n- 在线视频栏目，给你活生生的音乐人！\r\n- 单曲列表，支持音乐风格筛选、热门排序\r\n- 落网作者，点进头像一次性看文章作者的所有作品\r\n\r\n优化：\r\n- 文章详情，文中插入歌曲直接听起来\r\n- 缓存页面有播放器显示了，妈妈再也不用担心我还要返回到首页才能暂停\r\n\r\n修复\r\n- 定时关闭无反应","description_en":"Added:\r\n- Video in discover. Give you a live musician！\r\n- Single list. Add style of music and top sorting.\r\n- Author. Click avatar to read all the articles.\r\n\r\nModified:\r\n- Add songs in paragraph in article.\r\n- There are player display in cache page\r\n\r\nFixed:\r\n- Timer didn\u2019t work","updateDate":"2016-04-13 08:35:55","downloadUrl":"http://luoo-dl.kssws.ks-cdn.com/luoo_fm/Luoo-latest.apk","lyric_status":"0"}
     * push : {"push_comment":"1","push_at":"1","push_vote":"1","push_msg":"1","push_follow":"1"}
     * had_unread_msg : 0
     * vol_ids : ["833","751","657","651","642","633"]
     * single_ids : ["12304","12448","11433","12100","11931","11938","362","11594","11576","11577","11574","11573","11079"]
     * essay_ids : ["680"]
     * place_ids : []
     * event_ids : []
     * startup : {"id":"308","img":"http://7xkszy.com2.z0.glb.qiniucdn.com/site/201604/570cd4b1e9953.jpg","title":"今年，和我们一起出发！","end":"2016-04-24"}
     * cache_number : 50
     * media_hosts : [{"id":"1","host":"http://luoo-mp3.kssws.ks-cdn.com","status":"1"}]
     */

    private DataBean data;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String img_host;
        private String mp3_host;
        private String website_host;
        /**
         * upToDate : 否
         * latestVersion : 5.3.0
         * description : 新增：
         - 在线视频栏目，给你活生生的音乐人！
         - 单曲列表，支持音乐风格筛选、热门排序
         - 落网作者，点进头像一次性看文章作者的所有作品

         优化：
         - 文章详情，文中插入歌曲直接听起来
         - 缓存页面有播放器显示了，妈妈再也不用担心我还要返回到首页才能暂停

         修复
         - 定时关闭无反应
         * description_en : Added:
         - Video in discover. Give you a live musician！
         - Single list. Add style of music and top sorting.
         - Author. Click avatar to read all the articles.

         Modified:
         - Add songs in paragraph in article.
         - There are player display in cache page

         Fixed:
         - Timer didn’t work
         * updateDate : 2016-04-13 08:35:55
         * downloadUrl : http://luoo-dl.kssws.ks-cdn.com/luoo_fm/Luoo-latest.apk
         * lyric_status : 0
         */

        private VersionBean version;
        /**
         * push_comment : 1
         * push_at : 1
         * push_vote : 1
         * push_msg : 1
         * push_follow : 1
         */

        private PushBean push;
        private String had_unread_msg;
        /**
         * id : 308
         * img : http://7xkszy.com2.z0.glb.qiniucdn.com/site/201604/570cd4b1e9953.jpg
         * title : 今年，和我们一起出发！
         * end : 2016-04-24
         */

        private StartupBean startup;
        private String cache_number;
        private List<String> vol_ids;
        private List<String> single_ids;
        private List<String> essay_ids;
        private List<?> place_ids;
        private List<?> event_ids;
        /**
         * id : 1
         * host : http://luoo-mp3.kssws.ks-cdn.com
         * status : 1
         */

        private List<MediaHostsBean> media_hosts;

        public String getImg_host() {
            return img_host;
        }

        public void setImg_host(String img_host) {
            this.img_host = img_host;
        }

        public String getMp3_host() {
            return mp3_host;
        }

        public void setMp3_host(String mp3_host) {
            this.mp3_host = mp3_host;
        }

        public String getWebsite_host() {
            return website_host;
        }

        public void setWebsite_host(String website_host) {
            this.website_host = website_host;
        }

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public PushBean getPush() {
            return push;
        }

        public void setPush(PushBean push) {
            this.push = push;
        }

        public String getHad_unread_msg() {
            return had_unread_msg;
        }

        public void setHad_unread_msg(String had_unread_msg) {
            this.had_unread_msg = had_unread_msg;
        }

        public StartupBean getStartup() {
            return startup;
        }

        public void setStartup(StartupBean startup) {
            this.startup = startup;
        }

        public String getCache_number() {
            return cache_number;
        }

        public void setCache_number(String cache_number) {
            this.cache_number = cache_number;
        }

        public List<String> getVol_ids() {
            return vol_ids;
        }

        public void setVol_ids(List<String> vol_ids) {
            this.vol_ids = vol_ids;
        }

        public List<String> getSingle_ids() {
            return single_ids;
        }

        public void setSingle_ids(List<String> single_ids) {
            this.single_ids = single_ids;
        }

        public List<String> getEssay_ids() {
            return essay_ids;
        }

        public void setEssay_ids(List<String> essay_ids) {
            this.essay_ids = essay_ids;
        }

        public List<?> getPlace_ids() {
            return place_ids;
        }

        public void setPlace_ids(List<?> place_ids) {
            this.place_ids = place_ids;
        }

        public List<?> getEvent_ids() {
            return event_ids;
        }

        public void setEvent_ids(List<?> event_ids) {
            this.event_ids = event_ids;
        }

        public List<MediaHostsBean> getMedia_hosts() {
            return media_hosts;
        }

        public void setMedia_hosts(List<MediaHostsBean> media_hosts) {
            this.media_hosts = media_hosts;
        }

        public static class VersionBean {
            private String upToDate;
            private String latestVersion;
            private String description;
            private String description_en;
            private String updateDate;
            private String downloadUrl;
            private String lyric_status;

            public String getUpToDate() {
                return upToDate;
            }

            public void setUpToDate(String upToDate) {
                this.upToDate = upToDate;
            }

            public String getLatestVersion() {
                return latestVersion;
            }

            public void setLatestVersion(String latestVersion) {
                this.latestVersion = latestVersion;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDescription_en() {
                return description_en;
            }

            public void setDescription_en(String description_en) {
                this.description_en = description_en;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getDownloadUrl() {
                return downloadUrl;
            }

            public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
            }

            public String getLyric_status() {
                return lyric_status;
            }

            public void setLyric_status(String lyric_status) {
                this.lyric_status = lyric_status;
            }
        }

        public static class PushBean {
            private String push_comment;
            private String push_at;
            private String push_vote;
            private String push_msg;
            private String push_follow;

            public String getPush_comment() {
                return push_comment;
            }

            public void setPush_comment(String push_comment) {
                this.push_comment = push_comment;
            }

            public String getPush_at() {
                return push_at;
            }

            public void setPush_at(String push_at) {
                this.push_at = push_at;
            }

            public String getPush_vote() {
                return push_vote;
            }

            public void setPush_vote(String push_vote) {
                this.push_vote = push_vote;
            }

            public String getPush_msg() {
                return push_msg;
            }

            public void setPush_msg(String push_msg) {
                this.push_msg = push_msg;
            }

            public String getPush_follow() {
                return push_follow;
            }

            public void setPush_follow(String push_follow) {
                this.push_follow = push_follow;
            }
        }

        public static class StartupBean {
            private String id;
            private String img;
            private String title;
            private String end;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }
        }

        public static class MediaHostsBean {
            private String id;
            private String host;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
