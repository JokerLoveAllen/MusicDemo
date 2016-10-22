package com.joker.allenmp3.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chen on 2016/4/13.
 */
public class ForumData {

    private int err_code;
    private String msg;
    /**
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
        private int pages;
        /**
         * post_id : 35799
         * status : 1
         * attachs_id : 10831
         * content : #这是一个遗憾的故事
         * uid : 936471
         * create_time : 1460396020
         * update_time : 1460481334
         * count : 11
         * vote_count : 9
         * comment_count : 17
         * post_img : ["http://7xkszy.com2.z0.glb.qiniucdn.com/forum_posts/201604/570bdfe9b09dd.jpg?imageView2/1/w/700/h/700"]
         * post_audio : [{"source_id":"1769167647","site":1,"song_name":"说谎","album":"感官/世界","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/3510481386667071.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460222941.mp3"},{"source_id":"2088666","site":1,"song_name":"伯乐","album":"神秘嘉宾","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/1690341386667416.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460223001.mp3"},{"source_id":"2088671","site":1,"song_name":"心有林夕","album":"神秘嘉宾","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/1690341386667416.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1458573121.mp3"},{"source_id":"1769167648","site":1,"song_name":"心酸","album":"感官/世界","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/3510481386667071.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1451800321.mp3"},{"source_id":"1770182611","site":1,"song_name":"想自由","album":"美妙生活","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/4386281386439629.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460223121.mp3"},{"source_id":"1770182612","site":1,"song_name":"我总是一个人在练习一个人","album":"美妙生活","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/4386281386439629.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460223181.mp3"},{"source_id":"1770182615","site":1,"song_name":"拥有","album":"美妙生活","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/4386281386439629.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460223241.mp3"},{"source_id":"1771093946","site":1,"song_name":"傻子","album":"大小说家","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/5161021340071211.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1452615361.mp3"},{"source_id":"1771093941","site":1,"song_name":"浪费","album":"大小说家","artist":"林宥嘉","cover":"http://pic.xiami.net/images/album/img17/23517/5161021340071211.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1459864981.mp3"},{"source_id":"1774748234","site":1,"song_name":"如果我变成一首歌","album":"杜拉拉追婚记 电影原声带","artist":"原声带","cover":"http://pic.xiami.net/images/album/img23/78523/21001978061449144955.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1460223301.mp3"},{"source_id":"1775015432","site":1,"song_name":"兜圈","album":"必娶女人 电视原声带","artist":"原声带","cover":"http://pic.xiami.net/images/album/img17/23517/21002283261447850082.jpg@!c-80-80","url":"http://luoo-mp3.kssws.ks-cdn.com/xiami/1452577321.mp3"}]
         * user_avatar : http://7xkszy.com2.z0.glb.qiniucdn.com/pics/avatars/u9364711460221531.jpg?imageView2/1/w/128/h/128
         * avatar : http://7xkszy.com2.z0.glb.qiniucdn.com/pics/avatars/u9364711460221531.jpg?imageView2/1/w/128/h/128
         * user_name : Jan1cehu
         * is_voted : false
         */

        private List<RecommendBean> recommend;

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        private List<ItemsBean> items;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            private int post_id;
            private int status;
            private int attachs_id;
            private String content;
            private int uid;
            private int create_time;
            private int update_time;
            private String count;
            private int vote_count;
            private int comment_count;
            private String user_avatar;
            private String avatar;
            private String user_name;
            private boolean is_voted;
            private List<String> post_img;
            /**
             * source_id : 1769167647
             * site : 1
             * song_name : 说谎
             * album : 感官/世界
             * artist : 林宥嘉
             * cover : http://pic.xiami.net/images/album/img17/23517/3510481386667071.jpg@!c-80-80
             * url : http://luoo-mp3.kssws.ks-cdn.com/xiami/1460222941.mp3
             */

            private List<PostAudioBean> post_audio;

            public int getPost_id() {
                return post_id;
            }

