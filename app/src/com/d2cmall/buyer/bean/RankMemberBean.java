package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/8.
 */

public class RankMemberBean extends BaseBean {

    /**
     * data : {"hotMember":[{"nickName":"匿名_2397","hot":2,"follow":0,"headPic":null,"memberId":2397},{"nickName":"匿名_2873968","hot":2,"follow":0,"headPic":null,"memberId":2873968},{"nickName":"李玉洁","hot":3,"follow":0,"headPic":"/app/a/17/09/06/4427cc54c205499262928326913bd146","memberId":2905581},{"nickName":"LUNA","hot":14,"follow":0,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","memberId":947949},{"nickName":"DIENASTIE","hot":2,"follow":0,"headPic":"/app/a/17/01/11/ffbbb3ceaca61ba3057396a051a1e6ce","memberId":2684779},{"nickName":"匿名_2884369","hot":2,"follow":0,"headPic":null,"memberId":2884369},{"nickName":"匿名_2872666","hot":1,"follow":0,"headPic":"/app/a/17/08/04/BB331129F43A821034CE40940CAD3FA0","memberId":137982},{"nickName":"夏小楠","hot":7,"follow":0,"headPic":"/app/a/17/05/26/92D7803EDD900F70012FE5ED98EABFEF","memberId":2839515},{"nickName":"你说莫","hot":1,"follow":0,"headPic":"/app/a/17/09/05/4554aa1fd079137164061616c5193cab","memberId":2905569},{"nickName":"G.O Chic","hot":5,"follow":0,"headPic":"/app/a/17/05/09/4EFCA01524D8E9045722BD50340BDDBA","memberId":27508},{"nickName":"众里寻他千百度","hot":1,"follow":0,"headPic":"/member_head/9/609409/dc4ca696c82a4d649f1f27312bc63a1a.png","memberId":609409},{"nickName":"MissLace","hot":8,"follow":0,"headPic":"/app/a/17/06/03/04E8ACF84DDA23AD607996C33274274E","memberId":829735},{"nickName":"twfx","hot":2,"follow":0,"headPic":null,"memberId":872685},{"nickName":"Lisa很忙","hot":2,"follow":0,"headPic":"http://tva4.sinaimg.cn/crop.0.0.1125.1125.1024/679da205jw8f3szvpj2npj20v90v9tb2.jpg","memberId":928059},{"nickName":"THEROOM设计师品牌概念店","hot":1,"follow":0,"headPic":"/member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png","memberId":929377},{"nickName":"闪电潮牌","hot":1,"follow":0,"headPic":"/app/a/16/08/29/428fd5e84910721f64ba7a19e1a4633f","memberId":962010},{"nickName":"池雨蒂","hot":1,"follow":0,"headPic":null,"memberId":1070168},{"nickName":"巴拉巴拉","hot":1,"follow":0,"headPic":"/app/a/17/03/02/9a42618a1a1a08340260c10c722d866c","memberId":1452023},{"nickName":"REOPARUDO","hot":2,"follow":0,"headPic":"/app/a/17/01/13/5AE3B73DC202894520E8C43E8A6C44DC","memberId":1797110},{"nickName":"Dope4ever","hot":1,"follow":0,"headPic":"/app/a/17/07/28/B0576655CA529C125A04955021DBF9D2","memberId":1829635},{"nickName":"何佳妮SARYHE","hot":1,"follow":0,"headPic":"/app/a/16/12/20/9c2223f227d9781979ad697191067ccf","memberId":1930707},{"nickName":"ζ     君拂","hot":1,"follow":0,"headPic":"http://q.qlogo.cn/qqapp/101265483/A69A204A506FCE8D04F5037B640E569F/100","memberId":2619514},{"nickName":"A🍳范\u2022","hot":1,"follow":0,"headPic":"http://wx.qlogo.cn/mmopen/I2fxKtgmKrRtdJsoZqJjXIs0gIeYtWuVypL2891YtokjDKTiaXBbRAkOqsUGLlxwKDnCBQYw2uqllg5csYH82Fw/0","memberId":2678915},{"nickName":"TADASHI SHOJI","hot":4,"follow":0,"headPic":"/app/a/17/04/19/8C43C16891010904331F27F37D0BA6E6","memberId":2687229},{"nickName":"匿名_2791174","hot":1,"follow":0,"headPic":null,"memberId":2791174},{"nickName":"7/7Mois A Moi","hot":2,"follow":0,"headPic":"/app/a/17/05/08/70026A71A5D2C0C692A64D81BE59279F","memberId":2817695},{"nickName":"🐯猫猫🐯","hot":2,"follow":0,"headPic":"http://wx.qlogo.cn/mmopen/jyB8bIIUQ6oL6KMDXnawsFKRUyuGoYDqlFoSmNMPs0f0cRKUVkZqWZAuVkMYsQZMaVUeiazvZeBTaOM0ib2jYcdnn0SBPy3z5Y/0","memberId":2871643}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HotMemberBean> hotMember;

        public List<HotMemberBean> getHotMember() {
            return hotMember;
        }

        public void setHotMember(List<HotMemberBean> hotMember) {
            this.hotMember = hotMember;
        }

        public static class HotMemberBean {
            /**
             * nickName : 匿名_2397
             * hot : 2
             * follow : 0
             * headPic : null
             * memberId : 2397
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
