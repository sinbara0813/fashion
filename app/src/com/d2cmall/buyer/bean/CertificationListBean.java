package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 * Description : CertificationListBean
 */

public class CertificationListBean extends BaseBean {

    /**
     * data : {"certifications":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"realName":"99000000005","identityCard":"5125**********5172","isdefault":0,"memberId":3031131}]}}
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
         * certifications : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"realName":"99000000005","identityCard":"5125**********5172","isdefault":0,"memberId":3031131}]}
         */

        private CertificationsBean certifications;

        public CertificationsBean getCertifications() {
            return certifications;
        }

        public void setCertifications(CertificationsBean certifications) {
            this.certifications = certifications;
        }

        public static class CertificationsBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"realName":"99000000005","identityCard":"5125**********5172","isdefault":0,"memberId":3031131}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * realName : 99000000005
                 * identityCard : 5125**********5172
                 * isdefault : 0
                 * memberId : 3031131
                 */

                private String realName;
                private String identityCard;
                private int isdefault;

                public int getIsUpload() {
                    return isUpload;
                }

                public void setIsUpload(int isUpload) {
                    this.isUpload = isUpload;
                }

                private int  isUpload;
                private long id;

                private long memberId;

                public long getId() {
                    return id;
                }
                public void setId(long id) {
                    this.id = id;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getIdentityCard() {
                    return identityCard;
                }

                public void setIdentityCard(String identityCard) {
                    this.identityCard = identityCard;
                }

                public int getIsdefault() {
                    return isdefault;
                }

                public void setIsdefault(int isdefault) {
                    this.isdefault = isdefault;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }
            }
        }
    }
}