            public void setPost_id(int post_id) {
                this.post_id = post_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getAttachs_id() {
                return attachs_id;
            }

            public void setAttachs_id(int attachs_id) {
                this.attachs_id = attachs_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public int getVote_count() {
                return vote_count;
            }

            public void setVote_count(int vote_count) {
                this.vote_count = vote_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public boolean isIs_voted() {
                return is_voted;
            }

            public void setIs_voted(boolean is_voted) {
                this.is_voted = is_voted;
            }

            public List<String> getPost_img() {
                return post_img;
            }

            public void setPost_img(List<String> post_img) {
                this.post_img = post_img;
            }

            public List<PostAudioBean> getPost_audio() {
                return post_audio;
            }

            public void setPost_audio(List<PostAudioBean> post_audio) {
                this.post_audio = post_audio;
            }

            public static class PostAudioBean implements Serializable{
                private String source_id;
                private int site;
                private String song_name;
                private String album;
                private String artist;
                private String cover;
                private String url;

                public String getSource_id() {
                    return source_id;
                }

                public void setSource_id(String source_id) {
                    this.source_id = source_id;
                }

                public int getSite() {
                    return site;
                }

                public void setSite(int site) {
                    this.site = site;
                }

                public String getSong_name() {
                    return song_name;
                }

                public void setSong_name(String song_name) {
                    this.song_name = song_name;
                }

                public String getAlbum() {
                    return album;
                }

                public void setAlbum(String album) {
                    this.album = album;
                }

                public String getArtist() {
                    return artist;
                }

                public void setArtist(String artist) {
                    this.artist = artist;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
        public static class RecommendBean {
            private String avatar;
            private String user_name;
            private int uid;
            private int post_count;
            private int fans_count;
            private List<PostsBean> posts;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getPost_count() {
                return post_count;
            }

            public void setPost_count(int post_count) {
                this.post_count = post_count;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public List<PostsBean> getPosts() {
                return posts;
            }

            public void setPosts(List<PostsBean> posts) {
                this.posts = posts;
            }

            public static class PostsBean {
                private int post_id;
                private int status;
                private int attachs_id;
                private String content;
                private int uid;
                private int create_time;
                private int update_time;
                private Object count;
                private int vote_count;
                private int comment_count;
                private String user_avatar;
                private String avatar;
                private String user_name;
                private List<String> post_img;
                /**
                 * source_id : 1774297682
                 * site : 1
                 * song_name : I'll Be Gone By Winter
                 * album : Architect
                 * artist : C Duncan
                 * cover : http://pic.xiami.net/images/album/img4/434304/4343041431434304.jpeg@!c-720-720
                 * url : http://luoo-mp3.kssws.ks-cdn.com/xiami/1458101101.mp3
                 */

                private List<PostAudioBean> post_audio;

                public int getPost_id() {
                    return post_id;
                }

                public void setPost_id(int post_id) {
                    this.post_id = post_id;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getAttachs_id() {
                    return attachs_id;
                }

                public void setAttachs_id(int attachs_id) {
                    this.attachs_id = attachs_id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public int getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(int update_time) {
                    this.update_time = update_time;
                }

                public Object getCount() {
                    return count;
                }

                public void setCount(Object count) {
                    this.count = count;
                }

                public int getVote_count() {
                    return vote_count;
                }

                public void setVote_count(int vote_count) {
                    this.vote_count = vote_count;
                }

                public int getComment_count() {
                    return comment_count;
                }

                public void setComment_count(int comment_count) {
                    this.comment_count = comment_count;
                }

                public String getUser_avatar() {
                    return user_avatar;
                }

                public void setUser_avatar(String user_avatar) {
                    this.user_avatar = user_avatar;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public List<String> getPost_img() {
                    return post_img;
                }

                public void setPost_img(List<String> post_img) {
                    this.post_img = post_img;
                }

                public List<PostAudioBean> getPost_audio() {
                    return post_audio;
                }

                public void setPost_audio(List<PostAudioBean> post_audio) {
                    this.post_audio = post_audio;
                }

                public static class PostAudioBean {
                    private String source_id;
                    private int site;
                    private String song_name;
                    private String album;
                    private String artist;
                    private String cover;
                    private String url;

                    public String getSource_id() {
                        return source_id;
                    }

                    public void setSource_id(String source_id) {
                        this.source_id = source_id;
                    }

                    public int getSite() {
                        return site;
                    }

                    public void setSite(int site) {
                        this.site = site;
                    }

                    public String getSong_name() {
                        return song_name;
                    }

                    public void setSong_name(String song_name) {
                        this.song_name = song_name;
                    }

                    public String getAlbum() {
                        return album;
                    }

                    public void setAlbum(String album) {
                        this.album = album;
                    }

                    public String getArtist() {
                        return artist;
                    }

                    public void setArtist(String artist) {
                        this.artist = artist;
                    }

                    public String getCover() {
                        return cover;
                    }

                    public void setCover(String cover) {
                        this.cover = cover;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }
}
