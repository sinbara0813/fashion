package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/13.
 */

public class PhysicalStoreBean extends BaseBean {

    /**
     * data : {"stores":{"next":false,"total":30,"previous":false,"index":1,"pageSize":40,"list":[{"linker":"邹亚楠","xy":"116.518318，39.923969","address":"北京市朝阳区朝阳北路101号朝阳大悦城2F-206号","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"北京朝北大悦城","tel":"010 85519493","pic":"/store/1501/f4f524176fe5a12ad1333450c478a60c","bdxy":"116.524028,39.929707"},{"linker":"杨天龙","xy":"120.160805,30.274235","address":"杭州市下城区杭州大厦C座三楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州大厦店","tel":"0571-87660190","pic":"/2017/03/30/091921319732e7a6a707ecde9bd8db6c993aa1.jpg","bdxy":"120.1673,30.280009"},{"linker":"苏忠雷","xy":"120.215546,30.251709","address":"浙江省杭州市江干区富春路701号万象城L409 dD2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州万象城","tel":"0571-85291819","pic":"/2017/03/30/09193717feffc80c163a6740127de60fd73f3d.jpg","bdxy":"120.220671,30.257801"},{"linker":"刘晓渝","xy":"106.518475，29.518038","address":"重庆市九龙坡区重庆华润中心万象城第 LG层LG193号商铺","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆万象城","tel":"023-68419856","pic":"/2017/03/30/0919561549079055ef642ef0e62ecda91b84ba.jpg","bdxy":"106.525389,29.522749"},{"linker":"黄媛","xy":"118.784716，32.044569","address":"江苏省南京市玄武区中山路18号德基广场4层F41","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南京德基广场(二期)","tel":"025-86777422","pic":"/2017/03/30/09201134ae5336fbc19055d69c521a6b943591.jpg","bdxy":"118.79086,32.051663"},{"linker":"蒋慎兴","xy":"102.707856，25.035775","address":"云南省昆明市崇仁街9号顺城购物中心E2-08A","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"昆明顺城","tel":"0871-63637720","pic":"/2017/03/30/092027467100eb2cb73e875065317225293367.jpg","bdxy":"102.714605,25.042513"},{"linker":"朱立夫","xy":"117.292526，31.8625","address":"安徽省合肥市长江中路98号合肥银泰3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"合肥银泰","tel":"0551-65852159","pic":"/2017/03/30/0920452d4752556250edd876fd2281ae004578.jpg","bdxy":"117.299285,31.867952"},{"linker":"宋巍","xy":"125.319768,43.891235","address":"吉林省长春市朝阳区重庆路1255号卓展A座购物中心4楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"长春卓展","tel":"0431-88483292","pic":"/2017/03/30/09211184774610dfee786eb147a7717ebb3c02.jpg","bdxy":"125.325569,43.896316"},{"linker":"宋巍","xy":"125.305876,43.863768","address":"长春市朝阳区工农大路99号欧亚新生活购物广场","weixin":null,"storeService":"1","name":"长春新生活购物广场","tel":"0431-82531065","pic":"/2017/08/10/054746b22952ad544d11b50c589ef704b11918.jpg","bdxy":"125.31081,43.867958"},{"linker":"徐洁","xy":"120.446085,31.521056","address":"江苏省无锡市新吴区（原新区）锡勤路18-28号，无锡百联奥特莱斯A5-107","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡奥特莱斯","tel":"13921277287","pic":"/2017/07/11/05153697de48cf95ea2b97781addd4f76cf837.jpg","bdxy":"120.454998,31.527282"},{"linker":"刘婷婷","xy":"87.588218,43.820159","address":"新疆乌鲁木齐市沙依巴克区友好北路 美美购物中心 二楼 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"新疆乌鲁木齐美美百货","tel":"18167820643","pic":"/2017/07/11/0524582827b41663a13f699d530e74a039afae.jpg","bdxy":"87.593653,43.827073"},{"linker":"陈晓娣","xy":"118.125418,24.499286","address":"福建省厦门市嘉禾路厦门SM城市广场二期蓝宝石二楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门SM广场店","tel":"0592-5062967","pic":"/2017/03/30/092127f89909d201c45b165f5914ff6a12fbdd.jpg","bdxy":"118.13182,24.505316"},{"linker":"李群","xy":"120.302486,31.572003","address":"江苏省无锡市梁溪区中山路168号 无锡八佰半 2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡八佰伴","tel":"0510-81790961","pic":"/2017/03/30/092141a74cbff9ceba9280a3e5c78ff414dce4.jpg","bdxy":"120.308994,31.577594"},{"linker":"华雪","xy":"125.300673,43.870022","address":"吉林省长春市工农大路1218号，欧亚商都  3楼   D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚商都","tel":"0431-85187393","pic":"/2017/03/30/092203679112017533ea8c1f0b700e6991f1cd.jpg","bdxy":"125.306956,43.876137"},{"linker":"谌秋","xy":"115.898279,28.674713","address":"江西省南昌市红谷滩新区庐山南大道铜锣湾广场2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南昌铜锣湾","tel":"18521785121","pic":"/2017/03/30/0922181eef25de9bea201e72803fc6328d6da9.jpg","bdxy":"115.904843,28.680728"},{"linker":"束婷婷","xy":"123.45735,41.742811","address":"沈阳市浑南新区营盘北街5号兴隆大奥莱A座3楼D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"兴隆大奥莱","tel":"024-31263091","pic":"/2017/03/30/092240c0b26b03ba3ad3651e21719de8e08dd7.jpg","bdxy":"123.465486,41.747885"},{"linker":"宋巍","xy":"125.243267,43.84068","address":"吉林省长春市朝阳区欧亚卖场开运街5178号  一楼9号门  D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚大卖场","tel":"0431-85529040","pic":"/2017/03/30/0924266d92d5510a0f494317bbb6c6a781b646.jpg","bdxy":"125.243267,43.84068"},{"linker":"伊雯","xy":"117.18703,34.266249","address":"江苏省徐州市鼓楼区金鹰彭城广场店三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"徐州金鹰店","tel":"0516-83701665","pic":"/2017/03/30/092549018e657758aa3e54cda2bf73c4151c7d.jpg","bdxy":"117.18703,34.266249"},{"linker":"刘峰秀","xy":"129.616595,44.582325","address":"黑龙江省牡丹江市太平路146号波斯特购物中心2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"牡丹江百货","tel":"0453\u20146227609","pic":"/2017/03/30/092637593fd5472bef4c70af44da3938baa56d.jpg","bdxy":"129.623213,44.588966"},{"linker":"向兴红","xy":"106.533479,29.577737","address":"重庆江北区观音桥北城天街X-1F-003","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆北城天街店","tel":"023-67953056","pic":"/2017/03/30/092728014b7c6b416588932f5e420b7b82fac4.jpg","bdxy":"106.539725,29.584018"},{"linker":"郭杨","xy":"121.636647,38.916408","address":"大连市中山区解放路19号百年城225a D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"大连百年城","tel":"0411-65855239","pic":"/2017/03/30/092828e1799acf0a75b28d1680c17ccd3c76c0.jpg","bdxy":"121.636647,38.916408"},{"linker":"郑蓉","xy":"114.271518,30.579544","address":"湖北省武汉市江岸区解放大道690号武汉国际广场A区3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"武汉国际广场店","tel":"027-85321088","pic":"/2017/03/30/0929240fbecdfb1c6f51effac116749ca9d7d9.jpg","bdxy":"114.27745,30.586794"},{"linker":"朱惠珍","xy":"118.07829,24.453545","address":"福建省厦门市思明区中山路巴黎春天百货2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门巴黎春天","tel":"0592-2101719","pic":"/2017/03/30/0930248691f8f53ca1a283bee0881125a842f8.jpg","bdxy":"118.084405,24.459982"},{"linker":"黄霞","xy":"119.43274,32.393913","address":"江苏省扬州市汶河南路120号扬州金鹰购物中心文昌店3楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"江苏扬州金鹰","tel":"0514-87116163","pic":"/2017/03/30/0931267054da2b7b2c5bc0e8239f67e652179e.jpg","bdxy":"119.439911,32.399192"},{"linker":"范琨","xy":"126.64212,45.76035","address":"哈尔滨南岗区建设街142号尚都新天地2层","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"哈尔滨尚都店","tel":"0451-87588602","pic":"/2017/03/30/09330036782888dc1173ddd2e2d29552c14aa4.jpg","bdxy":"126.648419,45.766034"},{"linker":"束婷婷","xy":"123.434113,41.808996","address":"辽宁省沈阳市沈河区北京街7-1号卓展购物中心4F D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳卓展","tel":"024-22795149","pic":"/2017/03/30/093355a1f5b0e0e2efa98894d8a039c045e8ae.jpg","bdxy":"123.440391,41.813941"},{"linker":"李泽宇","xy":"108.33586,22.813544","address":"广西省南宁市青秀区民族大道中段49号梦之岛百货（新梦）三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南宁梦之岛","tel":"0771-2859807","pic":"/2017/03/30/09360979f0b1c5c2dfbcdb227e6bcf6531a6c3.jpg","bdxy":"108.342352,22.819665"},{"linker":"李丽雪","xy":"123.43453,41.776016","address":"辽宁省沈阳市和平区青年大街286号华润万象城4F 415 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳万象城","tel":"024-31379266","pic":"/store/1508/3e5602d7aa31a4285f616374e8879af9","bdxy":"123.442127,41.783302"},{"linker":"于晓慧","xy":"120.378561,36.066597","address":"山东省青岛市市南区香港中路华润中心万象城L226B号D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"青岛万象城","tel":"13396482102","pic":"/store/1505/1679b6e892c0977b6ef11152971f7c11","bdxy":"120.384511,36.073066"},{"linker":"徐芳","xy":"114.51145,38.04281","address":"河北省石家庄中山东路188号北国商城三层淑女商场  D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"石家庄北国","tel":"0311-89669373","pic":"/store/1505/cdbc0c2ba158aa57ea9a20a0545de971","bdxy":"114.517956,38.048748"}]}}
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
         * stores : {"next":false,"total":30,"previous":false,"index":1,"pageSize":40,"list":[{"linker":"邹亚楠","xy":"116.518318，39.923969","address":"北京市朝阳区朝阳北路101号朝阳大悦城2F-206号","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"北京朝北大悦城","tel":"010 85519493","pic":"/store/1501/f4f524176fe5a12ad1333450c478a60c","bdxy":"116.524028,39.929707"},{"linker":"杨天龙","xy":"120.160805,30.274235","address":"杭州市下城区杭州大厦C座三楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州大厦店","tel":"0571-87660190","pic":"/2017/03/30/091921319732e7a6a707ecde9bd8db6c993aa1.jpg","bdxy":"120.1673,30.280009"},{"linker":"苏忠雷","xy":"120.215546,30.251709","address":"浙江省杭州市江干区富春路701号万象城L409 dD2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州万象城","tel":"0571-85291819","pic":"/2017/03/30/09193717feffc80c163a6740127de60fd73f3d.jpg","bdxy":"120.220671,30.257801"},{"linker":"刘晓渝","xy":"106.518475，29.518038","address":"重庆市九龙坡区重庆华润中心万象城第 LG层LG193号商铺","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆万象城","tel":"023-68419856","pic":"/2017/03/30/0919561549079055ef642ef0e62ecda91b84ba.jpg","bdxy":"106.525389,29.522749"},{"linker":"黄媛","xy":"118.784716，32.044569","address":"江苏省南京市玄武区中山路18号德基广场4层F41","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南京德基广场(二期)","tel":"025-86777422","pic":"/2017/03/30/09201134ae5336fbc19055d69c521a6b943591.jpg","bdxy":"118.79086,32.051663"},{"linker":"蒋慎兴","xy":"102.707856，25.035775","address":"云南省昆明市崇仁街9号顺城购物中心E2-08A","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"昆明顺城","tel":"0871-63637720","pic":"/2017/03/30/092027467100eb2cb73e875065317225293367.jpg","bdxy":"102.714605,25.042513"},{"linker":"朱立夫","xy":"117.292526，31.8625","address":"安徽省合肥市长江中路98号合肥银泰3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"合肥银泰","tel":"0551-65852159","pic":"/2017/03/30/0920452d4752556250edd876fd2281ae004578.jpg","bdxy":"117.299285,31.867952"},{"linker":"宋巍","xy":"125.319768,43.891235","address":"吉林省长春市朝阳区重庆路1255号卓展A座购物中心4楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"长春卓展","tel":"0431-88483292","pic":"/2017/03/30/09211184774610dfee786eb147a7717ebb3c02.jpg","bdxy":"125.325569,43.896316"},{"linker":"宋巍","xy":"125.305876,43.863768","address":"长春市朝阳区工农大路99号欧亚新生活购物广场","weixin":null,"storeService":"1","name":"长春新生活购物广场","tel":"0431-82531065","pic":"/2017/08/10/054746b22952ad544d11b50c589ef704b11918.jpg","bdxy":"125.31081,43.867958"},{"linker":"徐洁","xy":"120.446085,31.521056","address":"江苏省无锡市新吴区（原新区）锡勤路18-28号，无锡百联奥特莱斯A5-107","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡奥特莱斯","tel":"13921277287","pic":"/2017/07/11/05153697de48cf95ea2b97781addd4f76cf837.jpg","bdxy":"120.454998,31.527282"},{"linker":"刘婷婷","xy":"87.588218,43.820159","address":"新疆乌鲁木齐市沙依巴克区友好北路 美美购物中心 二楼 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"新疆乌鲁木齐美美百货","tel":"18167820643","pic":"/2017/07/11/0524582827b41663a13f699d530e74a039afae.jpg","bdxy":"87.593653,43.827073"},{"linker":"陈晓娣","xy":"118.125418,24.499286","address":"福建省厦门市嘉禾路厦门SM城市广场二期蓝宝石二楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门SM广场店","tel":"0592-5062967","pic":"/2017/03/30/092127f89909d201c45b165f5914ff6a12fbdd.jpg","bdxy":"118.13182,24.505316"},{"linker":"李群","xy":"120.302486,31.572003","address":"江苏省无锡市梁溪区中山路168号 无锡八佰半 2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡八佰伴","tel":"0510-81790961","pic":"/2017/03/30/092141a74cbff9ceba9280a3e5c78ff414dce4.jpg","bdxy":"120.308994,31.577594"},{"linker":"华雪","xy":"125.300673,43.870022","address":"吉林省长春市工农大路1218号，欧亚商都  3楼   D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚商都","tel":"0431-85187393","pic":"/2017/03/30/092203679112017533ea8c1f0b700e6991f1cd.jpg","bdxy":"125.306956,43.876137"},{"linker":"谌秋","xy":"115.898279,28.674713","address":"江西省南昌市红谷滩新区庐山南大道铜锣湾广场2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南昌铜锣湾","tel":"18521785121","pic":"/2017/03/30/0922181eef25de9bea201e72803fc6328d6da9.jpg","bdxy":"115.904843,28.680728"},{"linker":"束婷婷","xy":"123.45735,41.742811","address":"沈阳市浑南新区营盘北街5号兴隆大奥莱A座3楼D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"兴隆大奥莱","tel":"024-31263091","pic":"/2017/03/30/092240c0b26b03ba3ad3651e21719de8e08dd7.jpg","bdxy":"123.465486,41.747885"},{"linker":"宋巍","xy":"125.243267,43.84068","address":"吉林省长春市朝阳区欧亚卖场开运街5178号  一楼9号门  D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚大卖场","tel":"0431-85529040","pic":"/2017/03/30/0924266d92d5510a0f494317bbb6c6a781b646.jpg","bdxy":"125.243267,43.84068"},{"linker":"伊雯","xy":"117.18703,34.266249","address":"江苏省徐州市鼓楼区金鹰彭城广场店三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"徐州金鹰店","tel":"0516-83701665","pic":"/2017/03/30/092549018e657758aa3e54cda2bf73c4151c7d.jpg","bdxy":"117.18703,34.266249"},{"linker":"刘峰秀","xy":"129.616595,44.582325","address":"黑龙江省牡丹江市太平路146号波斯特购物中心2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"牡丹江百货","tel":"0453\u20146227609","pic":"/2017/03/30/092637593fd5472bef4c70af44da3938baa56d.jpg","bdxy":"129.623213,44.588966"},{"linker":"向兴红","xy":"106.533479,29.577737","address":"重庆江北区观音桥北城天街X-1F-003","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆北城天街店","tel":"023-67953056","pic":"/2017/03/30/092728014b7c6b416588932f5e420b7b82fac4.jpg","bdxy":"106.539725,29.584018"},{"linker":"郭杨","xy":"121.636647,38.916408","address":"大连市中山区解放路19号百年城225a D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"大连百年城","tel":"0411-65855239","pic":"/2017/03/30/092828e1799acf0a75b28d1680c17ccd3c76c0.jpg","bdxy":"121.636647,38.916408"},{"linker":"郑蓉","xy":"114.271518,30.579544","address":"湖北省武汉市江岸区解放大道690号武汉国际广场A区3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"武汉国际广场店","tel":"027-85321088","pic":"/2017/03/30/0929240fbecdfb1c6f51effac116749ca9d7d9.jpg","bdxy":"114.27745,30.586794"},{"linker":"朱惠珍","xy":"118.07829,24.453545","address":"福建省厦门市思明区中山路巴黎春天百货2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门巴黎春天","tel":"0592-2101719","pic":"/2017/03/30/0930248691f8f53ca1a283bee0881125a842f8.jpg","bdxy":"118.084405,24.459982"},{"linker":"黄霞","xy":"119.43274,32.393913","address":"江苏省扬州市汶河南路120号扬州金鹰购物中心文昌店3楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"江苏扬州金鹰","tel":"0514-87116163","pic":"/2017/03/30/0931267054da2b7b2c5bc0e8239f67e652179e.jpg","bdxy":"119.439911,32.399192"},{"linker":"范琨","xy":"126.64212,45.76035","address":"哈尔滨南岗区建设街142号尚都新天地2层","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"哈尔滨尚都店","tel":"0451-87588602","pic":"/2017/03/30/09330036782888dc1173ddd2e2d29552c14aa4.jpg","bdxy":"126.648419,45.766034"},{"linker":"束婷婷","xy":"123.434113,41.808996","address":"辽宁省沈阳市沈河区北京街7-1号卓展购物中心4F D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳卓展","tel":"024-22795149","pic":"/2017/03/30/093355a1f5b0e0e2efa98894d8a039c045e8ae.jpg","bdxy":"123.440391,41.813941"},{"linker":"李泽宇","xy":"108.33586,22.813544","address":"广西省南宁市青秀区民族大道中段49号梦之岛百货（新梦）三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南宁梦之岛","tel":"0771-2859807","pic":"/2017/03/30/09360979f0b1c5c2dfbcdb227e6bcf6531a6c3.jpg","bdxy":"108.342352,22.819665"},{"linker":"李丽雪","xy":"123.43453,41.776016","address":"辽宁省沈阳市和平区青年大街286号华润万象城4F 415 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳万象城","tel":"024-31379266","pic":"/store/1508/3e5602d7aa31a4285f616374e8879af9","bdxy":"123.442127,41.783302"},{"linker":"于晓慧","xy":"120.378561,36.066597","address":"山东省青岛市市南区香港中路华润中心万象城L226B号D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"青岛万象城","tel":"13396482102","pic":"/store/1505/1679b6e892c0977b6ef11152971f7c11","bdxy":"120.384511,36.073066"},{"linker":"徐芳","xy":"114.51145,38.04281","address":"河北省石家庄中山东路188号北国商城三层淑女商场  D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"石家庄北国","tel":"0311-89669373","pic":"/store/1505/cdbc0c2ba158aa57ea9a20a0545de971","bdxy":"114.517956,38.048748"}]}
         */

