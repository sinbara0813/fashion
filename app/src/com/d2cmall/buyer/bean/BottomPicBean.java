package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class BottomPicBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<AppNavigationBean> appNavigation;

        public List<AppNavigationBean> getAppNavigation() {
            return appNavigation;
        }

        public void setAppNavigation(List<AppNavigationBean> appNavigation) {
            this.appNavigation = appNavigation;
        }

        public static class AppNavigationBean {

            private String clickPic;
            private String name;
            private String pic;
            private int sort;
            private String backColor;

            public String getBackColor() {
                return backColor;
            }

            public void setBackColor(String backColor) {
                this.backColor = backColor;
            }

            public String getClickPic() {
                return clickPic;
            }

            public void setClickPic(String clickPic) {
                this.clickPic = clickPic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

        }
    }
}
