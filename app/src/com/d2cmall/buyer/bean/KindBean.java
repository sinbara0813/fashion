package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class KindBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {

        private List<NavigationsEntity> navigations;

        public void setNavigations(List<NavigationsEntity> navigations) {
            this.navigations = navigations;
        }

        public List<NavigationsEntity> getNavigations() {
            return navigations;
        }

        public static class NavigationsEntity implements Identifiable {
            private long id;
            private String name;
            private String url;

            private List<ItemsEntity> items;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                private int id;
                private String title;
                private String url;
                private String pic;

                public void setId(int id) {
                    this.id = id;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public int getId() {
                    return id;
                }

                public String getTitle() {
                    return title;
                }

                public String getUrl() {
                    return url;
                }

                public String getPic() {
                    return pic;
                }
            }
        }
    }
}
