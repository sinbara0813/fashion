package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

public class MainBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 66
         * moduleId : 1
         * title : 首页banner
         * visible : 0
         * type : 1
         * sequence : 100
         * properties : {"width":"375","height":"280","top":"0","bottom":"10","left":"0","right":"0"}
         * sectionValues : [{"id":89,"sectionDefId":66,"shortTitle":"我是歌手","url":"http://img.d2c.cn/model/1603/a0ba8e5d760c30bbb9fbf16d512eddfb","frontPic":"/model/1603/a0ba8e5d760c30bbb9fbf16d512eddfb","sequence":100,"properties":{}},{"id":88,"sectionDefId":66,"shortTitle":"白色情人节","url":"http://img.d2c.cn/model/1603/ba686373ef8f5c9933387c95178410ed","frontPic":"/model/1603/ba686373ef8f5c9933387c95178410ed","sequence":100,"properties":{}}]
         */
        private int version;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        private boolean next;

        public boolean isNext() {
            return next;
        }

        public void setNext(boolean next) {
            this.next = next;
        }

        private List<ContentEntity> content;

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class ContentEntity implements Identifiable {
            private long id;
            private int moduleId;
            private String title;
            private int visible;
            private int more;
            private int type;
            private int sequence;
            private String moreUrl;
            /**
             * width : 375
             * height : 280
             * top : 0
             * bottom : 10
             * left : 0
             * right : 0
             */

            private PropertiesEntity properties;
            /**
             * id : 89
             * sectionDefId : 66
             * shortTitle : 我是歌手
             * url : http://img.d2c.cn/model/1603/a0ba8e5d760c30bbb9fbf16d512eddfb
             * frontPic : /model/1603/a0ba8e5d760c30bbb9fbf16d512eddfb
             * sequence : 100
             * properties : {}
             */

            private List<SectionValuesEntity> sectionValues;

            /**
             * id : 116602
             * img : /model/1511/d15f7f55d2dd5045d378c1071bc6ca62
             * price : 5776.0
             * minPrice : 2888.0
             * name : ZHANGSHUAI X ERAL 张帅X艾莱依 囧囧侠第二季 范冰冰 李小璐 张歆艺 陈紫函 霍思燕 谢娜 张天爱 何洁 明星同款 时光之眼系列 大貉子毛拉链连帽贴标眼睛长款羽绒服  女款
             * isTopical : true
             * store : 1
             * mark : 1
             */

            private List<RecommendProductBean.DataEntity.RecommendsEntity.ListEntity> listEntities;

            public List<RecommendProductBean.DataEntity.RecommendsEntity.ListEntity> getListEntities() {
                return listEntities;
            }

            public void setListEntities(List<RecommendProductBean.DataEntity.RecommendsEntity.ListEntity> listEntities) {
                this.listEntities = listEntities;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getModuleId() {
                return moduleId;
            }

            public void setModuleId(int moduleId) {
                this.moduleId = moduleId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public PropertiesEntity getProperties() {
                return properties;
            }

            public void setProperties(PropertiesEntity properties) {
                this.properties = properties;
            }

            public List<SectionValuesEntity> getSectionValues() {
                return sectionValues;
            }

            public void setSectionValues(List<SectionValuesEntity> sectionValues) {
                this.sectionValues = sectionValues;
            }

            public int getMore() {
                return more;
            }

            public void setMore(int more) {
                this.more = more;
            }

            public String getMoreUrl() {
                return moreUrl;
            }

            public void setMoreUrl(String moreUrl) {
                this.moreUrl = moreUrl;
            }

            public static class PropertiesEntity {
                private String width;
                private String height;
                private String top;
                private String bottom;
                private String left;
                private String right;
                private String num;

                public int getWidth() {
                    return Util.isEmpty(width) ? 0 : Integer.parseInt(width);
                }

                public int getHeight() {
                    return Util.isEmpty(height) ? 0 : Integer.parseInt(height);
                }

                public int getTop() {
                    return Util.isEmpty(top) ? 0 : Integer.parseInt(top);
                }

                public int getBottom() {
                    return Util.isEmpty(bottom) ? 0 : Integer.parseInt(bottom);
                }

                public int getLeft() {
                    return Util.isEmpty(left) ? 0 : Integer.parseInt(left);
                }

                public int getRight() {
                    return Util.isEmpty(right) ? 0 : Integer.parseInt(right);
                }

                public int getNum() {
                    int mNum = Util.isEmpty(num) ? 0 : Integer.parseInt(num);
                    if (mNum <= 0) {
                        return 4;
                    } else {
                        return mNum;
                    }
                }
            }

            public static class SectionValuesEntity {
                private int sectionDefId;
                private String shortTitle;
                private String url;
                private String frontPic;
                private int sequence;
                private String longTitle;
                private String price;
                private String originalPrice;
                private List<SectionValuesListEntity> list;

                public String getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(String originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getSectionDefId() {
                    return sectionDefId;
                }

                public void setSectionDefId(int sectionDefId) {
                    this.sectionDefId = sectionDefId;
                }

                public String getShortTitle() {
                    return shortTitle;
                }

                public void setShortTitle(String shortTitle) {
                    this.shortTitle = shortTitle;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getFrontPic() {
                    return frontPic;
                }

                public void setFrontPic(String frontPic) {
                    this.frontPic = frontPic;
                }

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
                }

                public String getLongTitle() {
                    return longTitle;
                }

                public void setLongTitle(String longTitle) {
                    this.longTitle = longTitle;
                }

                public List<SectionValuesListEntity> getList() {
                    return list;
                }

                public void setList(List<SectionValuesListEntity> list) {
                    this.list = list;
                }

                public static class SectionValuesListEntity implements Identifiable {
                    private long id;
                    private String shortTitle;
                    private String frontPic;
                    private int sequence;
                    private String properties;
                    private String url;
                    private double price;
                    private double salePrice;
                    private String brand;

                    public double getSalePrice() {
                        return salePrice;
                    }

                    public void setSalePrice(double salePrice) {
                        this.salePrice = salePrice;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public long getId() {
                        return id;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public String getShortTitle() {
                        return shortTitle;
                    }

                    public void setShortTitle(String shortTitle) {
                        this.shortTitle = shortTitle;
                    }

                    public String getFrontPic() {
                        return frontPic;
                    }

                    public void setFrontPic(String frontPic) {
                        this.frontPic = frontPic;
                    }

                    public int getSequence() {
                        return sequence;
                    }

                    public void setSequence(int sequence) {
                        this.sequence = sequence;
                    }

                    public String getProperties() {
                        return properties;
                    }

                    public void setProperties(String properties) {
                        this.properties = properties;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public String getBrand() {
                        return brand;
                    }

                    public void setBrand(String brand) {
                        this.brand = brand;
                    }
                }
            }
        }
    }
}
