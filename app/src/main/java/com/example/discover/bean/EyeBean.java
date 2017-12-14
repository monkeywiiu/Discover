package com.example.discover.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class EyeBean implements Serializable {

    private List<ItemList> itemList;

    public List<ItemList> getItemList() {
        return itemList;
    }

    public class ItemList implements Serializable {

        private Data data;
        private String type;

        public Data getData() {
            return data;
        }

        public String getType() {
            return type;
        }

        public class Data implements Serializable{

            private String title;
            private String description;
            private String categroy;

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


            public List<PlayInfo> getPlayInfo() {
                return playInfo;
            }

            public class PlayInfo implements Serializable {

                private int weight;
                private int height;
                private String name;
                private String type;
                private List<UrlList> urlList;

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

                public List<UrlList> getUrlList() {
                    return urlList;
                }

                public class UrlList implements Serializable{

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
