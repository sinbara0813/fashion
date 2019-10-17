package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 16:12
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UnFocusMemberBean extends BaseBean {

    /**
     * data : {"activeMember":[{"nickName":"THEROOM设计师品牌概念店","hot":313,"headPic":"/member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png","memberId":929377},{"nickName":"徐菲阳","hot":132,"headPic":"/app/a/17/05/22/205B5BA380F5F97CE7CC45C82D345D0F","memberId":24530},{"nickName":"LUNA","hot":106,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","memberId":947949},{"nickName":"闪电潮牌","hot":100,"headPic":"/app/a/16/08/29/428fd5e84910721f64ba7a19e1a4633f","memberId":962010},{"nickName":"V.Charm设计师刘阳","hot":95,"headPic":"http://wx.qlogo.cn/mmopen/PiajxSqBRaELsRv3zJVp8T1q2Acibh0XpZUu9oV8XNsImZS9yV4qrhoJthK9Y6RPEmSOG2l8yApgYA7iaxym6VkjQ/0","memberId":77832}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ActiveMemberBean> activeMember;

        public List<ActiveMemberBean> getActiveMember() {
            return activeMember;
        }

        public void setActiveMember(List<ActiveMemberBean> activeMember) {
            this.activeMember = activeMember;
        }

        public static class ActiveMemberBean {
            /**
             * nickName : THEROOM设计师品牌概念店
             * hot : 313
             * headPic : /member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png
             * memberId : 929377
             */

            private String nickName;
            private int hot;
            private String headPic;
            private long memberId;
            private int follow;

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
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