        private StoresBean stores;

        public StoresBean getStores() {
            return stores;
        }

        public void setStores(StoresBean stores) {
            this.stores = stores;
        }

        public static class StoresBean {
            /**
             * next : false
             * total : 30
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"linker":"邹亚楠","xy":"116.518318，39.923969","address":"北京市朝阳区朝阳北路101号朝阳大悦城2F-206号","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"北京朝北大悦城","tel":"010 85519493","pic":"/store/1501/f4f524176fe5a12ad1333450c478a60c","bdxy":"116.524028,39.929707"},{"linker":"杨天龙","xy":"120.160805,30.274235","address":"杭州市下城区杭州大厦C座三楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州大厦店","tel":"0571-87660190","pic":"/2017/03/30/091921319732e7a6a707ecde9bd8db6c993aa1.jpg","bdxy":"120.1673,30.280009"},{"linker":"苏忠雷","xy":"120.215546,30.251709","address":"浙江省杭州市江干区富春路701号万象城L409 dD2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"杭州万象城","tel":"0571-85291819","pic":"/2017/03/30/09193717feffc80c163a6740127de60fd73f3d.jpg","bdxy":"120.220671,30.257801"},{"linker":"刘晓渝","xy":"106.518475，29.518038","address":"重庆市九龙坡区重庆华润中心万象城第 LG层LG193号商铺","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆万象城","tel":"023-68419856","pic":"/2017/03/30/0919561549079055ef642ef0e62ecda91b84ba.jpg","bdxy":"106.525389,29.522749"},{"linker":"黄媛","xy":"118.784716，32.044569","address":"江苏省南京市玄武区中山路18号德基广场4层F41","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南京德基广场(二期)","tel":"025-86777422","pic":"/2017/03/30/09201134ae5336fbc19055d69c521a6b943591.jpg","bdxy":"118.79086,32.051663"},{"linker":"蒋慎兴","xy":"102.707856，25.035775","address":"云南省昆明市崇仁街9号顺城购物中心E2-08A","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"昆明顺城","tel":"0871-63637720","pic":"/2017/03/30/092027467100eb2cb73e875065317225293367.jpg","bdxy":"102.714605,25.042513"},{"linker":"朱立夫","xy":"117.292526，31.8625","address":"安徽省合肥市长江中路98号合肥银泰3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"合肥银泰","tel":"0551-65852159","pic":"/2017/03/30/0920452d4752556250edd876fd2281ae004578.jpg","bdxy":"117.299285,31.867952"},{"linker":"宋巍","xy":"125.319768,43.891235","address":"吉林省长春市朝阳区重庆路1255号卓展A座购物中心4楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"长春卓展","tel":"0431-88483292","pic":"/2017/03/30/09211184774610dfee786eb147a7717ebb3c02.jpg","bdxy":"125.325569,43.896316"},{"linker":"宋巍","xy":"125.305876,43.863768","address":"长春市朝阳区工农大路99号欧亚新生活购物广场","weixin":null,"storeService":"1","name":"长春新生活购物广场","tel":"0431-82531065","pic":"/2017/08/10/054746b22952ad544d11b50c589ef704b11918.jpg","bdxy":"125.31081,43.867958"},{"linker":"徐洁","xy":"120.446085,31.521056","address":"江苏省无锡市新吴区（原新区）锡勤路18-28号，无锡百联奥特莱斯A5-107","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡奥特莱斯","tel":"13921277287","pic":"/2017/07/11/05153697de48cf95ea2b97781addd4f76cf837.jpg","bdxy":"120.454998,31.527282"},{"linker":"刘婷婷","xy":"87.588218,43.820159","address":"新疆乌鲁木齐市沙依巴克区友好北路 美美购物中心 二楼 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"新疆乌鲁木齐美美百货","tel":"18167820643","pic":"/2017/07/11/0524582827b41663a13f699d530e74a039afae.jpg","bdxy":"87.593653,43.827073"},{"linker":"陈晓娣","xy":"118.125418,24.499286","address":"福建省厦门市嘉禾路厦门SM城市广场二期蓝宝石二楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门SM广场店","tel":"0592-5062967","pic":"/2017/03/30/092127f89909d201c45b165f5914ff6a12fbdd.jpg","bdxy":"118.13182,24.505316"},{"linker":"李群","xy":"120.302486,31.572003","address":"江苏省无锡市梁溪区中山路168号 无锡八佰半 2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"无锡八佰伴","tel":"0510-81790961","pic":"/2017/03/30/092141a74cbff9ceba9280a3e5c78ff414dce4.jpg","bdxy":"120.308994,31.577594"},{"linker":"华雪","xy":"125.300673,43.870022","address":"吉林省长春市工农大路1218号，欧亚商都  3楼   D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚商都","tel":"0431-85187393","pic":"/2017/03/30/092203679112017533ea8c1f0b700e6991f1cd.jpg","bdxy":"125.306956,43.876137"},{"linker":"谌秋","xy":"115.898279,28.674713","address":"江西省南昌市红谷滩新区庐山南大道铜锣湾广场2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南昌铜锣湾","tel":"18521785121","pic":"/2017/03/30/0922181eef25de9bea201e72803fc6328d6da9.jpg","bdxy":"115.904843,28.680728"},{"linker":"束婷婷","xy":"123.45735,41.742811","address":"沈阳市浑南新区营盘北街5号兴隆大奥莱A座3楼D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"兴隆大奥莱","tel":"024-31263091","pic":"/2017/03/30/092240c0b26b03ba3ad3651e21719de8e08dd7.jpg","bdxy":"123.465486,41.747885"},{"linker":"宋巍","xy":"125.243267,43.84068","address":"吉林省长春市朝阳区欧亚卖场开运街5178号  一楼9号门  D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"欧亚大卖场","tel":"0431-85529040","pic":"/2017/03/30/0924266d92d5510a0f494317bbb6c6a781b646.jpg","bdxy":"125.243267,43.84068"},{"linker":"伊雯","xy":"117.18703,34.266249","address":"江苏省徐州市鼓楼区金鹰彭城广场店三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"徐州金鹰店","tel":"0516-83701665","pic":"/2017/03/30/092549018e657758aa3e54cda2bf73c4151c7d.jpg","bdxy":"117.18703,34.266249"},{"linker":"刘峰秀","xy":"129.616595,44.582325","address":"黑龙江省牡丹江市太平路146号波斯特购物中心2楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"牡丹江百货","tel":"0453\u20146227609","pic":"/2017/03/30/092637593fd5472bef4c70af44da3938baa56d.jpg","bdxy":"129.623213,44.588966"},{"linker":"向兴红","xy":"106.533479,29.577737","address":"重庆江北区观音桥北城天街X-1F-003","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"重庆北城天街店","tel":"023-67953056","pic":"/2017/03/30/092728014b7c6b416588932f5e420b7b82fac4.jpg","bdxy":"106.539725,29.584018"},{"linker":"郭杨","xy":"121.636647,38.916408","address":"大连市中山区解放路19号百年城225a D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"大连百年城","tel":"0411-65855239","pic":"/2017/03/30/092828e1799acf0a75b28d1680c17ccd3c76c0.jpg","bdxy":"121.636647,38.916408"},{"linker":"郑蓉","xy":"114.271518,30.579544","address":"湖北省武汉市江岸区解放大道690号武汉国际广场A区3楼","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"武汉国际广场店","tel":"027-85321088","pic":"/2017/03/30/0929240fbecdfb1c6f51effac116749ca9d7d9.jpg","bdxy":"114.27745,30.586794"},{"linker":"朱惠珍","xy":"118.07829,24.453545","address":"福建省厦门市思明区中山路巴黎春天百货2楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"厦门巴黎春天","tel":"0592-2101719","pic":"/2017/03/30/0930248691f8f53ca1a283bee0881125a842f8.jpg","bdxy":"118.084405,24.459982"},{"linker":"黄霞","xy":"119.43274,32.393913","address":"江苏省扬州市汶河南路120号扬州金鹰购物中心文昌店3楼 D2C 专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"江苏扬州金鹰","tel":"0514-87116163","pic":"/2017/03/30/0931267054da2b7b2c5bc0e8239f67e652179e.jpg","bdxy":"119.439911,32.399192"},{"linker":"范琨","xy":"126.64212,45.76035","address":"哈尔滨南岗区建设街142号尚都新天地2层","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"哈尔滨尚都店","tel":"0451-87588602","pic":"/2017/03/30/09330036782888dc1173ddd2e2d29552c14aa4.jpg","bdxy":"126.648419,45.766034"},{"linker":"束婷婷","xy":"123.434113,41.808996","address":"辽宁省沈阳市沈河区北京街7-1号卓展购物中心4F D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳卓展","tel":"024-22795149","pic":"/2017/03/30/093355a1f5b0e0e2efa98894d8a039c045e8ae.jpg","bdxy":"123.440391,41.813941"},{"linker":"李泽宇","xy":"108.33586,22.813544","address":"广西省南宁市青秀区民族大道中段49号梦之岛百货（新梦）三楼D2C专柜","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"南宁梦之岛","tel":"0771-2859807","pic":"/2017/03/30/09360979f0b1c5c2dfbcdb227e6bcf6531a6c3.jpg","bdxy":"108.342352,22.819665"},{"linker":"李丽雪","xy":"123.43453,41.776016","address":"辽宁省沈阳市和平区青年大街286号华润万象城4F 415 D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"沈阳万象城","tel":"024-31379266","pic":"/store/1508/3e5602d7aa31a4285f616374e8879af9","bdxy":"123.442127,41.783302"},{"linker":"于晓慧","xy":"120.378561,36.066597","address":"山东省青岛市市南区香港中路华润中心万象城L226B号D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"青岛万象城","tel":"13396482102","pic":"/store/1505/1679b6e892c0977b6ef11152971f7c11","bdxy":"120.384511,36.073066"},{"linker":"徐芳","xy":"114.51145,38.04281","address":"河北省石家庄中山东路188号北国商城三层淑女商场  D2C","weixin":null,"storeService":"咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家","name":"石家庄北国","tel":"0311-89669373","pic":"/store/1505/cdbc0c2ba158aa57ea9a20a0545de971","bdxy":"114.517956,38.048748"}]
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
                 * linker : 邹亚楠
                 * xy : 116.518318，39.923969
                 * address : 北京市朝阳区朝阳北路101号朝阳大悦城2F-206号
                 * weixin : null
                 * storeService : 咖啡,精品甜点,拎包服务,免费寄存,私人形象顾问,免费顺丰到家
                 * name : 北京朝北大悦城
                 * tel : 010 85519493
                 * pic : /store/1501/f4f524176fe5a12ad1333450c478a60c
                 * bdxy : 116.524028,39.929707
                 */

                private String linker;
                private String xy;
                private String address;
                private String weixin;
                private String storeService;
                private String name;
                private String tel;
                private String pic;
                private String bdxy;
                private double result;

                public String getLinker() {
                    return linker;
                }

                public void setLinker(String linker) {
                    this.linker = linker;
                }

                public String getXy() {
                    return xy;
                }

                public void setXy(String xy) {
                    this.xy = xy;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getWeixin() {
                    return weixin;
                }

                public void setWeixin(String weixin) {
                    this.weixin = weixin;
                }

                public String getStoreService() {
                    return storeService;
                }

                public void setStoreService(String storeService) {
                    this.storeService = storeService;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getBdxy() {
                    return bdxy;
                }

                public void setBdxy(String bdxy) {
                    this.bdxy = bdxy;
                }

                public double getResult() {
                    return result;
                }

                public void setResult(double result) {
                    this.result = result;
                }
            }
        }
    }
}
