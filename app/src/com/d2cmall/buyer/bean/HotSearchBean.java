package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/11.
 */

public class HotSearchBean extends BaseBean {

    /**
     * data : {"memberSearchSumList":[{"sourceId":null,"number":0,"system":true,"displayName":"1111","id":82,"sort":153,"keyword":"1111","type":null,"url":"/product/list?k=1111","status":0},{"sourceId":null,"number":1348,"system":true,"displayName":"Marie Elie","id":66,"sort":152,"keyword":"marie elie","type":null,"url":"/showroom/designer/10603","status":1},{"sourceId":null,"number":1698,"system":true,"displayName":"LYMK","id":15,"sort":151,"keyword":"LYMK","type":"ALL","url":"/showroom/designer/11150","status":1},{"sourceId":null,"number":543,"system":true,"displayName":"blingbingquinn","id":75,"sort":151,"keyword":"blingbingquinn","type":null,"url":"/showroom/designer/11075","status":1},{"sourceId":null,"number":562,"system":true,"displayName":"七夕 特惠单品","id":78,"sort":150,"keyword":"七夕 特惠单品","type":null,"url":"/product/list?tagId=489","status":0},{"sourceId":null,"number":217,"system":true,"displayName":"七夕礼券 全场通用","id":77,"sort":150,"keyword":"七夕礼券 全场通用","type":null,"url":"/page/qixiqinghua","status":0},{"sourceId":null,"number":95,"system":true,"displayName":"七夕TOP 50 优品","id":80,"sort":150,"keyword":"七夕top 50 优品","type":null,"url":"/product/list?tagId=499","status":0},{"sourceId":null,"number":77,"system":true,"displayName":"七夕吸引力榜单","id":79,"sort":150,"keyword":"七夕吸引力榜单","type":null,"url":"/product/list?tagId=496","status":0},{"sourceId":null,"number":151,"system":true,"displayName":"Ayan Wang","id":76,"sort":149,"keyword":"ayan wang","type":null,"url":"/showroom/designer/11029","status":1},{"sourceId":null,"number":970,"system":true,"displayName":"krisxu","id":62,"sort":147,"keyword":"krisxu","type":null,"url":"/showroom/designer/10301","status":1},{"sourceId":null,"number":709,"system":true,"displayName":"变色T","id":72,"sort":146,"keyword":"变色t","type":null,"url":"/showroom/designer/10127","status":1}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MemberSearchSumListBean> memberSearchSumList;

        public List<MemberSearchSumListBean> getMemberSearchSumList() {
            return memberSearchSumList;
        }

        public void setMemberSearchSumList(List<MemberSearchSumListBean> memberSearchSumList) {
            this.memberSearchSumList = memberSearchSumList;
        }

        public static class MemberSearchSumListBean {
            /**
             * sourceId : null
             * number : 0
             * system : true
             * displayName : 1111
             * id : 82
             * sort : 153
             * keyword : 1111
             * type : null
             * url : /product/list?k=1111
             * status : 0
             */

            private long sourceId;
            private int number;
            private boolean system;
            private String displayName;
            private int id;
            private int sort;
            private String keyword;
            private String type;
            private String url;
            private int status;

            public long getSourceId() {
                return sourceId;
            }

            public void setSourceId(long sourceId) {
                this.sourceId = sourceId;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public boolean isSystem() {
                return system;
            }

            public void setSystem(boolean system) {
                this.system = system;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int statusX) {
                this.status = statusX;
            }
        }
    }
}
