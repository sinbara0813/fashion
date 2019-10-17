package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/2 18:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpTopBean extends BaseBean {

    /**
     * data : {"brandArray":[{"name":"XINYE JIANG","count":17,"id":11116,"headPic":"/2017/05/31/0154012569d091995c8a9029e0190659f3652b.jpg"},{"name":"La Fata 服装工作室","count":38,"id":11322,"headPic":"/2017/12/11/081342f91e68c91f113c1a742b28988346b2a0.jpg"},{"name":"崔航","count":8,"id":11223,"headPic":"/2017/08/17/013417908c92b1bfd8e772d456ad1043d41142.jpg"},{"name":"叶谦，YE\u2018S by YESIR","count":20,"id":10189,"headPic":"/model/1603/58dda1ed055d789eeaa3f7006ba063b8"},{"name":"Neo","count":13,"id":10430,"headPic":"/model/1604/f469130449fc6b20a6e1e542e01f29d4"},{"name":"吕燕","count":10,"id":10144,"headPic":"/model/1603/a7489d63e59bc15ee370f061bfc982cb"},{"name":"Neo","count":13,"id":10523,"headPic":"/2017/06/17/06432269a8cc9e3c1a515fa9e67708cbec33b6.jpg"},{"name":"Fion","count":102,"id":10790,"headPic":"/2016/09/28/064009f24b38e795f45655608619b834bdb9f9.jpg"},{"name":"KrisXu","count":2,"id":10301,"headPic":"/2017/06/05/0255474c64d2a6aed9d69107b7a8d9ce222827.jpg"},{"name":"杨家明","count":6,"id":10922,"headPic":"/2016/12/28/031003950acf0e4b9b98781887fd0a814ee083.jpg"},{"name":"Jean Luc Amsler","count":20,"id":11249,"headPic":"/2017/09/18/091108a6b68969a781ad3779c048cf20c0d620.jpg"},{"name":"陈枫捷","count":6,"id":11130,"headPic":"/2017/06/09/0157555c32ab7fdcd230c1f9ffcc13549c049e.jpg"},{"name":"郑铭","count":26,"id":11062,"headPic":"/2017/04/20/0708535d995020694e0dce5451cd40402e6ac5.jpg"},{"name":"Tony","count":21,"id":11297,"headPic":"/2017/12/27/0626246dcea936f1e5b9659168248173c814c6.jpg"},{"name":"PRIVATE STRUCTURE","count":10,"id":10707,"headPic":"/model/1607/e8ae29be24a70c53dd36412a9d1780c4"},{"name":"LUNA","count":3,"id":10666,"headPic":"/model/1606/b1692c6667c10587997f0ccee53d8a7d"},{"name":"HOT","count":17,"id":10614,"headPic":"/model/1605/cb0f80f2562dee0c21aa489cc4f657ff"},{"name":"Huge Lee","count":4,"id":11071,"headPic":"/2017/04/26/024136217873e13780dab14da16dfce7049c96.jpg"},{"name":"Way","count":29,"id":11029,"headPic":"/2017/03/15/055133f7514eb17fbf52e12fe829b24e706259.jpg"},{"name":"许北亭","count":7,"id":11117,"headPic":"/2017/05/31/085240026adb50df831a135a897b9f2aefbefd.jpg"},{"name":"希孜","count":7,"id":11135,"headPic":"/2017/06/14/05291904554d501fa531dc797314f2ba6db22e.jpg"},{"name":"白薇","count":25,"id":11328,"headPic":"/2017/12/19/02023295971a76bac8420e6ae7f23310efa7d5.jpg"},{"name":"SU live","count":1,"id":11293,"headPic":"/2017/11/15/041914b674197e49e9353d8bfb5842e875c298.jpg"},{"name":"D2C","count":5,"id":10918,"headPic":"/2016/12/23/065557e391d07017d6229ea242122bb9575fe7.jpg"},{"name":"GABRIELLE. H","count":2,"id":11124,"headPic":"/2017/06/05/022852a741a7e5c28d45f013ea8bd9af33c89b.jpg"},{"name":"于承艳","count":9,"id":11216,"headPic":"/2017/08/12/045428e93388656ccf8aa4f843c50d0430da88.jpg"},{"name":"JARVEN","count":22,"id":10623,"headPic":"/2017/05/02/08064301babc8754c89eaacc1d1eec6dacda0e.jpg"},{"name":"沈欣","count":5,"id":11097,"headPic":"/2017/05/16/025642e5610c676a51587a91a8fa7eb59dcf5e.jpg"},{"name":"赫伟","count":14,"id":11327,"headPic":"/2017/12/18/055611c553437df3ff4268fc5e8c4305a471d2.png"},{"name":"Jack Fan","count":11,"id":10444,"headPic":"/2017/10/24/0458055d5376628150728567c121305955b4f3.png"},{"name":"鱼","count":34,"id":11307,"headPic":"/2017/11/29/065556f49cd5f784807b0263002f5669ee0be0.jpg"},{"name":"朱安邦","count":19,"id":11329,"headPic":"/2017/12/19/0220180a4173efa2a6f8f42fb9e3a3bdb21f14.jpg"},{"name":"金小生","count":14,"id":11331,"headPic":"/2017/12/19/0600319aba706520418118421829a0ac74e977.jpg"},{"name":"向月","count":18,"id":11316,"headPic":"/2017/12/11/0117578297caf4f585b96b81ffed5779f5be64.jpg"},{"name":"cecilia woo","count":22,"id":10482,"headPic":"/model/1604/2ff002c479c34e8f242832f7665385ab"},{"name":"Jack","count":4,"id":11059,"headPic":"/2017/04/17/0254193fc4912ece1f5eded6be917adeb5ecf2.jpg"},{"name":"turdove","count":3,"id":11200,"headPic":"/2017/07/28/082021ed2f721e28db1a6d5ac88cc7ef135440.jpg"},{"name":"JESSICA","count":5,"id":10883,"headPic":"/2016/11/28/09544308e41bf7c682f8886e56fce18cf0beaa.jpg"},{"name":"任茜","count":2,"id":10032,"headPic":"/model/1603/2caac0e3ea84d7c310033bac18a06a85"},{"name":"船长","count":7,"id":11061,"headPic":"/2017/04/19/0602436e1f2d21131d69d347c3e0d117317115.jpg"},{"name":"close","count":1,"id":11282,"headPic":"/2017/11/08/0514419836bbbc1b54fa43c4ce94718302374c.jpg"},{"name":"梅雪","count":15,"id":11207,"headPic":"/2017/08/07/0231152ca8d8aad1e5cecfcf3fc5c21b593b42.jpg"},{"name":"程莉莉","count":14,"id":10952,"headPic":"/2017/01/17/023548ae4145cac4c3b25eda47de9b2f5d1d4e.jpg"},{"name":"张帅","count":1,"id":10053,"headPic":"/dh/53/10053/aa065cf9d74d81fab131423d83708a03"},{"name":"Chris Ma","count":16,"id":10905,"headPic":"/2016/12/12/065844893e00fc6012c6244740da37139620d1.jpg"},{"name":"Theresa & Zoe","count":12,"id":11147,"headPic":"/2017/06/22/0848339689839afd22a6cf5f912336bdd3339b.jpg"},{"name":"TADASHI SHOJI","count":20,"id":10599,"headPic":"/model/1605/1b93cbbeb6534c796a95d71b0d5e3f57"},{"name":"丁子","count":5,"id":10947,"headPic":"/2017/01/12/0205042f188f834623ca4bf8757825d3b5f8ac.jpg"},{"name":"刘海","count":9,"id":11325,"headPic":"/2017/12/13/0804563b9645510a9c4f58b1bf0c47afa7d3e4.jpg"},{"name":"Lili M","count":6,"id":11105,"headPic":"/2017/07/17/0436105246c0fb2d8ad39b29ea17ab705f376c.jpg"},{"name":"sasha","count":10,"id":10901,"headPic":"/2016/12/09/0701436693508c5657995a4005a62f35eadef2.jpg"},{"name":"carter li","count":1,"id":10654,"headPic":"/model/1606/757cf411be4bf7c4246a7f039e0e604a"},{"name":"朱安邦","count":4,"id":11330,"headPic":"/2017/12/19/0257264a4aedff0ad796c4ce22ebab80f24526.jpg"},{"name":"La Hormiga","count":7,"id":11250,"headPic":"/2017/09/19/1142084391fbb6861ffa4e24c0b9b7f698c25f.jpg"},{"name":"袁明亮","count":12,"id":11334,"headPic":"/2017/12/21/095939338605fe25c8c0c2f3b992f49493e0af.jpg"},{"name":"MELON","count":4,"id":10197,"headPic":"/2017/12/11/0532154f03eb8d6321e0d4470966e459c592ac.jpg"},{"name":"無有乐园","count":3,"id":11206,"headPic":"/2017/08/04/075900bfa234d6a10f22885b7b1d961f14f8f8.jpg"},{"name":"Catherine","count":6,"id":10842,"headPic":"/2016/11/05/20384994f59b7d198eebe6a23f641988036b58.jpg"},{"name":"Find hot","count":4,"id":11337,"headPic":"/2017/12/26/09481942f415ab0ffc74045a385b677cbc0f04.jpg"},{"name":"Lola Luna","count":3,"id":11197,"headPic":"/2017/07/26/100810f6e4d050be7e9bc7accbf364af5ce751.jpg"},{"name":"O.HA","count":1,"id":11185,"headPic":"/2017/07/20/0947006fbf67dd6ab1a38a0becf469510fbf86.jpg"},{"name":"devilbeauty","count":9,"id":10925,"headPic":"/2016/12/30/0312077e689f506c0a922f7027a385fd3d58c0.jpg"},{"name":"谢怡君","count":14,"id":11326,"headPic":"/2017/12/13/084152b4b7838ab8b6b30e9a22009ff3853319.jpg"},{"name":"迦堤GALTISCOPIO","count":4,"id":11113,"headPic":"/2017/05/25/060646b539d69a17119a2c2fc9c4ebab1807b7.jpg"},{"name":"Ms Mustache","count":4,"id":11090,"headPic":"/2017/05/09/080701aa7bd815846a789e99853fb82fa04565.jpeg"},{"name":"叶成俊","count":3,"id":11312,"headPic":"/2017/12/09/024636fc8d1e5c96bf70b5f85c0370ffac52b4.jpg"},{"name":"Kris.W","count":2,"id":11261,"headPic":"/2017/10/16/03571716ef5af6c10c351b458c48f0cd5f681a.png"},{"name":"LEO","count":1,"id":10924,"headPic":"/2016/12/28/1121114e0a930a15b0552938cfa8247fc8994b.jpg"},{"name":"何思逸","count":1,"id":10914,"headPic":"/2016/12/21/0821478c5fa8bd76d8305fbb2a705dbf8510f6.jpg"},{"name":"邢永","count":1,"id":10513,"headPic":"/model/1604/81bc3c88c061815ff8894dcf46b9dd9a"},{"name":"TOM","count":1,"id":10734,"headPic":"/2016/08/23/022522c4bc157f03aae41832fbb0ea5cd7a566.jpg"},{"name":"Wendy Zhang张韵文","count":1,"id":10393,"headPic":"/model/1603/f8c6786265441cc0ba5402e0a647c62f"},{"name":"张大琪于","count":2,"id":10940,"headPic":"/2017/01/09/0607300ef046d61b1f6ee6f4d9c99528ab6dfd.jpg"},{"name":"何敏","count":1,"id":11308,"headPic":"/2017/12/04/091728461194f0d283841d9d7252210d8219f8.jpg"},{"name":"欧阳丹","count":1,"id":10511,"headPic":"/model/1604/fbb039e1582668c27e566e7205c2ce99"},{"name":"the warm","count":1,"id":11313,"headPic":"/2017/12/09/053621ed7d17931d27ad432b751972b911ee84.jpg"}],"topCateArray":[{"name":"首饰","count":86,"id":3},{"name":"女装","count":476,"id":1},{"name":"配饰","count":79,"id":9},{"name":"箱包","count":34,"id":15},{"name":"家居个护","count":10,"id":11},{"name":"男装","count":60,"id":6},{"name":"鞋履","count":14,"id":13},{"name":"童装","count":36,"id":7},{"name":"居家生活","count":6,"id":12}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private AdResourceBean adResource;
        private List<BrandArrayBean> brandArray;
        private List<TopCateArrayBean> topCateArray;

        public List<BrandArrayBean> getBrandArray() {
            return brandArray;
        }

        public void setBrandArray(List<BrandArrayBean> brandArray) {
            this.brandArray = brandArray;
        }

        public List<TopCateArrayBean> getTopCateArray() {
            return topCateArray;
        }

        public void setTopCateArray(List<TopCateArrayBean> topCateArray) {
            this.topCateArray = topCateArray;
        }

        public AdResourceBean getAdResource() {
            return adResource;
        }

        public void setAdResource(AdResourceBean adResource) {
            this.adResource = adResource;
        }

        public static class AdResourceBean {
            /**
             * id : 12
             * pic : /2017/09/07/083545330855671d886c867900469643b06480.png
             * title : 123
             * url : /product/163935
             */

            private int id;
            private String pic;
            private String title;
            private String url;
            private String[] pics;
            private String[] picsUrl;
            private String[] shotTitles;
            private String video;
            private String videoPic;
            private String[] descriptions;

            public String getVideoPic() {
                return videoPic;
            }

            public void setVideoPic(String videoPic) {
                this.videoPic = videoPic;
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

            public String[] getPics() {
                return pics;
            }

            public void setPics(String[] pics) {
                this.pics = pics;
            }

            public String[] getPicsUrl() {
                return picsUrl;
            }

            public void setPicsUrl(String[] picsUrl) {
                this.picsUrl = picsUrl;
            }

            public String[] getShotTitles() {
                return shotTitles;
            }

            public void setShotTitles(String[] shotTitles) {
                this.shotTitles = shotTitles;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String[] getDescriptions() {
                return descriptions;
            }

            public void setDescriptions(String[] descriptions) {
                this.descriptions = descriptions;
            }
        }

        public static class BrandArrayBean {
            /**
             * name : XINYE JIANG
             * count : 17
             * id : 11116
             * headPic : /2017/05/31/0154012569d091995c8a9029e0190659f3652b.jpg
             */

            private String name;
            private int count;
            private int id;
            private String headPic;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }
        }

        public static class TopCateArrayBean {
            /**
             * name : 首饰
             * count : 86
             * id : 3
             */

            private String name;
            private int count;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
