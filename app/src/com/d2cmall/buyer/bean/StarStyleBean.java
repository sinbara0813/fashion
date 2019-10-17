package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/14 16:57
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarStyleBean extends BaseBean {



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * products : {"next":false,"total":22,"previous":false,"index":1,"pageSize":40,"list":[{"designerId":10753,"img":"/2017/08/30/06205406c58276174de91b0eb6ad2ac8f13646.jpg","comments":65,"originalPrice":899,"isSpot":false,"promotionPrice":629,"store":1,"designer":"9631 cm","isCart":1,"categoryName":"平底鞋","colors":[{"img":"/2017/08/30/06205206c58276174de91b0eb6ad2ac8f13646.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"酒红色/黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"39"}],"price":899,"minPrice":899,"name":"9631cm真皮女鞋欧美拼色牛皮深口鞋英伦中性风平底鞋单鞋女牛津鞋","recomScore":5150,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170009,"mark":1,"categoryIds":1674},{"designerId":10687,"img":"/2017/09/06/091744ffe8a3643c312385e86390669e02fc8f.jpg","comments":32,"originalPrice":0.01,"isSpot":true,"promotionPrice":0.01,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"礼服","colors":[{"img":"/2017/09/06/091617b55a6e1d6a11798ccc548961bcc54761.jpg","code":"091","groupId":0,"name":"颜色","id":80,"value":"咖啡色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":0.01,"minPrice":0.01,"name":"到货通知","recomScore":3800,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170010,"mark":1,"categoryIds":1592},{"designerId":10687,"img":"/2017/09/11/06090245d20e86c24bf731849a251b1b77a383.png","comments":11,"originalPrice":100,"isSpot":true,"promotionPrice":70,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"迷你短裙","colors":[{"img":"/2017/09/11/06085145d20e86c24bf731849a251b1b77a383.png","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":100,"minPrice":100,"name":"迷你小短裙","recomScore":2950,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170013,"mark":1,"categoryIds":1685},{"designerId":10617,"img":"/2017/09/12/0825090409991f618c7963008a65bccb520278.jpg","comments":9,"originalPrice":100,"isSpot":true,"promotionPrice":70,"store":1,"designer":"时晨昳","isCart":1,"categoryName":"针织衫","colors":[{"img":"/2017/09/12/0824532e3f295f2e5910498a86aab32fbaf85e.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":100,"minPrice":100,"name":"测试商品","recomScore":2950,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170026,"mark":1,"categoryIds":1602},{"designerId":10315,"img":"/2017/09/13/025736ec81175ae7d00e85e6fe1683e7b3dd3e.png","comments":1,"originalPrice":698,"isSpot":true,"promotionPrice":489,"store":1,"designer":"沈恩绮","isCart":1,"categoryName":"礼服","colors":[{"img":"/2017/09/13/025721ec81175ae7d00e85e6fe1683e7b3dd3e.png","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":698,"minPrice":698,"name":"测试商品","recomScore":2550,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170027,"mark":1,"categoryIds":1592},{"designerId":11228,"img":"/2017/08/24/1201192cd93c60ee0fa9eab8ffc69a9a8987de.jpg","comments":0,"originalPrice":1328,"isSpot":false,"promotionPrice":1195,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1201162cd93c60ee0fa9eab8ffc69a9a8987de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"70C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"70D"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"75B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"75C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"75D"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"80B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":7,"value":"80C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":8,"value":"80D"}],"price":1195,"minPrice":1195,"name":"William's Angel 黑色蕾丝复古花纹塑身衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170006,"mark":1,"categoryIds":1929},{"designerId":10753,"img":"/2017/08/30/0617031bc5fd4aaa8d1598fbdf3aa0c4dc1ec9.jpg","comments":0,"originalPrice":699,"isSpot":false,"promotionPrice":629,"store":1,"designer":"9631 cm","isCart":1,"categoryName":"平底鞋","colors":[{"img":"/2017/08/30/0616411bc5fd4aaa8d1598fbdf3aa0c4dc1ec9.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"枪灰色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"39"}],"price":629,"minPrice":629,"name":"9631cm2017秋季新款真皮女鞋牛皮兔毛球百搭平底乐福鞋单鞋一脚蹬","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170008,"mark":1,"categoryIds":1674},{"designerId":11228,"img":"/2017/08/24/1142142e7e2fdaf4623c4e1b15eceac547f7c2.jpg","comments":0,"originalPrice":1620,"isSpot":false,"promotionPrice":1458,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1140072e7e2fdaf4623c4e1b15eceac547f7c2.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-88A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"170-96A"}],"price":1458,"minPrice":1458,"name":"William's Angel 吊脖深V流苏设计感睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170003,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1036175daae4f2ff5421dc4e5ba877395e69de.jpg","comments":0,"originalPrice":1780,"isSpot":false,"promotionPrice":1602,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1036145daae4f2ff5421dc4e5ba877395e69de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"}],"price":1602,"minPrice":1602,"name":"William's Angel 性感大V吊脖蕾丝睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":169999,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/11464572db935e0e15cceb2a1f47bb582e8753.jpg","comments":0,"originalPrice":1480,"isSpot":false,"promotionPrice":1332,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/11464072db935e0e15cceb2a1f47bb582e8753.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-88A"}],"price":1332,"minPrice":1332,"name":"William's Angel 吊脖深V吊袜设计感睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170004,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1151149e99b4763c3402f175e25548a95726c0.jpg","comments":0,"originalPrice":2320,"isSpot":false,"promotionPrice":2088,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1151119e99b4763c3402f175e25548a95726c0.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"170-92A"}],"price":2088,"minPrice":2088,"name":"William's Angel 深V款长裙","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170005,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/102935b44bbcad3725619315eba14f74d77dc6.jpg","comments":0,"originalPrice":1780,"isSpot":false,"promotionPrice":1602,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/102933b44bbcad3725619315eba14f74d77dc6.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"70A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"70B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"70C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"75A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"75B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"75C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":7,"value":"80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":8,"value":"80B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":9,"value":"80C"}],"price":1602,"minPrice":1602,"name":"William's Angel 性感大V吊脖蕾丝睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":169998,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1050105daae4f2ff5421dc4e5ba877395e69de.jpg","comments":0,"originalPrice":1800,"isSpot":false,"promotionPrice":1620,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1050085daae4f2ff5421dc4e5ba877395e69de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"}],"price":1620,"minPrice":1620,"name":"William's Angel 吊带紧身蕾丝睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170000,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/105910a462d4b57212d410b4bcd8504eafa029.jpg","comments":0,"originalPrice":1980,"isSpot":false,"promotionPrice":1782,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/105908a462d4b57212d410b4bcd8504eafa029.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"165-88A"}],"price":1782,"minPrice":1782,"name":"William's Angel 抹胸流苏睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170001,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1104221aa707571db63bda49025d7b49f3d7e8.jpg","comments":0,"originalPrice":1680,"isSpot":false,"promotionPrice":1512,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1104191aa707571db63bda49025d7b49f3d7e8.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"165-88A"}],"price":1512,"minPrice":1512,"name":"William's Angel 大露背深V性感诱人情趣睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170002,"mark":1,"categoryIds":1929},{"designerId":10687,"img":"/2017/09/13/085354fd4aadc8dab471ed61b99dbc67ee1e33.jpg","comments":12,"originalPrice":1,"isSpot":false,"promotionPrice":1,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"针织衫","colors":[{"img":"/2017/09/13/085320f380c5a11deffcb3b1700a1864ab9757.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"},{"img":"","code":"0","groupId":0,"name":"颜色","id":2,"value":"自定义"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"XL"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":""}],"price":1,"minPrice":1,"name":"测试商品","recomScore":0,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170029,"mark":1,"categoryIds":1602},{"designerId":10056,"img":"/pi/86/101586/94ac9d4b15338dd42e40767344e0da51","comments":14,"originalPrice":3800,"isSpot":true,"promotionPrice":3800,"store":1,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/1505/55e5e29f1af6af91305633c8c87f2275","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"},{"img":"/sp/1505/cd91de68639a0c57149cc8d6e3c30b90","code":"061","groupId":0,"name":"颜色","id":95,"value":"蓝色"},{"img":"/sp/1505/dea1ab563884664548d748f54991b48c","code":"058","groupId":0,"name":"颜色","id":93,"value":"绿色"},{"img":"/sp/1505/5230848395380e9ebb4347925a008465","code":"043","groupId":0,"name":"颜色","id":50,"value":"粉色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":0,"name":"尺码","id":137,"value":"36码"},{"img":"","code":"37","groupId":0,"name":"尺码","id":138,"value":"37码"},{"img":"","code":"39","groupId":0,"name":"尺码","id":140,"value":"39码"},{"img":"","code":"40","groupId":0,"name":"尺码","id":141,"value":"40码"},{"img":"","code":"41","groupId":0,"name":"尺码","id":142,"value":"41码"},{"img":"","code":"42","groupId":0,"name":"尺码","id":143,"value":"42码"},{"img":"","code":"43","groupId":0,"name":"尺码","id":144,"value":"43码"},{"img":"","code":"44","groupId":0,"name":"尺码","id":145,"value":"44码"},{"img":"","code":"38","groupId":0,"name":"尺码","id":139,"value":"38码"}],"price":3800,"minPrice":3800,"name":"万一方 SAMUEL YANG SIMULATION炫彩发光鞋 荧光鞋 夜光鞋 W00543830001","recomScore":2840,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":101586,"mark":1,"categoryIds":1665},{"designerId":11032,"img":"/2017/09/09/062413074f5bf461366bf3acccc2d91ce6c383.jpg","comments":0,"originalPrice":21,"isSpot":true,"promotionPrice":19,"store":1,"designer":"Pink bell","isCart":1,"categoryName":"背带裙","colors":[{"img":"/2017/09/09/062406074f5bf461366bf3acccc2d91ce6c383.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":19,"minPrice":19,"name":"test1","recomScore":2500,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":170011,"mark":1,"categoryIds":1864},{"designerId":10390,"img":"/2017/08/24/10285083dbb852e2bb461457fd98f0e079fcd5.jpg","comments":0,"originalPrice":2800,"isSpot":false,"promotionPrice":2520,"store":1,"designer":"朱熙越","isCart":1,"categoryName":"阔腿裤","colors":[{"img":"/2017/08/24/10284983dbb852e2bb461457fd98f0e079fcd5.jpg","code":"065","groupId":0,"name":"颜色","id":69,"value":"灰蓝"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":2520,"minPrice":2520,"name":"朱熙越阔腿裤","recomScore":648,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":169997,"mark":1,"categoryIds":2002},{"designerId":10056,"img":"/pi/37/102737/3475987ef13f694667aeb4b60997de0a","comments":1,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/37/102737/3475987ef13f694667aeb4b60997de0a","code":"104","groupId":"0","name":"颜色","id":"99","value":"黄色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1100,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102737,"mark":1,"categoryIds":1665},{"designerId":10056,"img":"/pi/91/102691/d821faca63bd0562451deb000ee20aa2","comments":0,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/91/102691/d821faca63bd0562451deb000ee20aa2","code":"061","groupId":"0","name":"颜色","id":"95","value":"蓝色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"},{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1015,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102691,"mark":1,"categoryIds":1665},{"designerId":10056,"img":"/pi/35/102735/c820751a0faeb6ad2bdcecaf14106bfd","comments":0,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/35/102735/c820751a0faeb6ad2bdcecaf14106bfd","code":"044","groupId":"0","name":"颜色","id":"92","value":"红色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1015,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102735,"mark":1,"categoryIds":1665}]}
         */

        private ProductsBean products;

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * next : false
             * total : 22
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"designerId":10753,"img":"/2017/08/30/06205406c58276174de91b0eb6ad2ac8f13646.jpg","comments":65,"originalPrice":899,"isSpot":false,"promotionPrice":629,"store":1,"designer":"9631 cm","isCart":1,"categoryName":"平底鞋","colors":[{"img":"/2017/08/30/06205206c58276174de91b0eb6ad2ac8f13646.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"酒红色/黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"39"}],"price":899,"minPrice":899,"name":"9631cm真皮女鞋欧美拼色牛皮深口鞋英伦中性风平底鞋单鞋女牛津鞋","recomScore":5150,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170009,"mark":1,"categoryIds":1674},{"designerId":10687,"img":"/2017/09/06/091744ffe8a3643c312385e86390669e02fc8f.jpg","comments":32,"originalPrice":0.01,"isSpot":true,"promotionPrice":0.01,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"礼服","colors":[{"img":"/2017/09/06/091617b55a6e1d6a11798ccc548961bcc54761.jpg","code":"091","groupId":0,"name":"颜色","id":80,"value":"咖啡色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":0.01,"minPrice":0.01,"name":"到货通知","recomScore":3800,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170010,"mark":1,"categoryIds":1592},{"designerId":10687,"img":"/2017/09/11/06090245d20e86c24bf731849a251b1b77a383.png","comments":11,"originalPrice":100,"isSpot":true,"promotionPrice":70,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"迷你短裙","colors":[{"img":"/2017/09/11/06085145d20e86c24bf731849a251b1b77a383.png","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":100,"minPrice":100,"name":"迷你小短裙","recomScore":2950,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170013,"mark":1,"categoryIds":1685},{"designerId":10617,"img":"/2017/09/12/0825090409991f618c7963008a65bccb520278.jpg","comments":9,"originalPrice":100,"isSpot":true,"promotionPrice":70,"store":1,"designer":"时晨昳","isCart":1,"categoryName":"针织衫","colors":[{"img":"/2017/09/12/0824532e3f295f2e5910498a86aab32fbaf85e.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":100,"minPrice":100,"name":"测试商品","recomScore":2950,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170026,"mark":1,"categoryIds":1602},{"designerId":10315,"img":"/2017/09/13/025736ec81175ae7d00e85e6fe1683e7b3dd3e.png","comments":1,"originalPrice":698,"isSpot":true,"promotionPrice":489,"store":1,"designer":"沈恩绮","isCart":1,"categoryName":"礼服","colors":[{"img":"/2017/09/13/025721ec81175ae7d00e85e6fe1683e7b3dd3e.png","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":698,"minPrice":698,"name":"测试商品","recomScore":2550,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170027,"mark":1,"categoryIds":1592},{"designerId":11228,"img":"/2017/08/24/1201192cd93c60ee0fa9eab8ffc69a9a8987de.jpg","comments":0,"originalPrice":1328,"isSpot":false,"promotionPrice":1195,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1201162cd93c60ee0fa9eab8ffc69a9a8987de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"70C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"70D"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"75B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"75C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"75D"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"80B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":7,"value":"80C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":8,"value":"80D"}],"price":1195,"minPrice":1195,"name":"William's Angel 黑色蕾丝复古花纹塑身衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170006,"mark":1,"categoryIds":1929},{"designerId":10753,"img":"/2017/08/30/0617031bc5fd4aaa8d1598fbdf3aa0c4dc1ec9.jpg","comments":0,"originalPrice":699,"isSpot":false,"promotionPrice":629,"store":1,"designer":"9631 cm","isCart":1,"categoryName":"平底鞋","colors":[{"img":"/2017/08/30/0616411bc5fd4aaa8d1598fbdf3aa0c4dc1ec9.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"枪灰色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"39"}],"price":629,"minPrice":629,"name":"9631cm2017秋季新款真皮女鞋牛皮兔毛球百搭平底乐福鞋单鞋一脚蹬","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170008,"mark":1,"categoryIds":1674},{"designerId":11228,"img":"/2017/08/24/1142142e7e2fdaf4623c4e1b15eceac547f7c2.jpg","comments":0,"originalPrice":1620,"isSpot":false,"promotionPrice":1458,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1140072e7e2fdaf4623c4e1b15eceac547f7c2.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-88A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"170-96A"}],"price":1458,"minPrice":1458,"name":"William's Angel 吊脖深V流苏设计感睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170003,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1036175daae4f2ff5421dc4e5ba877395e69de.jpg","comments":0,"originalPrice":1780,"isSpot":false,"promotionPrice":1602,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1036145daae4f2ff5421dc4e5ba877395e69de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"}],"price":1602,"minPrice":1602,"name":"William's Angel 性感大V吊脖蕾丝睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":169999,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/11464572db935e0e15cceb2a1f47bb582e8753.jpg","comments":0,"originalPrice":1480,"isSpot":false,"promotionPrice":1332,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/11464072db935e0e15cceb2a1f47bb582e8753.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-88A"}],"price":1332,"minPrice":1332,"name":"William's Angel 吊脖深V吊袜设计感睡衣","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170004,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1151149e99b4763c3402f175e25548a95726c0.jpg","comments":0,"originalPrice":2320,"isSpot":false,"promotionPrice":2088,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1151119e99b4763c3402f175e25548a95726c0.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"170-92A"}],"price":2088,"minPrice":2088,"name":"William's Angel 深V款长裙","recomScore":1200,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170005,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/102935b44bbcad3725619315eba14f74d77dc6.jpg","comments":0,"originalPrice":1780,"isSpot":false,"promotionPrice":1602,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/102933b44bbcad3725619315eba14f74d77dc6.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"70A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"70B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"70C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"75A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"75B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"75C"},{"img":"","code":"0","groupId":0,"name":"尺码","id":7,"value":"80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":8,"value":"80B"},{"img":"","code":"0","groupId":0,"name":"尺码","id":9,"value":"80C"}],"price":1602,"minPrice":1602,"name":"William's Angel 性感大V吊脖蕾丝睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":169998,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1050105daae4f2ff5421dc4e5ba877395e69de.jpg","comments":0,"originalPrice":1800,"isSpot":false,"promotionPrice":1620,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1050085daae4f2ff5421dc4e5ba877395e69de.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"}],"price":1620,"minPrice":1620,"name":"William's Angel 吊带紧身蕾丝睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170000,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/105910a462d4b57212d410b4bcd8504eafa029.jpg","comments":0,"originalPrice":1980,"isSpot":false,"promotionPrice":1782,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/105908a462d4b57212d410b4bcd8504eafa029.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"155-76A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"160-80A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"165-88A"}],"price":1782,"minPrice":1782,"name":"William's Angel 抹胸流苏睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170001,"mark":1,"categoryIds":1929},{"designerId":11228,"img":"/2017/08/24/1104221aa707571db63bda49025d7b49f3d7e8.jpg","comments":0,"originalPrice":1680,"isSpot":false,"promotionPrice":1512,"store":1,"designer":"Angel","isCart":1,"categoryName":"内衣内裤","colors":[{"img":"/2017/08/24/1104191aa707571db63bda49025d7b49f3d7e8.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"165-84A"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"165-88A"}],"price":1512,"minPrice":1512,"name":"William's Angel 大露背深V性感诱人情趣睡衣","recomScore":1000,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170002,"mark":1,"categoryIds":1929},{"designerId":10687,"img":"/2017/09/13/085354fd4aadc8dab471ed61b99dbc67ee1e33.jpg","comments":12,"originalPrice":1,"isSpot":false,"promotionPrice":1,"store":1,"designer":"朱超凡","isCart":1,"categoryName":"针织衫","colors":[{"img":"/2017/09/13/085320f380c5a11deffcb3b1700a1864ab9757.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"},{"img":"","code":"0","groupId":0,"name":"颜色","id":2,"value":"自定义"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"XL"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":""}],"price":1,"minPrice":1,"name":"测试商品","recomScore":0,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":170029,"mark":1,"categoryIds":1602},{"designerId":10056,"img":"/pi/86/101586/94ac9d4b15338dd42e40767344e0da51","comments":14,"originalPrice":3800,"isSpot":true,"promotionPrice":3800,"store":1,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/1505/55e5e29f1af6af91305633c8c87f2275","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"},{"img":"/sp/1505/cd91de68639a0c57149cc8d6e3c30b90","code":"061","groupId":0,"name":"颜色","id":95,"value":"蓝色"},{"img":"/sp/1505/dea1ab563884664548d748f54991b48c","code":"058","groupId":0,"name":"颜色","id":93,"value":"绿色"},{"img":"/sp/1505/5230848395380e9ebb4347925a008465","code":"043","groupId":0,"name":"颜色","id":50,"value":"粉色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":0,"name":"尺码","id":137,"value":"36码"},{"img":"","code":"37","groupId":0,"name":"尺码","id":138,"value":"37码"},{"img":"","code":"39","groupId":0,"name":"尺码","id":140,"value":"39码"},{"img":"","code":"40","groupId":0,"name":"尺码","id":141,"value":"40码"},{"img":"","code":"41","groupId":0,"name":"尺码","id":142,"value":"41码"},{"img":"","code":"42","groupId":0,"name":"尺码","id":143,"value":"42码"},{"img":"","code":"43","groupId":0,"name":"尺码","id":144,"value":"43码"},{"img":"","code":"44","groupId":0,"name":"尺码","id":145,"value":"44码"},{"img":"","code":"38","groupId":0,"name":"尺码","id":139,"value":"38码"}],"price":3800,"minPrice":3800,"name":"万一方 SAMUEL YANG SIMULATION炫彩发光鞋 荧光鞋 夜光鞋 W00543830001","recomScore":2840,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":101586,"mark":1,"categoryIds":1665},{"designerId":11032,"img":"/2017/09/09/062413074f5bf461366bf3acccc2d91ce6c383.jpg","comments":0,"originalPrice":21,"isSpot":true,"promotionPrice":19,"store":1,"designer":"Pink bell","isCart":1,"categoryName":"背带裙","colors":[{"img":"/2017/09/09/062406074f5bf461366bf3acccc2d91ce6c383.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"99","groupId":0,"name":"尺码","id":15,"value":"均码"}],"price":19,"minPrice":19,"name":"test1","recomScore":2500,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":170011,"mark":1,"categoryIds":1864},{"designerId":10390,"img":"/2017/08/24/10285083dbb852e2bb461457fd98f0e079fcd5.jpg","comments":0,"originalPrice":2800,"isSpot":false,"promotionPrice":2520,"store":1,"designer":"朱熙越","isCart":1,"categoryName":"阔腿裤","colors":[{"img":"/2017/08/24/10284983dbb852e2bb461457fd98f0e079fcd5.jpg","code":"065","groupId":0,"name":"颜色","id":69,"value":"灰蓝"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":2520,"minPrice":2520,"name":"朱熙越阔腿裤","recomScore":648,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":169997,"mark":1,"categoryIds":2002},{"designerId":10056,"img":"/pi/37/102737/3475987ef13f694667aeb4b60997de0a","comments":1,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/37/102737/3475987ef13f694667aeb4b60997de0a","code":"104","groupId":"0","name":"颜色","id":"99","value":"黄色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1100,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102737,"mark":1,"categoryIds":1665},{"designerId":10056,"img":"/pi/91/102691/d821faca63bd0562451deb000ee20aa2","comments":0,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/91/102691/d821faca63bd0562451deb000ee20aa2","code":"061","groupId":"0","name":"颜色","id":"95","value":"蓝色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"},{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1015,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102691,"mark":1,"categoryIds":1665},{"designerId":10056,"img":"/pi/35/102735/c820751a0faeb6ad2bdcecaf14106bfd","comments":0,"originalPrice":3950,"isSpot":false,"promotionPrice":3950,"store":0,"designer":"万一方","isCart":1,"categoryName":"休闲鞋","colors":[{"img":"/sp/35/102735/c820751a0faeb6ad2bdcecaf14106bfd","code":"044","groupId":"0","name":"颜色","id":"92","value":"红色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"36","groupId":"0","name":"尺码","id":"137","value":"36码"},{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"},{"img":"","code":"41","groupId":"0","name":"尺码","id":"142","value":"41码"},{"img":"","code":"42","groupId":"0","name":"尺码","id":"143","value":"42码"},{"img":"","code":"43","groupId":"0","name":"尺码","id":"144","value":"43码"},{"img":"","code":"44","groupId":"0","name":"尺码","id":"145","value":"44码"}],"price":3950,"minPrice":3950,"name":"万一方 SIMULATION 第二代炫彩发光鞋 三色荧光鞋/夜光鞋 W00543830004","recomScore":1015,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":102735,"mark":1,"categoryIds":1665}]
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
                public Integer getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(Integer promotionId) {
                    this.promotionId = promotionId;
                }

                /**
                 * designerId : 10753
                 * img : /2017/08/30/06205406c58276174de91b0eb6ad2ac8f13646.jpg
                 * comments : 65
                 * originalPrice : 899.0
                 * isSpot : false
                 * promotionPrice : 629.0
                 * store : 1
                 * designer : 9631 cm
                 * isCart : 1
                 * categoryName : 平底鞋
                 * colors : [{"img":"/2017/08/30/06205206c58276174de91b0eb6ad2ac8f13646.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"酒红色/黑色"}]
                 * consults : 0
                 * isCrowd : false
                 * sizes : [{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"39"}]
                 * price : 899.0
                 * minPrice : 899.0
                 * name : 9631cm真皮女鞋欧美拼色牛皮深口鞋英伦中性风平底鞋单鞋女牛津鞋
                 * recomScore : 5150.0
                 * collectioned : null
                 * isTopical : true
                 * productSellType : SPOT
                 * id : 170009
                 * mark : 1
                 * categoryIds : 1674
                 */
                private Integer promotionId;

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                private double salePrice;
                private int designerId;
                private String img;
                private int comments;
                private double originalPrice;
                private boolean isSpot;
                private double promotionPrice;
                private int store;
                private String designer;

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                private String brand;
                private int isCart;
                private String categoryName;
                private int consults;
                private boolean isCrowd;
                private double price;
                private double minPrice;
                private String name;
                private double recomScore;
                private Object collectioned;
                private boolean isTopical;
                private String productSellType;
                private int id;
                private int mark;
                private int categoryId;

                private List<ColorsBean> colors;
                private List<SizesBean> sizes;
                private String orderPromotionType;
                private long orderPromotionId;
                private String orderPromotionTypeName;
                private  String promotionTypeName;
                private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)

                private GoodsBean.DataBean.ProductsBean.ListBean.SoonPromotion soonPromotion;
                public String getProductTradeType() {
                    return productTradeType;
                }

                public void setProductTradeType(String productTradeType) {
                    this.productTradeType = productTradeType;
                }


                public GoodsBean.DataBean.ProductsBean.ListBean.SoonPromotion getSoonPromotion() {
                    return soonPromotion;
                }
                public void setSoonPromotion(GoodsBean.DataBean.ProductsBean.ListBean.SoonPromotion soonPromotion) {
                    this.soonPromotion = soonPromotion;
                }
                public Integer getFlashPromotionId() {
                    return flashPromotionId;
                }

                public void setFlashPromotionId(Integer flashPromotionId) {
                    this.flashPromotionId = flashPromotionId;
                }

                private Integer flashPromotionId;
                public String getPromotionTypeName() {
                    return promotionTypeName;
                }

                public void setPromotionTypeName(String promotionTypeName) {
                    this.promotionTypeName = promotionTypeName;
                }

                public String getOrderPromotionType() {
                    return orderPromotionType;
                }

                public void setOrderPromotionType(String orderPromotionType) {
                    this.orderPromotionType = orderPromotionType;
                }

                public long getOrderPromotionId() {
                    return orderPromotionId;
                }

                public void setOrderPromotionId(int orderPromotionId) {
                    this.orderPromotionId = orderPromotionId;
                }

                public String getOrderPromotionTypeName() {
                    return orderPromotionTypeName;
                }

                public void setOrderPromotionTypeName(String orderPromotionTypeName) {
                    this.orderPromotionTypeName = orderPromotionTypeName;
                }
                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getComments() {
                    return comments;
                }

                public void setComments(int comments) {
                    this.comments = comments;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public boolean isIsSpot() {
                    return isSpot;
                }

                public void setIsSpot(boolean isSpot) {
                    this.isSpot = isSpot;
                }

                public double getPromotionPrice() {
                    return promotionPrice;
                }

                public void setPromotionPrice(double promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public int getStore() {
                    return store;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                public String getDesigner() {
                    return designer;
                }

                public void setDesigner(String designer) {
                    this.designer = designer;
                }

                public int getIsCart() {
                    return isCart;
                }

                public void setIsCart(int isCart) {
                    this.isCart = isCart;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public int getConsults() {
                    return consults;
                }

                public void setConsults(int consults) {
                    this.consults = consults;
                }

                public boolean isIsCrowd() {
                    return isCrowd;
                }

                public void setIsCrowd(boolean isCrowd) {
                    this.isCrowd = isCrowd;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getMinPrice() {
                    return minPrice;
                }

                public void setMinPrice(double minPrice) {
                    this.minPrice = minPrice;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public double getRecomScore() {
                    return recomScore;
                }

                public void setRecomScore(double recomScore) {
                    this.recomScore = recomScore;
                }

                public Object getCollectioned() {
                    return collectioned;
                }

                public void setCollectioned(Object collectioned) {
                    this.collectioned = collectioned;
                }

                public boolean isIsTopical() {
                    return isTopical;
                }

                public void setIsTopical(boolean isTopical) {
                    this.isTopical = isTopical;
                }

                public String getProductSellType() {
                    return productSellType;
                }

                public void setProductSellType(String productSellType) {
                    this.productSellType = productSellType;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMark() {
                    return mark;
                }

                public void setMark(int mark) {
                    this.mark = mark;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public List<ColorsBean> getColors() {
                    return colors;
                }

                public void setColors(List<ColorsBean> colors) {
                    this.colors = colors;
                }

                public List<SizesBean> getSizes() {
                    return sizes;
                }

                public void setSizes(List<SizesBean> sizes) {
                    this.sizes = sizes;
                }

                public static class ColorsBean {
                    /**
                     * img : /2017/08/30/06205206c58276174de91b0eb6ad2ac8f13646.jpg
                     * code : 0
                     * groupId : 0
                     * name : 颜色
                     * id : 1
                     * value : 酒红色/黑色
                     */

                    private String img;
                    private String code;
                    private int groupId;
                    private String name;
                    private int id;
                    private String value;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public int getGroupId() {
                        return groupId;
                    }

                    public void setGroupId(int groupId) {
                        this.groupId = groupId;
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

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }

                public static class SizesBean {
                    /**
                     * img :
                     * code : 0
                     * groupId : 0
                     * name : 尺码
                     * id : 1
                     * value : 35
                     */

                    private String img;
                    private String code;
                    private int groupId;
                    private String name;
                    private int id;
                    private String value;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public int getGroupId() {
                        return groupId;
                    }

                    public void setGroupId(int groupId) {
                        this.groupId = groupId;
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

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }
    }
}
