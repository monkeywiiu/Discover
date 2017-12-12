package com.example.discover.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class EyeBean {

    private List<ItemList> itemList;

    public List<ItemList> getItemList() {
        return itemList;
    }

    public class ItemList {

        private Data data;

        public Data getData() {
            return data;
        }

        public class Data {

            private String title;
            private String description;
            private String categroy;
            //private String playUrl;
            private List<PlayInfo> playInfo;

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getCategroy() {
                return categroy;
            }

            /*public String getPlayUrl() {
                return playUrl;
            }*/

            public List<PlayInfo> getPlayInfo() {
                return playInfo;
            }

            public class PlayInfo {

                private int weight;
                private int height;
                private String name;
                private String type;
                private List<UrlList> urlLIst;

                public int getWeight() {
                    return weight;
                }

                public int getHeight() {
                    return height;
                }

                public String getName() {
                    return name;
                }

                public String getType() {
                    return type;
                }

                public List<UrlList> getUrlLIst() {
                    return urlLIst;
                }

                public class UrlList {

                    private String name;
                    private String url;
                    private long size;

                    public String getName() {
                        return name;
                    }

                    public long getSize() {
                        return size;
                    }

                    public String getUrl() {
                        return url;
                    }
                }
            }
        }
    }
}
