package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 * Description : SaleSchoolTagsBean
 */

public class SaleSchoolTagsBean extends BaseBean {

    /**
     * data : {"themeTags":[{"fix":0,"name":"测试买手","id":16,"pic":"/2018/02/02/0324037c9ec36c3b74e3050ff01db5ec8d22f3.png"},{"fix":1,"name":"测试买手商学院tag","id":15,"pic":"/2018/02/02/032249be422d3adea63061d84d9783265c14bb.png"},{"fix":0,"name":"合伙人高级班","id":14,"pic":"/2018/02/01/0518066894b0bc91636c9ba730f4806f7cc29a.png"},{"fix":0,"name":"进阶合伙人","id":13,"pic":"/2018/02/01/051752d61051d32b7ce40169970e3529ce33e8.png"},{"fix":0,"name":"新手合伙人","id":12,"pic":"/2018/02/01/0516553422f96fee1958c2adcb7c6a84c9a2f5.png"},{"fix":1,"name":"买手高级课程","id":11,"pic":"/2018/02/01/0519383fd34b2b66740ca66df0745b190b1ef9.png"},{"fix":1,"name":"买手进阶","id":10,"pic":"/2018/02/01/051928272921b8bf73ecc22f8af678c74a78e7.png"},{"fix":1,"name":"新人买手","id":9,"pic":"/2018/02/01/051836d8bacfe19b6db98e3e68c25bc20e14fc.png"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ThemeTagsBean> themeTags;

        public List<ThemeTagsBean> getThemeTags() {
            return themeTags;
        }

        public void setThemeTags(List<ThemeTagsBean> themeTags) {
            this.themeTags = themeTags;
        }

        public static class ThemeTagsBean {
            /**
             * fix : 0
             * name : 测试买手
             * id : 16
             * pic : /2018/02/02/0324037c9ec36c3b74e3050ff01db5ec8d22f3.png
             */

            private int fix;
            private String name;
            private int id;
            private String pic;

            public int getFix() {
                return fix;
            }

            public void setFix(int fix) {
                this.fix = fix;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
