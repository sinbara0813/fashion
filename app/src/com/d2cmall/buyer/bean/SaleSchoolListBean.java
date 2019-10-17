package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 * Description : SaleSchoolListBean
 */

public class SaleSchoolListBean extends BaseBean {

    /**
     * data : {"themes":{"page":1,"pageSize":20,"total":3,"pageCount":1,"list":[{"id":76,"createDate":1517550980000,"creator":"方健","modifyDate":1517550710000,"lastModifyMan":null,"title":"买手高级课程333","url":null,"upMarketDate":null,"pic":"/2018/02/02/055402a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055405a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试333333333333","type":"WECHAT"},{"id":75,"createDate":1517550952000,"creator":"方健","modifyDate":1517550683000,"lastModifyMan":null,"title":"买手高级课程222","url":null,"upMarketDate":null,"pic":"/2018/02/02/05532684b08245fee76c42b5e8fb9ea322ddb2.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/05532984b08245fee76c42b5e8fb9ea322ddb2.png","keyword":"测试测试22222222222222","type":"WECHAT"},{"id":74,"createDate":1517550806000,"creator":"方健","modifyDate":1517550644000,"lastModifyMan":null,"title":"买手高级课程1111","url":null,"upMarketDate":null,"pic":"/2018/02/02/055056a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055101a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试测试测试测试测试测试测试测试测试1111","type":"WECHAT"}],"totalCount":3,"pageNumber":1,"forward":false,"next":false,"startRow":0,"offset":0}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * themes : {"page":1,"pageSize":20,"total":3,"pageCount":1,"list":[{"id":76,"createDate":1517550980000,"creator":"方健","modifyDate":1517550710000,"lastModifyMan":null,"title":"买手高级课程333","url":null,"upMarketDate":null,"pic":"/2018/02/02/055402a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055405a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试333333333333","type":"WECHAT"},{"id":75,"createDate":1517550952000,"creator":"方健","modifyDate":1517550683000,"lastModifyMan":null,"title":"买手高级课程222","url":null,"upMarketDate":null,"pic":"/2018/02/02/05532684b08245fee76c42b5e8fb9ea322ddb2.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/05532984b08245fee76c42b5e8fb9ea322ddb2.png","keyword":"测试测试22222222222222","type":"WECHAT"},{"id":74,"createDate":1517550806000,"creator":"方健","modifyDate":1517550644000,"lastModifyMan":null,"title":"买手高级课程1111","url":null,"upMarketDate":null,"pic":"/2018/02/02/055056a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055101a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试测试测试测试测试测试测试测试测试1111","type":"WECHAT"}],"totalCount":3,"pageNumber":1,"forward":false,"next":false,"startRow":0,"offset":0}
         */

        private ThemesBean themes;

        public ThemesBean getThemes() {
            return themes;
        }

        public void setThemes(ThemesBean themes) {
            this.themes = themes;
        }

        public static class ThemesBean {
            /**
             * page : 1
             * pageSize : 20
             * total : 3
             * pageCount : 1
             * list : [{"id":76,"createDate":1517550980000,"creator":"方健","modifyDate":1517550710000,"lastModifyMan":null,"title":"买手高级课程333","url":null,"upMarketDate":null,"pic":"/2018/02/02/055402a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055405a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试333333333333","type":"WECHAT"},{"id":75,"createDate":1517550952000,"creator":"方健","modifyDate":1517550683000,"lastModifyMan":null,"title":"买手高级课程222","url":null,"upMarketDate":null,"pic":"/2018/02/02/05532684b08245fee76c42b5e8fb9ea322ddb2.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/05532984b08245fee76c42b5e8fb9ea322ddb2.png","keyword":"测试测试22222222222222","type":"WECHAT"},{"id":74,"createDate":1517550806000,"creator":"方健","modifyDate":1517550644000,"lastModifyMan":null,"title":"买手高级课程1111","url":null,"upMarketDate":null,"pic":"/2018/02/02/055056a07c4e4deef17b6440fb0535135bad2d.png","status":1,"sort":0,"deleted":0,"tagId":11,"pcContent":null,"wapContent":null,"sharePic":"/2018/02/02/055101a07c4e4deef17b6440fb0535135bad2d.png","keyword":"测试测试测试测试测试测试测试测试测试1111","type":"WECHAT"}]
             * totalCount : 3
             * pageNumber : 1
             * forward : false
             * next : false
             * startRow : 0
             * offset : 0
             */

            private int page;
            private int pageSize;
            private int total;
            private int pageCount;
            private int totalCount;
            private int pageNumber;
            private boolean forward;
            private boolean next;
            private int startRow;
            private int offset;
            private List<ListBean> list;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public boolean isForward() {
                return forward;
            }

            public void setForward(boolean forward) {
                this.forward = forward;
            }

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 76
                 * createDate : 1517550980000
                 * creator : 方健
                 * modifyDate : 1517550710000
                 * lastModifyMan : null
                 * title : 买手高级课程333
                 * url : null
                 * upMarketDate : null
                 * pic : /2018/02/02/055402a07c4e4deef17b6440fb0535135bad2d.png
                 * status : 1
                 * sort : 0
                 * deleted : 0
                 * tagId : 11
                 * pcContent : null
                 * wapContent : null
                 * sharePic : /2018/02/02/055405a07c4e4deef17b6440fb0535135bad2d.png
                 * keyword : 测试333333333333
                 * type : WECHAT
                 */

                private int id;
                private Date createDate;
                private String creator;
                private long modifyDate;
                private Date lastModifyMan;
                private String title;
                private String url;
                private Date upMarketDate;
                private String pic;
                @SerializedName("status")
                private int statusX;
                private int sort;
                private int deleted;
                private int tagId;
                private String pcContent;
                private String wapContent;
                private String sharePic;
                private String keyword;
                private String type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Date getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Date createDate) {
                    this.createDate = createDate;
                }

                public String getCreator() {
                    return creator;
                }

                public void setCreator(String creator) {
                    this.creator = creator;
                }

                public long getModifyDate() {
                    return modifyDate;
                }

                public void setModifyDate(long modifyDate) {
                    this.modifyDate = modifyDate;
                }

                public Date getLastModifyMan() {
                    return lastModifyMan;
                }

                public void setLastModifyMan(Date lastModifyMan) {
                    this.lastModifyMan = lastModifyMan;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public Date getUpMarketDate() {
                    return upMarketDate;
                }

                public void setUpMarketDate(Date upMarketDate) {
                    this.upMarketDate = upMarketDate;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public int getStatusX() {
                    return statusX;
                }

                public void setStatusX(int statusX) {
                    this.statusX = statusX;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public int getDeleted() {
                    return deleted;
                }

                public void setDeleted(int deleted) {
                    this.deleted = deleted;
                }

                public int getTagId() {
                    return tagId;
                }

                public void setTagId(int tagId) {
                    this.tagId = tagId;
                }

                public String getPcContent() {
                    return pcContent;
                }

                public void setPcContent(String pcContent) {
                    this.pcContent = pcContent;
                }

                public String getWapContent() {
                    return wapContent;
                }

                public void setWapContent(String wapContent) {
                    this.wapContent = wapContent;
                }

                public String getSharePic() {
                    return sharePic;
                }

                public void setSharePic(String sharePic) {
                    this.sharePic = sharePic;
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
            }
        }
    }
}
