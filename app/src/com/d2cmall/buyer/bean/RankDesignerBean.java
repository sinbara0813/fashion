package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/8.
 */

public class RankDesignerBean extends BaseBean {

    /**
     * data : {"hotDesigner":[{"nickName":"你说莫","hot":5,"follow":0,"headPic":"/app/a/17/09/05/4554aa1fd079137164061616c5193cab","memberId":2905569},{"nickName":"我不知道哦","hot":3,"follow":0,"headPic":"/app/a/17/08/30/dc899fa703d96437754c378272e4af67","memberId":2865965},{"nickName":"dddaa","hot":1,"follow":0,"headPic":"/app/a/17/09/02/0e1887e5f4ef5e34b301f0e3536ea3e5","memberId":2905570},{"nickName":"G.O Chic","hot":3,"follow":0,"headPic":"/app/a/17/05/09/4EFCA01524D8E9045722BD50340BDDBA","memberId":27508},{"nickName":"LUNA","hot":3,"follow":0,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","memberId":947949},{"nickName":"DIENASTIE","hot":1,"follow":0,"headPic":"/app/a/17/01/11/ffbbb3ceaca61ba3057396a051a1e6ce","memberId":2684779},{"nickName":"夏小楠","hot":4,"follow":0,"headPic":"/app/a/17/05/26/92D7803EDD900F70012FE5ED98EABFEF","memberId":2839515},{"nickName":"MissLace","hot":4,"follow":0,"headPic":"/app/a/17/06/03/04E8ACF84DDA23AD607996C33274274E","memberId":829735},{"nickName":"闪电潮牌","hot":1,"follow":0,"headPic":"/app/a/16/08/29/428fd5e84910721f64ba7a19e1a4633f","memberId":962010},{"nickName":"Dope4ever","hot":1,"follow":0,"headPic":"/app/a/17/07/28/B0576655CA529C125A04955021DBF9D2","memberId":1829635},{"nickName":"匿名_2376061","hot":1,"follow":0,"headPic":null,"memberId":2376061},{"nickName":"devilbeauty","hot":1,"follow":0,"headPic":"/app/a/17/06/05/958982342FF33308423ECF4BC9FD5431","memberId":2678248},{"nickName":"🍀  無表情","hot":1,"follow":0,"headPic":"http://wx.qlogo.cn/mmopen/I2fxKtgmKrTibeb2icsgvkkYgn8ZIlSbD6mwPYwGQQkXYC9aXJeWV3Vdl27gkfZYTy3K9dmehA1lXUD42iaN3pzrl5a33fTEmbz/0","memberId":2870033}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HotDesignerBean> hotDesigner;

        public List<HotDesignerBean> getHotDesigner() {
            return hotDesigner;
        }

        public void setHotDesigner(List<HotDesignerBean> hotDesigner) {
            this.hotDesigner = hotDesigner;
        }

        public static class HotDesignerBean {
            /**
             * nickName : 你说莫
             * hot : 5
             * follow : 0
             * headPic : /app/a/17/09/05/4554aa1fd079137164061616c5193cab
             * memberId : 2905569
             */

            private String nickName;
            private int hot;
            private int follow;
            private String headPic;
            private int memberId;

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

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }
    }
}
