package com.example.discover.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/16.
 */

public class CateGoryEyeBean implements Serializable {

    private CategoryInfoBean categoryInfo;
    private int count;
    private Object nextPageUrl;
    private List<SectionListBean> sectionList;

    public CategoryInfoBean getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfoBean categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<SectionListBean> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionListBean> sectionList) {
        this.sectionList = sectionList;
    }

    public static class CategoryInfoBean {
        /**
         * dataType : CategoryInfo
         * id : 4
         * name : 开胃
         * description : 眼球和味蕾，一个都不放过
         * headerImage : http://img.kaiyanapp.com/085dca86b0eddab9903c82f42e132203.png
         * actionUrl : eyepetizer://category/4/?title=%E5%BC%80%E8%83%83
         * follow : {"itemType":"category","itemId":4,"followed":false}
         */

        private String dataType;
        private int id;
        private String name;
        private String description;
        private String headerImage;
        private String actionUrl;
        private FollowBean follow;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHeaderImage() {
            return headerImage;
        }

        public void setHeaderImage(String headerImage) {
            this.headerImage = headerImage;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public FollowBean getFollow() {
            return follow;
        }

        public void setFollow(FollowBean follow) {
            this.follow = follow;
        }

        public static class FollowBean {
            /**
             * itemType : category
             * itemId : 4
             * followed : false
             */

            private String itemType;
            private int itemId;
            private boolean followed;

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }
        }
    }

    public static class SectionListBean {

        private int id;
        private String type;
        private Object header;
        private FooterBean footer;
        private int count;
        private Object adTrack;
        private List<ItemListBeanX> itemList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getHeader() {
            return header;
        }

        public void setHeader(Object header) {
            this.header = header;
        }

        public FooterBean getFooter() {
            return footer;
        }

        public void setFooter(FooterBean footer) {
            this.footer = footer;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(Object adTrack) {
            this.adTrack = adTrack;
        }

        public List<ItemListBeanX> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBeanX> itemList) {
            this.itemList = itemList;
        }

        public static class FooterBean {
            /**
             * type : blankFooter
             * data : {"height":18}
             */

            private String type;
            private DataBean data;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * height : 18
                 */

                private int height;

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class ItemListBeanX {


            private String type;
            private DataBeanXX data;
            private Object tag;
            private int id;
            private int adIndex;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataBeanXX getData() {
                return data;
            }

            public void setData(DataBeanXX data) {
                this.data = data;
            }

            public Object getTag() {
                return tag;
            }

            public void setTag(Object tag) {
                this.tag = tag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdIndex() {
                return adIndex;
            }

            public void setAdIndex(int adIndex) {
                this.adIndex = adIndex;
            }

            public static class DataBeanXX {

                private String dataType;
                private HeaderBean header;
                private int count;
                private Object adTrack;
                private List<ItemListBean> itemList;

                public String getDataType() {
                    return dataType;
                }

                public void setDataType(String dataType) {
                    this.dataType = dataType;
                }

                public HeaderBean getHeader() {
                    return header;
                }

                public void setHeader(HeaderBean header) {
                    this.header = header;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }

                public List<ItemListBean> getItemList() {
                    return itemList;
                }

                public void setItemList(List<ItemListBean> itemList) {
                    this.itemList = itemList;
                }

                public static class HeaderBean {
                    /**
                     * id : 4
                     * title : 最近更新
                     * font : normal
                     * subTitle : null
                     * subTitleFont : null
                     * textAlign : middle
                     * cover : null
                     * label : null
                     * actionUrl : eyepetizer://common/?title=%E6%9C%80%E8%BF%91%E6%9B%B4%E6%96%B0&url=http%3A%2F%2Fbaobab.kaiyanapp.com%2Fapi%2Fv4%2Fcategories%2FvideoList%3Fid%3D4%26strategy%3Ddate
                     * labelList : null
                     */

                    private int id;
                    private String title;
                    private String font;
                    private Object subTitle;
                    private Object subTitleFont;
                    private String textAlign;
                    private Object cover;
                    private Object label;
                    private String actionUrl;
                    private Object labelList;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getFont() {
                        return font;
                    }

                    public void setFont(String font) {
                        this.font = font;
                    }

                    public Object getSubTitle() {
                        return subTitle;
                    }

                    public void setSubTitle(Object subTitle) {
                        this.subTitle = subTitle;
                    }

                    public Object getSubTitleFont() {
                        return subTitleFont;
                    }

                    public void setSubTitleFont(Object subTitleFont) {
                        this.subTitleFont = subTitleFont;
                    }

                    public String getTextAlign() {
                        return textAlign;
                    }

                    public void setTextAlign(String textAlign) {
                        this.textAlign = textAlign;
                    }

                    public Object getCover() {
                        return cover;
                    }

                    public void setCover(Object cover) {
                        this.cover = cover;
                    }

                    public Object getLabel() {
                        return label;
                    }

                    public void setLabel(Object label) {
                        this.label = label;
                    }

                    public String getActionUrl() {
                        return actionUrl;
                    }

                    public void setActionUrl(String actionUrl) {
                        this.actionUrl = actionUrl;
                    }

                    public Object getLabelList() {
                        return labelList;
                    }

                    public void setLabelList(Object labelList) {
                        this.labelList = labelList;
                    }
                }

                public static class ItemListBean {


                    private String type;
                    private DataBeanX data;
                    private Object tag;
                    private int id;
                    private int adIndex;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public DataBeanX getData() {
                        return data;
                    }

                    public void setData(DataBeanX data) {
                        this.data = data;
                    }

                    public Object getTag() {
                        return tag;
                    }

                    public void setTag(Object tag) {
                        this.tag = tag;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getAdIndex() {
                        return adIndex;
                    }

                    public void setAdIndex(int adIndex) {
                        this.adIndex = adIndex;
                    }

                    public static class DataBeanX {


                        private String dataType;
                        private int id;
                        private String title;
                        private Object slogan;
                        private String description;
                        private ProviderBean provider;
                        private String category;
                        private AuthorBean author;
                        private CoverBean cover;
                        private String playUrl;
                        private Object thumbPlayUrl;
                        private int duration;
                        private WebUrlBean webUrl;
                        private long releaseTime;
                        private String library;
                        private ConsumptionBean consumption;
                        private Object campaign;
                        private Object waterMarks;
                        private Object adTrack;
                        private String type;
                        private String titlePgc;
                        private String descriptionPgc;
                        private String remark;
                        private int idx;
                        private Object shareAdTrack;
                        private Object favoriteAdTrack;
                        private Object webAdTrack;
                        private long date;
                        private Object promotion;
                        private Object label;
                        private String descriptionEditor;
                        private boolean collected;
                        private boolean played;
                        private Object lastViewTime;
                        private Object playlists;
                        private Object src;
                        private List<PlayInfoBean> playInfo;
                        private List<TagsBean> tags;
                        private List<?> labelList;
                        private List<?> subtitles;

                        public String getDataType() {
                            return dataType;
                        }

                        public void setDataType(String dataType) {
                            this.dataType = dataType;
                        }

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public Object getSlogan() {
                            return slogan;
                        }

                        public void setSlogan(Object slogan) {
                            this.slogan = slogan;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public ProviderBean getProvider() {
                            return provider;
                        }

                        public void setProvider(ProviderBean provider) {
                            this.provider = provider;
                        }

                        public String getCategory() {
                            return category;
                        }

                        public void setCategory(String category) {
                            this.category = category;
                        }

                        public AuthorBean getAuthor() {
                            return author;
                        }

                        public void setAuthor(AuthorBean author) {
                            this.author = author;
                        }

                        public CoverBean getCover() {
                            return cover;
                        }

                        public void setCover(CoverBean cover) {
                            this.cover = cover;
                        }

                        public String getPlayUrl() {
                            return playUrl;
                        }

                        public void setPlayUrl(String playUrl) {
                            this.playUrl = playUrl;
                        }

                        public Object getThumbPlayUrl() {
                            return thumbPlayUrl;
                        }

                        public void setThumbPlayUrl(Object thumbPlayUrl) {
                            this.thumbPlayUrl = thumbPlayUrl;
                        }

                        public int getDuration() {
                            return duration;
                        }

                        public void setDuration(int duration) {
                            this.duration = duration;
                        }

                        public WebUrlBean getWebUrl() {
                            return webUrl;
                        }

                        public void setWebUrl(WebUrlBean webUrl) {
                            this.webUrl = webUrl;
                        }

                        public long getReleaseTime() {
                            return releaseTime;
                        }

                        public void setReleaseTime(long releaseTime) {
                            this.releaseTime = releaseTime;
                        }

                        public String getLibrary() {
                            return library;
                        }

                        public void setLibrary(String library) {
                            this.library = library;
                        }

                        public ConsumptionBean getConsumption() {
                            return consumption;
                        }

                        public void setConsumption(ConsumptionBean consumption) {
                            this.consumption = consumption;
                        }

                        public Object getCampaign() {
                            return campaign;
                        }

                        public void setCampaign(Object campaign) {
                            this.campaign = campaign;
                        }

                        public Object getWaterMarks() {
                            return waterMarks;
                        }

                        public void setWaterMarks(Object waterMarks) {
                            this.waterMarks = waterMarks;
                        }

                        public Object getAdTrack() {
                            return adTrack;
                        }

                        public void setAdTrack(Object adTrack) {
                            this.adTrack = adTrack;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getTitlePgc() {
                            return titlePgc;
                        }

                        public void setTitlePgc(String titlePgc) {
                            this.titlePgc = titlePgc;
                        }

                        public String getDescriptionPgc() {
                            return descriptionPgc;
                        }

                        public void setDescriptionPgc(String descriptionPgc) {
                            this.descriptionPgc = descriptionPgc;
                        }

                        public String getRemark() {
                            return remark;
                        }

                        public void setRemark(String remark) {
                            this.remark = remark;
                        }

                        public int getIdx() {
                            return idx;
                        }

                        public void setIdx(int idx) {
                            this.idx = idx;
                        }

                        public Object getShareAdTrack() {
                            return shareAdTrack;
                        }

                        public void setShareAdTrack(Object shareAdTrack) {
                            this.shareAdTrack = shareAdTrack;
                        }

                        public Object getFavoriteAdTrack() {
                            return favoriteAdTrack;
                        }

                        public void setFavoriteAdTrack(Object favoriteAdTrack) {
                            this.favoriteAdTrack = favoriteAdTrack;
                        }

                        public Object getWebAdTrack() {
                            return webAdTrack;
                        }

                        public void setWebAdTrack(Object webAdTrack) {
                            this.webAdTrack = webAdTrack;
                        }

                        public long getDate() {
                            return date;
                        }

                        public void setDate(long date) {
                            this.date = date;
                        }

                        public Object getPromotion() {
                            return promotion;
                        }

                        public void setPromotion(Object promotion) {
                            this.promotion = promotion;
                        }

                        public Object getLabel() {
                            return label;
                        }

                        public void setLabel(Object label) {
                            this.label = label;
                        }

                        public String getDescriptionEditor() {
                            return descriptionEditor;
                        }

                        public void setDescriptionEditor(String descriptionEditor) {
                            this.descriptionEditor = descriptionEditor;
                        }

                        public boolean isCollected() {
                            return collected;
                        }

                        public void setCollected(boolean collected) {
                            this.collected = collected;
                        }

                        public boolean isPlayed() {
                            return played;
                        }

                        public void setPlayed(boolean played) {
                            this.played = played;
                        }

                        public Object getLastViewTime() {
                            return lastViewTime;
                        }

                        public void setLastViewTime(Object lastViewTime) {
                            this.lastViewTime = lastViewTime;
                        }

                        public Object getPlaylists() {
                            return playlists;
                        }

                        public void setPlaylists(Object playlists) {
                            this.playlists = playlists;
                        }

                        public Object getSrc() {
                            return src;
                        }

                        public void setSrc(Object src) {
                            this.src = src;
                        }

                        public List<PlayInfoBean> getPlayInfo() {
                            return playInfo;
                        }

                        public void setPlayInfo(List<PlayInfoBean> playInfo) {
                            this.playInfo = playInfo;
                        }

                        public List<TagsBean> getTags() {
                            return tags;
                        }

                        public void setTags(List<TagsBean> tags) {
                            this.tags = tags;
                        }

                        public List<?> getLabelList() {
                            return labelList;
                        }

                        public void setLabelList(List<?> labelList) {
                            this.labelList = labelList;
                        }

                        public List<?> getSubtitles() {
                            return subtitles;
                        }

                        public void setSubtitles(List<?> subtitles) {
                            this.subtitles = subtitles;
                        }

                        public static class ProviderBean {
                            /**
                             * name : PGC
                             * alias : PGC
                             * icon :
                             */

                            private String name;
                            private String alias;
                            private String icon;

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getAlias() {
                                return alias;
                            }

                            public void setAlias(String alias) {
                                this.alias = alias;
                            }

                            public String getIcon() {
                                return icon;
                            }

                            public void setIcon(String icon) {
                                this.icon = icon;
                            }
                        }

                        public static class AuthorBean {
                            /**
                             * id : 1122
                             * icon : http://img.kaiyanapp.com/e6b15cd31c927372a83b997ca3f971a0.png?imageMogr2/quality/60/format/jpg
                             * name : xianying
                             * description : make most simple things look beautiful and breathtaking.
                             * link :
                             * latestReleaseTime : 1516027540000
                             * videoNum : 24
                             * adTrack : null
                             * follow : {"itemType":"author","itemId":1122,"followed":false}
                             * shield : {"itemType":"author","itemId":1122,"shielded":false}
                             * approvedNotReadyVideoCount : 0
                             * ifPgc : true
                             */

                            private int id;
                            private String icon;
                            private String name;
                            private String description;
                            private String link;
                            private long latestReleaseTime;
                            private int videoNum;
                            private Object adTrack;
                            private FollowBeanX follow;
                            private ShieldBean shield;
                            private int approvedNotReadyVideoCount;
                            private boolean ifPgc;

                            public int getId() {
                                return id;
                            }

                            public void setId(int id) {
                                this.id = id;
                            }

                            public String getIcon() {
                                return icon;
                            }

                            public void setIcon(String icon) {
                                this.icon = icon;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getDescription() {
                                return description;
                            }

                            public void setDescription(String description) {
                                this.description = description;
                            }

                            public String getLink() {
                                return link;
                            }

                            public void setLink(String link) {
                                this.link = link;
                            }

                            public long getLatestReleaseTime() {
                                return latestReleaseTime;
                            }

                            public void setLatestReleaseTime(long latestReleaseTime) {
                                this.latestReleaseTime = latestReleaseTime;
                            }

                            public int getVideoNum() {
                                return videoNum;
                            }

                            public void setVideoNum(int videoNum) {
                                this.videoNum = videoNum;
                            }

                            public Object getAdTrack() {
                                return adTrack;
                            }

                            public void setAdTrack(Object adTrack) {
                                this.adTrack = adTrack;
                            }

                            public FollowBeanX getFollow() {
                                return follow;
                            }

                            public void setFollow(FollowBeanX follow) {
                                this.follow = follow;
                            }

                            public ShieldBean getShield() {
                                return shield;
                            }

                            public void setShield(ShieldBean shield) {
                                this.shield = shield;
                            }

                            public int getApprovedNotReadyVideoCount() {
                                return approvedNotReadyVideoCount;
                            }

                            public void setApprovedNotReadyVideoCount(int approvedNotReadyVideoCount) {
                                this.approvedNotReadyVideoCount = approvedNotReadyVideoCount;
                            }

                            public boolean isIfPgc() {
                                return ifPgc;
                            }

                            public void setIfPgc(boolean ifPgc) {
                                this.ifPgc = ifPgc;
                            }

                            public static class FollowBeanX {
                                /**
                                 * itemType : author
                                 * itemId : 1122
                                 * followed : false
                                 */

                                private String itemType;
                                private int itemId;
                                private boolean followed;

                                public String getItemType() {
                                    return itemType;
                                }

                                public void setItemType(String itemType) {
                                    this.itemType = itemType;
                                }

                                public int getItemId() {
                                    return itemId;
                                }

                                public void setItemId(int itemId) {
                                    this.itemId = itemId;
                                }

                                public boolean isFollowed() {
                                    return followed;
                                }

                                public void setFollowed(boolean followed) {
                                    this.followed = followed;
                                }
                            }

                            public static class ShieldBean {
                                /**
                                 * itemType : author
                                 * itemId : 1122
                                 * shielded : false
                                 */

                                private String itemType;
                                private int itemId;
                                private boolean shielded;

                                public String getItemType() {
                                    return itemType;
                                }

                                public void setItemType(String itemType) {
                                    this.itemType = itemType;
                                }

                                public int getItemId() {
                                    return itemId;
                                }

                                public void setItemId(int itemId) {
                                    this.itemId = itemId;
                                }

                                public boolean isShielded() {
                                    return shielded;
                                }

                                public void setShielded(boolean shielded) {
                                    this.shielded = shielded;
                                }
                            }
                        }

                        public static class CoverBean {
                            /**
                             * feed : http://img.kaiyanapp.com/4680bd2644b16750678d26d4512e8501.png?imageMogr2/quality/60/format/jpg
                             * detail : http://img.kaiyanapp.com/4680bd2644b16750678d26d4512e8501.png?imageMogr2/quality/60/format/jpg
                             * blurred : http://img.kaiyanapp.com/03c75eac298379e487e7e61ae9d66cdb.jpeg?imageMogr2/quality/60/format/jpg
                             * sharing : null
                             * homepage : null
                             */

                            private String feed;
                            private String detail;
                            private String blurred;
                            private Object sharing;
                            private Object homepage;

                            public String getFeed() {
                                return feed;
                            }

                            public void setFeed(String feed) {
                                this.feed = feed;
                            }

                            public String getDetail() {
                                return detail;
                            }

                            public void setDetail(String detail) {
                                this.detail = detail;
                            }

                            public String getBlurred() {
                                return blurred;
                            }

                            public void setBlurred(String blurred) {
                                this.blurred = blurred;
                            }

                            public Object getSharing() {
                                return sharing;
                            }

                            public void setSharing(Object sharing) {
                                this.sharing = sharing;
                            }

                            public Object getHomepage() {
                                return homepage;
                            }

                            public void setHomepage(Object homepage) {
                                this.homepage = homepage;
                            }
                        }

                        public static class WebUrlBean {
                            /**
                             * raw : http://www.eyepetizer.net/detail.html?vid=75139
                             * forWeibo : http://www.eyepetizer.net/detail.html?vid=75139
                             */

                            private String raw;
                            private String forWeibo;

                            public String getRaw() {
                                return raw;
                            }

                            public void setRaw(String raw) {
                                this.raw = raw;
                            }

                            public String getForWeibo() {
                                return forWeibo;
                            }

                            public void setForWeibo(String forWeibo) {
                                this.forWeibo = forWeibo;
                            }
                        }

                        public static class ConsumptionBean {
                            /**
                             * collectionCount : 0
                             * shareCount : 0
                             * replyCount : 0
                             */

                            private int collectionCount;
                            private int shareCount;
                            private int replyCount;

                            public int getCollectionCount() {
                                return collectionCount;
                            }

                            public void setCollectionCount(int collectionCount) {
                                this.collectionCount = collectionCount;
                            }

                            public int getShareCount() {
                                return shareCount;
                            }

                            public void setShareCount(int shareCount) {
                                this.shareCount = shareCount;
                            }

                            public int getReplyCount() {
                                return replyCount;
                            }

                            public void setReplyCount(int replyCount) {
                                this.replyCount = replyCount;
                            }
                        }

                        public static class PlayInfoBean {
                            /**
                             * height : 480
                             * width : 854
                             * urlList : [{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=75139&editionType=normal&source=aliyun","size":33409551},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=75139&editionType=normal&source=qcloud","size":33409551},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=75139&editionType=normal&source=ucloud","size":33409551}]
                             * name : 标清
                             * type : normal
                             * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=75139&editionType=normal&source=aliyun
                             */

                            private int height;
                            private int width;
                            private String name;
                            private String type;
                            private String url;
                            private List<UrlListBean> urlList;

                            public int getHeight() {
                                return height;
                            }

                            public void setHeight(int height) {
                                this.height = height;
                            }

                            public int getWidth() {
                                return width;
                            }

                            public void setWidth(int width) {
                                this.width = width;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public String getUrl() {
                                return url;
                            }

                            public void setUrl(String url) {
                                this.url = url;
                            }

                            public List<UrlListBean> getUrlList() {
                                return urlList;
                            }

                            public void setUrlList(List<UrlListBean> urlList) {
                                this.urlList = urlList;
                            }

                            public static class UrlListBean {
                                /**
                                 * name : aliyun
                                 * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=75139&editionType=normal&source=aliyun
                                 * size : 33409551
                                 */

                                private String name;
                                private String url;
                                private int size;

                                public String getName() {
                                    return name;
                                }

                                public void setName(String name) {
                                    this.name = name;
                                }

                                public String getUrl() {
                                    return url;
                                }

                                public void setUrl(String url) {
                                    this.url = url;
                                }

                                public int getSize() {
                                    return size;
                                }

                                public void setSize(int size) {
                                    this.size = size;
                                }
                            }
                        }

                        public static class TagsBean {
                            /**
                             * id : 717
                             * name : 美食发现
                             * actionUrl : eyepetizer://tag/717/?title=%E7%BE%8E%E9%A3%9F%E5%8F%91%E7%8E%B0
                             * adTrack : null
                             * desc :
                             * bgPicture : http://img.kaiyanapp.com/1db100be73feffd81178b98fb124ed7d.jpeg?imageMogr2/quality/60/format/jpg
                             * headerImage : http://img.kaiyanapp.com/1db100be73feffd81178b98fb124ed7d.jpeg?imageMogr2/quality/60/format/jpg
                             * tagRecType : IMPORTANT
                             */

                            private int id;
                            private String name;
                            private String actionUrl;
                            private Object adTrack;
                            private String desc;
                            private String bgPicture;
                            private String headerImage;
                            private String tagRecType;

                            public int getId() {
                                return id;
                            }

                            public void setId(int id) {
                                this.id = id;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getActionUrl() {
                                return actionUrl;
                            }

                            public void setActionUrl(String actionUrl) {
                                this.actionUrl = actionUrl;
                            }

                            public Object getAdTrack() {
                                return adTrack;
                            }

                            public void setAdTrack(Object adTrack) {
                                this.adTrack = adTrack;
                            }

                            public String getDesc() {
                                return desc;
                            }

                            public void setDesc(String desc) {
                                this.desc = desc;
                            }

                            public String getBgPicture() {
                                return bgPicture;
                            }

                            public void setBgPicture(String bgPicture) {
                                this.bgPicture = bgPicture;
                            }

                            public String getHeaderImage() {
                                return headerImage;
                            }

                            public void setHeaderImage(String headerImage) {
                                this.headerImage = headerImage;
                            }

                            public String getTagRecType() {
                                return tagRecType;
                            }

                            public void setTagRecType(String tagRecType) {
                                this.tagRecType = tagRecType;
                            }
                        }
                    }
                }
            }
        }
    }
}
