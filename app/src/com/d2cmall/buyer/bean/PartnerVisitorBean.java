package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 * Description : PartnerVisitorBean
 */

public class PartnerVisitorBean extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"event":"买手店-访问","personId":"5a6c0ffd0cf270b8043bdd30","visitDate":1517231855702,"nickname":"熠熠","headImg":"/app/a/18/01/24/D360408F38204FE03B5CDA84AD19EBEA","memberId":968876,"data":{"targetId":41,"targetPath":"/pages/index/index","targetName":"首页"}},{"event":"买手店-访问","personId":null,"visitDate":1517207286107,"nickname":null,"headImg":null,"memberId":null,"data":{"targetId":41,"targetPath":"/pages/product/detail?id=187651","targetName":"商品/CUTO ROSSO 羊皮利是封红包钱包卡包多功能包（头层羊皮）","targetImg":"/2018/01/29/0455532930d807d4b44d646df55041546e68af.jpg"}},{"event":"买手店-访问","personId":"5a6e94d50cf20c3ef30e7794","visitDate":1517196502850,"nickname":"裸睡穿双袜","headImg":"/member_head/70/827070/1761e13abcdd47a0993b90a844321682.png","memberId":827070,"data":{"targetId":41,"targetPath":"/pages/product/detail?id=187651","targetName":"商品/CUTO ROSSO 羊皮利是封红包钱包卡包多功能包（头层羊皮）","targetImg":"/2018/01/27/0630208e8953ccc239a798f7c970aa5265a294.jpg"}},{"event":"买手店-访问","personId":"5a6c11670cf270b8043bddc3","visitDate":1517046980609,"nickname":"。。。","headImg":"/app/a/17/12/22/29CD12B23C51A0366FE8D582FBD674BE","memberId":2884369,"data":{"targetId":41,"targetPath":"/pages/index/index","targetName":"首页"}},{"event":"买手店-访问","personId":"5a6c3a3a0cf2bd0a7213fe2b","visitDate":1517045253638,"nickname":"Pon Pon。","headImg":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erNBvPRWxKdCMKfsc5QftZwONlCibffmeKeYZd6thJkmH4EJHwoDiaWqcQ8keyYQkTbz7Eq20lhUJ5g/132","memberId":927483,"data":{"targetId":41,"targetPath":"/pages/index/index","targetName":"首页"}},{"event":"买手店-访问","personId":"5a6c3d9d0cf20c3ef30dd507","visitDate":1517043101545,"nickname":"Kiss Me💋","headImg":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoeuiaHWZUTniaypkM6dyzlTTDtc8h2g2wPpV59vFl0plEd7Otv6owsXpIBl0Sa2ciamreQACs42Q79Q/132","memberId":3012242,"data":{"targetId":41,"targetPath":"/pages/product/detail?id=176304","targetName":"商品/JE.LES 唐佳 瑜伽兔 立体兔耳 兔子卫衣（男女同款）","targetImg":"/2017/11/07/013333fd5efdf4241c38c3fd9a9287fc266c53.jpg"}}]
     * total : 6
     * index : 1
     * pageCount : 1
     * pageSize : 6
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean next;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DatasBean {
    }

    public static class ListBean {
        /**
         * event : 买手店-访问
         * personId : 5a6c0ffd0cf270b8043bdd30
         * visitDate : 1517231855702
         * nickname : 熠熠
         * headImg : /app/a/18/01/24/D360408F38204FE03B5CDA84AD19EBEA
         * memberId : 968876
         * data : {"targetId":41,"targetPath":"/pages/index/index","targetName":"首页"}
         */

        private String event;
        private String personId;
        private Date visitDate;
        private String nickname;
        private String headImg;
        private String memberId;
        private DataBean data;

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public Date getVisitDate() {
            return visitDate;
        }

        public void setVisitDate(Date visitDate) {
            this.visitDate = visitDate;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * targetId : 41
             * targetPath : /pages/index/index
             * targetName : 首页
             */

            private int targetId;
            private String targetPath;
            private String targetName;

            public int getTargetId() {
                return targetId;
            }

            public void setTargetId(int targetId) {
                this.targetId = targetId;
            }

            public String getTargetPath() {
                return targetPath;
            }

            public void setTargetPath(String targetPath) {
                this.targetPath = targetPath;
            }

            public String getTargetName() {
                return targetName;
            }

            public void setTargetName(String targetName) {
                this.targetName = targetName;
            }
        }
    }
}
