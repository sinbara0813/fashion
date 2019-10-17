package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 直播商品列表
 * Author: hrb
 * Date: 2016/08/02 17:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveProductListBean extends BaseBean {

    /**
     * data : {"products":{"next":true,"total":13,"previous":false,"index":1,"pageSize":10,"list":[{"img":"/pi/25/100825/a4c26ddfb6fc666c14050e8a34377a49","originalPrice":1429,"promotionPrice":1429,"sellPrice":1429,"soonPrice":1429,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/25/100825/a38ae240430823f73974e14a3f76c5f4","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1429,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100825,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1429,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1429,"name":"TENOS 任蓉蓉 圆领个性时尚拼接纯棉连衣裙SR441020015","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","originalPrice":1499,"promotionPrice":1499,"sellPrice":1499,"soonPrice":1499,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","code":"120","groupId":"0","name":"颜色","id":"110","value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":1499,"recomScore":2400,"isTopical":false,"productSellType":"SPOT","id":100831,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1499,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1499,"name":"TENOS 任蓉蓉 圆领时尚条纹无袖雪纺连衣裙SR441020026","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/36/100836/001b8622a4c683efc8e4493e3bdbf27a","originalPrice":569,"promotionPrice":569,"sellPrice":569,"soonPrice":569,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/36/100836/001b8622a4c683efc8e4493e3bdbf27a","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":569,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100836,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":569,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":569,"name":"TENOS 任蓉蓉 个性不规则拼接无袖雪纺上衣SR441209077","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","originalPrice":1129,"promotionPrice":1129,"sellPrice":1129,"soonPrice":1129,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1129,"recomScore":4900,"isTopical":false,"productSellType":"SPOT","id":100838,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1129,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1129,"name":"TENOS 任蓉蓉 修身百搭七分裤SR441090023","collectioned":0,"isRec":false,"mark":1,"categoryIds":1621},{"img":"/pi/39/100839/6679a59ae21ec096d57c00afefd0a04a","originalPrice":1259,"promotionPrice":1259,"sellPrice":1259,"soonPrice":1259,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/39/100839/6679a59ae21ec096d57c00afefd0a04a","code":"014","groupId":"0","name":"颜色","id":"36","value":"米白"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1259,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100839,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1259,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1259,"name":"TENOS 任蓉蓉 时尚修身立体裁剪连衣裙SR441020040","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/43/100843/3a4f3de5a5234e1656e196232b64e7a7","originalPrice":1159,"promotionPrice":1159,"sellPrice":1159,"soonPrice":1159,"isCart":1,"flashPrice":0,"categoryName":"上衣","colors":[{"img":"/sp/43/100843/3a4f3de5a5234e1656e196232b64e7a7","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1159,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100843,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1159,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1159,"name":"TENOS 任蓉蓉 圆领长袖个性拼接上衣SR441209036","collectioned":0,"isRec":false,"mark":1,"categoryIds":1601},{"img":"/pi/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","originalPrice":919,"promotionPrice":919,"sellPrice":919,"soonPrice":919,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":919,"recomScore":3500,"isTopical":false,"productSellType":"SPOT","id":100844,"brand":"TENOS","designerId":10026,"comments":2,"isSpot":true,"grossRatio":1,"salePrice":919,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":919,"name":"TENOS 任蓉蓉 时尚简约数码裁剪拼接上衣","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/70/100870/34c165fd0490eb3dce6ec6333eab1712","originalPrice":889,"promotionPrice":889,"sellPrice":889,"soonPrice":889,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/70/100870/34c165fd0490eb3dce6ec6333eab1712","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":889,"recomScore":2450,"isTopical":false,"productSellType":"SPOT","id":100870,"brand":"TENOS","designerId":10026,"comments":1,"isSpot":true,"grossRatio":1,"salePrice":889,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":889,"name":"TENOS 任蓉蓉 欧美时尚修身圆领无袖纯棉上衣SR441070030","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/80/100880/5cd5493340b5d51324103008080a5224","originalPrice":1549,"promotionPrice":1549,"sellPrice":1549,"soonPrice":1549,"isCart":1,"flashPrice":0,"categoryName":"半身裙","colors":[{"img":"/sp/80/100880/5cd5493340b5d51324103008080a5224","code":"036","groupId":"0","name":"颜色","id":"114","value":"驼色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1549,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100880,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1549,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1549,"name":"TENOS 任蓉蓉 高腰蓬蓬裙西装裙半身裙SR441010085","collectioned":0,"isRec":false,"mark":1,"categoryIds":1684},{"img":"/pi/88/100888/81d0a873617fdb489dcec0cf4133202b","originalPrice":1439,"promotionPrice":1439,"sellPrice":1439,"soonPrice":1439,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/88/100888/81d0a873617fdb489dcec0cf4133202b","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":1439,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100888,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1439,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1439,"name":"TENOS 任蓉蓉 个性时尚条纹网纱连衣裙SR441020044","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588}]}}
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
         * products : {"next":true,"total":13,"previous":false,"index":1,"pageSize":10,"list":[{"img":"/pi/25/100825/a4c26ddfb6fc666c14050e8a34377a49","originalPrice":1429,"promotionPrice":1429,"sellPrice":1429,"soonPrice":1429,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/25/100825/a38ae240430823f73974e14a3f76c5f4","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1429,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100825,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1429,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1429,"name":"TENOS 任蓉蓉 圆领个性时尚拼接纯棉连衣裙SR441020015","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","originalPrice":1499,"promotionPrice":1499,"sellPrice":1499,"soonPrice":1499,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","code":"120","groupId":"0","name":"颜色","id":"110","value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":1499,"recomScore":2400,"isTopical":false,"productSellType":"SPOT","id":100831,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1499,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1499,"name":"TENOS 任蓉蓉 圆领时尚条纹无袖雪纺连衣裙SR441020026","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/36/100836/001b8622a4c683efc8e4493e3bdbf27a","originalPrice":569,"promotionPrice":569,"sellPrice":569,"soonPrice":569,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/36/100836/001b8622a4c683efc8e4493e3bdbf27a","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":569,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100836,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":569,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":569,"name":"TENOS 任蓉蓉 个性不规则拼接无袖雪纺上衣SR441209077","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","originalPrice":1129,"promotionPrice":1129,"sellPrice":1129,"soonPrice":1129,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1129,"recomScore":4900,"isTopical":false,"productSellType":"SPOT","id":100838,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1129,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1129,"name":"TENOS 任蓉蓉 修身百搭七分裤SR441090023","collectioned":0,"isRec":false,"mark":1,"categoryIds":1621},{"img":"/pi/39/100839/6679a59ae21ec096d57c00afefd0a04a","originalPrice":1259,"promotionPrice":1259,"sellPrice":1259,"soonPrice":1259,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/39/100839/6679a59ae21ec096d57c00afefd0a04a","code":"014","groupId":"0","name":"颜色","id":"36","value":"米白"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1259,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100839,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1259,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1259,"name":"TENOS 任蓉蓉 时尚修身立体裁剪连衣裙SR441020040","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/43/100843/3a4f3de5a5234e1656e196232b64e7a7","originalPrice":1159,"promotionPrice":1159,"sellPrice":1159,"soonPrice":1159,"isCart":1,"flashPrice":0,"categoryName":"上衣","colors":[{"img":"/sp/43/100843/3a4f3de5a5234e1656e196232b64e7a7","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1159,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100843,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1159,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1159,"name":"TENOS 任蓉蓉 圆领长袖个性拼接上衣SR441209036","collectioned":0,"isRec":false,"mark":1,"categoryIds":1601},{"img":"/pi/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","originalPrice":919,"promotionPrice":919,"sellPrice":919,"soonPrice":919,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":919,"recomScore":3500,"isTopical":false,"productSellType":"SPOT","id":100844,"brand":"TENOS","designerId":10026,"comments":2,"isSpot":true,"grossRatio":1,"salePrice":919,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":919,"name":"TENOS 任蓉蓉 时尚简约数码裁剪拼接上衣","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/70/100870/34c165fd0490eb3dce6ec6333eab1712","originalPrice":889,"promotionPrice":889,"sellPrice":889,"soonPrice":889,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/70/100870/34c165fd0490eb3dce6ec6333eab1712","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":889,"recomScore":2450,"isTopical":false,"productSellType":"SPOT","id":100870,"brand":"TENOS","designerId":10026,"comments":1,"isSpot":true,"grossRatio":1,"salePrice":889,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":889,"name":"TENOS 任蓉蓉 欧美时尚修身圆领无袖纯棉上衣SR441070030","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/80/100880/5cd5493340b5d51324103008080a5224","originalPrice":1549,"promotionPrice":1549,"sellPrice":1549,"soonPrice":1549,"isCart":1,"flashPrice":0,"categoryName":"半身裙","colors":[{"img":"/sp/80/100880/5cd5493340b5d51324103008080a5224","code":"036","groupId":"0","name":"颜色","id":"114","value":"驼色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1549,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100880,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1549,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1549,"name":"TENOS 任蓉蓉 高腰蓬蓬裙西装裙半身裙SR441010085","collectioned":0,"isRec":false,"mark":1,"categoryIds":1684},{"img":"/pi/88/100888/81d0a873617fdb489dcec0cf4133202b","originalPrice":1439,"promotionPrice":1439,"sellPrice":1439,"soonPrice":1439,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/88/100888/81d0a873617fdb489dcec0cf4133202b","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":1439,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100888,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1439,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1439,"name":"TENOS 任蓉蓉 个性时尚条纹网纱连衣裙SR441020044","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588}]}
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
             * next : true
             * total : 13
             * previous : false
             * index : 1
             * pageSize : 10
             * list : [{"img":"/pi/25/100825/a4c26ddfb6fc666c14050e8a34377a49","originalPrice":1429,"promotionPrice":1429,"sellPrice":1429,"soonPrice":1429,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/25/100825/a38ae240430823f73974e14a3f76c5f4","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1429,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100825,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1429,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1429,"name":"TENOS 任蓉蓉 圆领个性时尚拼接纯棉连衣裙SR441020015","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","originalPrice":1499,"promotionPrice":1499,"sellPrice":1499,"soonPrice":1499,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/31/100831/e1db724d72d65cb4fa66f8f2ca859a81","code":"120","groupId":"0","name":"颜色","id":"110","value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":1499,"recomScore":2400,"isTopical":false,"productSellType":"SPOT","id":100831,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1499,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1499,"name":"TENOS 任蓉蓉 圆领时尚条纹无袖雪纺连衣裙SR441020026","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/36/100836/001b8622a4c683efc8e4493e3bdbf27a","originalPrice":569,"promotionPrice":569,"sellPrice":569,"soonPrice":569,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/36/100836/001b8622a4c683efc8e4493e3bdbf27a","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":569,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100836,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":569,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":569,"name":"TENOS 任蓉蓉 个性不规则拼接无袖雪纺上衣SR441209077","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","originalPrice":1129,"promotionPrice":1129,"sellPrice":1129,"soonPrice":1129,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/38/100838/bde23d9b38d661d9905e9d45ace7c3b8","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1129,"recomScore":4900,"isTopical":false,"productSellType":"SPOT","id":100838,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1129,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1129,"name":"TENOS 任蓉蓉 修身百搭七分裤SR441090023","collectioned":0,"isRec":false,"mark":1,"categoryIds":1621},{"img":"/pi/39/100839/6679a59ae21ec096d57c00afefd0a04a","originalPrice":1259,"promotionPrice":1259,"sellPrice":1259,"soonPrice":1259,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/39/100839/6679a59ae21ec096d57c00afefd0a04a","code":"014","groupId":"0","name":"颜色","id":"36","value":"米白"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1259,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100839,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1259,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1259,"name":"TENOS 任蓉蓉 时尚修身立体裁剪连衣裙SR441020040","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588},{"img":"/pi/43/100843/3a4f3de5a5234e1656e196232b64e7a7","originalPrice":1159,"promotionPrice":1159,"sellPrice":1159,"soonPrice":1159,"isCart":1,"flashPrice":0,"categoryName":"上衣","colors":[{"img":"/sp/43/100843/3a4f3de5a5234e1656e196232b64e7a7","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1159,"recomScore":2000,"isTopical":false,"productSellType":"SPOT","id":100843,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1159,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1159,"name":"TENOS 任蓉蓉 圆领长袖个性拼接上衣SR441209036","collectioned":0,"isRec":false,"mark":1,"categoryIds":1601},{"img":"/pi/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","originalPrice":919,"promotionPrice":919,"sellPrice":919,"soonPrice":919,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/44/100844/f93d7ac0665de2bf8ebc34b340a8b231","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"}],"price":919,"recomScore":3500,"isTopical":false,"productSellType":"SPOT","id":100844,"brand":"TENOS","designerId":10026,"comments":2,"isSpot":true,"grossRatio":1,"salePrice":919,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":919,"name":"TENOS 任蓉蓉 时尚简约数码裁剪拼接上衣","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/70/100870/34c165fd0490eb3dce6ec6333eab1712","originalPrice":889,"promotionPrice":889,"sellPrice":889,"soonPrice":889,"isCart":1,"flashPrice":0,"categoryName":"背心","colors":[{"img":"/sp/70/100870/34c165fd0490eb3dce6ec6333eab1712","code":"071","groupId":"0","name":"颜色","id":"71","value":"浅灰"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":889,"recomScore":2450,"isTopical":false,"productSellType":"SPOT","id":100870,"brand":"TENOS","designerId":10026,"comments":1,"isSpot":true,"grossRatio":1,"salePrice":889,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":889,"name":"TENOS 任蓉蓉 欧美时尚修身圆领无袖纯棉上衣SR441070030","collectioned":0,"isRec":false,"mark":1,"categoryIds":1593},{"img":"/pi/80/100880/5cd5493340b5d51324103008080a5224","originalPrice":1549,"promotionPrice":1549,"sellPrice":1549,"soonPrice":1549,"isCart":1,"flashPrice":0,"categoryName":"半身裙","colors":[{"img":"/sp/80/100880/5cd5493340b5d51324103008080a5224","code":"036","groupId":"0","name":"颜色","id":"114","value":"驼色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":1549,"recomScore":2200,"isTopical":false,"productSellType":"SPOT","id":100880,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1549,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1549,"name":"TENOS 任蓉蓉 高腰蓬蓬裙西装裙半身裙SR441010085","collectioned":0,"isRec":false,"mark":1,"categoryIds":1684},{"img":"/pi/88/100888/81d0a873617fdb489dcec0cf4133202b","originalPrice":1439,"promotionPrice":1439,"sellPrice":1439,"soonPrice":1439,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/sp/88/100888/81d0a873617fdb489dcec0cf4133202b","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":1439,"recomScore":2100,"isTopical":false,"productSellType":"SPOT","id":100888,"brand":"TENOS","designerId":10026,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1439,"secondRatio":0,"store":1,"designer":"任蓉蓉","consults":0,"isCrowd":false,"minPrice":1439,"name":"TENOS 任蓉蓉 个性时尚条纹网纱连衣裙SR441020044","collectioned":0,"isRec":false,"mark":1,"categoryIds":1588}]
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

            public static class ListBean implements Identifiable{
                /**
                 * img : /pi/25/100825/a4c26ddfb6fc666c14050e8a34377a49
                 * originalPrice : 1429.0
                 * promotionPrice : 1429.0
                 * sellPrice : 1429.0
                 * soonPrice : 1429.0
                 * isCart : 1
                 * flashPrice : 0.0
                 * categoryName : 连衣裙
                 * colors : [{"img":"/sp/25/100825/a38ae240430823f73974e14a3f76c5f4","code":"035","groupId":"0","name":"颜色","id":"46","value":"米黄"}]
                 * firstRatio : 0.0
                 * sizes : [{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}]
                 * price : 1429.0
                 * recomScore : 2100.0
                 * isTopical : false
                 * productSellType : SPOT
                 * id : 100825
                 * brand : TENOS
                 * designerId : 10026
                 * comments : 0
                 * isSpot : true
                 * grossRatio : 1.0
                 * salePrice : 1429.0
                 * secondRatio : 0.0
                 * store : 1
                 * designer : 任蓉蓉
                 * consults : 0
                 * isCrowd : false
                 * minPrice : 1429.0
                 * name : TENOS 任蓉蓉 圆领个性时尚拼接纯棉连衣裙SR441020015
                 * collectioned : 0
                 * isRec : false
                 * mark : 1
                 * categoryIds : 1588
                 */

                private String img;
                private double originalPrice;
                private double promotionPrice;
                private double sellPrice;
                private double soonPrice;
                private int isCart;
                private double flashPrice;
                private String categoryName;
                private double firstRatio;
                private double price;
                private double recomScore;
                private boolean isTopical;
                private String productSellType;
                private long id;
                private String brand;
                private int designerId;
                private int comments;
                private boolean isSpot;
                private double grossRatio;
                private double salePrice;
                private double secondRatio;
                private int store;
                private String designer;
                private int consults;
                private boolean isCrowd;
                private double minPrice;
                private String name;
                private int collectioned;
                private boolean isRec;
                private int mark;
                private int categoryId;
                private List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors;
                private List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public double getPromotionPrice() {
                    return promotionPrice;
                }

                public void setPromotionPrice(double promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public double getSellPrice() {
                    return sellPrice;
                }

                public void setSellPrice(double sellPrice) {
                    this.sellPrice = sellPrice;
                }

                public double getSoonPrice() {
                    return soonPrice;
                }

                public void setSoonPrice(double soonPrice) {
                    this.soonPrice = soonPrice;
                }

                public int getIsCart() {
                    return isCart;
                }

                public void setIsCart(int isCart) {
                    this.isCart = isCart;
                }

                public double getFlashPrice() {
                    return flashPrice;
                }

                public void setFlashPrice(double flashPrice) {
                    this.flashPrice = flashPrice;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public double getFirstRatio() {
                    return firstRatio;
                }

                public void setFirstRatio(double firstRatio) {
                    this.firstRatio = firstRatio;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getRecomScore() {
                    return recomScore;
                }

                public void setRecomScore(double recomScore) {
                    this.recomScore = recomScore;
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

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public int getComments() {
                    return comments;
                }

                public void setComments(int comments) {
                    this.comments = comments;
                }

                public boolean isIsSpot() {
                    return isSpot;
                }

                public void setIsSpot(boolean isSpot) {
                    this.isSpot = isSpot;
                }

                public double getGrossRatio() {
                    return grossRatio;
                }

                public void setGrossRatio(double grossRatio) {
                    this.grossRatio = grossRatio;
                }

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                public double getSecondRatio() {
                    return secondRatio;
                }

                public void setSecondRatio(double secondRatio) {
                    this.secondRatio = secondRatio;
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

                public int getCollectioned() {
                    return collectioned;
                }

                public void setCollectioned(int collectioned) {
                    this.collectioned = collectioned;
                }

                public boolean isIsRec() {
                    return isRec;
                }

                public void setIsRec(boolean isRec) {
                    this.isRec = isRec;
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

                public List<ProductDetailBean.DataBean.ProductBean.ColorsBean> getColors() {
                    return colors;
                }

                public void setColors(List<ProductDetailBean.DataBean.ProductBean.ColorsBean> colors) {
                    this.colors = colors;
                }

                public List<ProductDetailBean.DataBean.ProductBean.SizesBean> getSizes() {
                    return sizes;
                }

                public void setSizes(List<ProductDetailBean.DataBean.ProductBean.SizesBean> sizes) {
                    this.sizes = sizes;
                }

                public static class ColorsBean {
                    /**
                     * img : /sp/25/100825/a38ae240430823f73974e14a3f76c5f4
                     * code : 035
                     * groupId : 0
                     * name : 颜色
                     * id : 46
                     * value : 米黄
                     */

                    private String img;
                    private String code;
                    private String groupId;
                    private String name;
                    private String id;
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

                    public String getGroupId() {
                        return groupId;
                    }

                    public void setGroupId(String groupId) {
                        this.groupId = groupId;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
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
                     * code : 01
                     * groupId : 0
                     * name : 尺码
                     * id : 26
                     * value : S码
                     */

                    private String img;
                    private String code;
                    private String groupId;
                    private String name;
                    private String id;
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

                    public String getGroupId() {
                        return groupId;
                    }

                    public void setGroupId(String groupId) {
                        this.groupId = groupId;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
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
