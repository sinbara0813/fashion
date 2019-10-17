package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2018/6/8.
 */

public class KaoLaConfirmBean extends BaseBean {

    /**
     * data : {"kaola":{"orderForm":{"needVerifyLevel":1,"orderCloseTime":43200,"orderAmount":1100,"payAmount":1617,"taxPayAmount":517,"packageList":[{"needVerifyLevel":1,"importType":1,"payAmount":1617,"packageOrder":0,"goodsList":[{"warehouseId":2,"goodsId":26765638,"goodsPayAmount":1100,"imageUrl":"http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg","goodsTaxAmount":517,"sku":{"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0},"goodsUnitPriceWithoutTax":1100,"goodsBuyNumber":1,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","composeTaxRate":0.47}],"taxPayAmount":517,"warehouse":{"warehouseNameAlias":"下沙保税仓库别名","warehouseId":2,"warehouseName":"下沙保税仓库"},"logisticsPayAmount":0,"goodsSource":"下沙保税仓库别名 发货"}],"logisticsPayAmount":0,"logisticsTaxAmount":0},"recMeg":"成功","recCode":200}}
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
         * kaola : {"orderForm":{"needVerifyLevel":1,"orderCloseTime":43200,"orderAmount":1100,"payAmount":1617,"taxPayAmount":517,"packageList":[{"needVerifyLevel":1,"importType":1,"payAmount":1617,"packageOrder":0,"goodsList":[{"warehouseId":2,"goodsId":26765638,"goodsPayAmount":1100,"imageUrl":"http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg","goodsTaxAmount":517,"sku":{"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0},"goodsUnitPriceWithoutTax":1100,"goodsBuyNumber":1,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","composeTaxRate":0.47}],"taxPayAmount":517,"warehouse":{"warehouseNameAlias":"下沙保税仓库别名","warehouseId":2,"warehouseName":"下沙保税仓库"},"logisticsPayAmount":0,"goodsSource":"下沙保税仓库别名 发货"}],"logisticsPayAmount":0,"logisticsTaxAmount":0},"recMeg":"成功","recCode":200}
         */

        private KaolaBean kaola;

        public KaolaBean getKaola() {
            return kaola;
        }

        public void setKaola(KaolaBean kaola) {
            this.kaola = kaola;
        }

        public static class KaolaBean {
            /**
             * orderForm : {"needVerifyLevel":1,"orderCloseTime":43200,"orderAmount":1100,"payAmount":1617,"taxPayAmount":517,"packageList":[{"needVerifyLevel":1,"importType":1,"payAmount":1617,"packageOrder":0,"goodsList":[{"warehouseId":2,"goodsId":26765638,"goodsPayAmount":1100,"imageUrl":"http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg","goodsTaxAmount":517,"sku":{"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0},"goodsUnitPriceWithoutTax":1100,"goodsBuyNumber":1,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","composeTaxRate":0.47}],"taxPayAmount":517,"warehouse":{"warehouseNameAlias":"下沙保税仓库别名","warehouseId":2,"warehouseName":"下沙保税仓库"},"logisticsPayAmount":0,"goodsSource":"下沙保税仓库别名 发货"}],"logisticsPayAmount":0,"logisticsTaxAmount":0}
             * recMeg : 成功
             * recCode : 200
             */

            private OrderFormBean orderForm;
            private String recMeg;
            private int recCode;
            private int subCode;

            public int getSubCode() {
                return subCode;
            }

            public void setSubCode(int subCode) {
                this.subCode = subCode;
            }

            public OrderFormBean getOrderForm() {
                return orderForm;
            }

            public void setOrderForm(OrderFormBean orderForm) {
                this.orderForm = orderForm;
            }

            public String getRecMeg() {
                return recMeg;
            }

            public void setRecMeg(String recMeg) {
                this.recMeg = recMeg;
            }

            public int getRecCode() {
                return recCode;
            }

            public void setRecCode(int recCode) {
                this.recCode = recCode;
            }

            public static class OrderFormBean {
                /**
                 * needVerifyLevel : 1
                 * orderCloseTime : 43200
                 * orderAmount : 1100.0
                 * payAmount : 1617.0
                 * taxPayAmount : 517.0
                 * packageList : [{"needVerifyLevel":1,"importType":1,"payAmount":1617,"packageOrder":0,"goodsList":[{"warehouseId":2,"goodsId":26765638,"goodsPayAmount":1100,"imageUrl":"http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg","goodsTaxAmount":517,"sku":{"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0},"goodsUnitPriceWithoutTax":1100,"goodsBuyNumber":1,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","composeTaxRate":0.47}],"taxPayAmount":517,"warehouse":{"warehouseNameAlias":"下沙保税仓库别名","warehouseId":2,"warehouseName":"下沙保税仓库"},"logisticsPayAmount":0,"goodsSource":"下沙保税仓库别名 发货"}]
                 * logisticsPayAmount : 0
                 * logisticsTaxAmount : 0.0
                 */

                private int needVerifyLevel;
                private int orderCloseTime;
                private double orderAmount;
                private double payAmount;
                private double taxPayAmount;
                private double logisticsPayAmount;
                private double logisticsTaxAmount;
                private List<PackageListBean> packageList;

                public int getNeedVerifyLevel() {
                    return needVerifyLevel;
                }

                public void setNeedVerifyLevel(int needVerifyLevel) {
                    this.needVerifyLevel = needVerifyLevel;
                }

                public int getOrderCloseTime() {
                    return orderCloseTime;
                }

                public void setOrderCloseTime(int orderCloseTime) {
                    this.orderCloseTime = orderCloseTime;
                }

                public double getOrderAmount() {
                    return orderAmount;
                }

                public void setOrderAmount(double orderAmount) {
                    this.orderAmount = orderAmount;
                }

                public double getPayAmount() {
                    return payAmount;
                }

                public void setPayAmount(double payAmount) {
                    this.payAmount = payAmount;
                }

                public double getTaxPayAmount() {
                    return taxPayAmount;
                }

                public void setTaxPayAmount(double taxPayAmount) {
                    this.taxPayAmount = taxPayAmount;
                }

                public double getLogisticsPayAmount() {
                    return logisticsPayAmount;
                }

                public void setLogisticsPayAmount(double logisticsPayAmount) {
                    this.logisticsPayAmount = logisticsPayAmount;
                }

                public double getLogisticsTaxAmount() {
                    return logisticsTaxAmount;
                }

                public void setLogisticsTaxAmount(double logisticsTaxAmount) {
                    this.logisticsTaxAmount = logisticsTaxAmount;
                }

                public List<PackageListBean> getPackageList() {
                    return packageList;
                }

                public void setPackageList(List<PackageListBean> packageList) {
                    this.packageList = packageList;
                }

                public static class PackageListBean {
                    /**
                     * needVerifyLevel : 1
                     * importType : 1
                     * payAmount : 1617.0
                     * packageOrder : 0
                     * goodsList : [{"warehouseId":2,"goodsId":26765638,"goodsPayAmount":1100,"imageUrl":"http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg","goodsTaxAmount":517,"sku":{"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0},"goodsUnitPriceWithoutTax":1100,"goodsBuyNumber":1,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","composeTaxRate":0.47}]
                     * taxPayAmount : 517.0
                     * warehouse : {"warehouseNameAlias":"下沙保税仓库别名","warehouseId":2,"warehouseName":"下沙保税仓库"}
                     * logisticsPayAmount : 0
                     * goodsSource : 下沙保税仓库别名 发货
                     */

                    private int needVerifyLevel;
                    private int importType;
                    private double payAmount;
                    private int packageOrder;
                    private double taxPayAmount;
                    private WarehouseBean warehouse;
                    private int logisticsPayAmount;
                    private String goodsSource;
                    private List<GoodsListBean> goodsList;

                    public int getNeedVerifyLevel() {
                        return needVerifyLevel;
                    }

                    public void setNeedVerifyLevel(int needVerifyLevel) {
                        this.needVerifyLevel = needVerifyLevel;
                    }

                    public int getImportType() {
                        return importType;
                    }

                    public void setImportType(int importType) {
                        this.importType = importType;
                    }

                    public double getPayAmount() {
                        return payAmount;
                    }

                    public void setPayAmount(double payAmount) {
                        this.payAmount = payAmount;
                    }

                    public int getPackageOrder() {
                        return packageOrder;
                    }

                    public void setPackageOrder(int packageOrder) {
                        this.packageOrder = packageOrder;
                    }

                    public double getTaxPayAmount() {
                        return taxPayAmount;
                    }

                    public void setTaxPayAmount(double taxPayAmount) {
                        this.taxPayAmount = taxPayAmount;
                    }

                    public WarehouseBean getWarehouse() {
                        return warehouse;
                    }

                    public void setWarehouse(WarehouseBean warehouse) {
                        this.warehouse = warehouse;
                    }

                    public int getLogisticsPayAmount() {
                        return logisticsPayAmount;
                    }

                    public void setLogisticsPayAmount(int logisticsPayAmount) {
                        this.logisticsPayAmount = logisticsPayAmount;
                    }

                    public String getGoodsSource() {
                        return goodsSource;
                    }

                    public void setGoodsSource(String goodsSource) {
                        this.goodsSource = goodsSource;
                    }

                    public List<GoodsListBean> getGoodsList() {
                        return goodsList;
                    }

                    public void setGoodsList(List<GoodsListBean> goodsList) {
                        this.goodsList = goodsList;
                    }

                    public static class WarehouseBean {
                        /**
                         * warehouseNameAlias : 下沙保税仓库别名
                         * warehouseId : 2
                         * warehouseName : 下沙保税仓库
                         */

                        private String warehouseNameAlias;
                        private int warehouseId;
                        private String warehouseName;

                        public String getWarehouseNameAlias() {
                            return warehouseNameAlias;
                        }

                        public void setWarehouseNameAlias(String warehouseNameAlias) {
                            this.warehouseNameAlias = warehouseNameAlias;
                        }

                        public int getWarehouseId() {
                            return warehouseId;
                        }

                        public void setWarehouseId(int warehouseId) {
                            this.warehouseId = warehouseId;
                        }

                        public String getWarehouseName() {
                            return warehouseName;
                        }

                        public void setWarehouseName(String warehouseName) {
                            this.warehouseName = warehouseName;
                        }
                    }

                    public static class GoodsListBean {
                        /**
                         * warehouseId : 2
                         * goodsId : 26765638
                         * goodsPayAmount : 1100.0
                         * imageUrl : http://haitao.nosdn4.127.net/irh4eykc91_800_800.jpg
                         * goodsTaxAmount : 517.0
                         * sku : {"actualCurrentPrice":220,"skuId":"26765638-86614c59eb2d26502e5219d91e1184a5","xyTaxRate":0}
                         * goodsUnitPriceWithoutTax : 1100.0
                         * goodsBuyNumber : 1
                         * skuId : 26765638-86614c59eb2d26502e5219d91e1184a5
                         * composeTaxRate : 0.47
                         */

                        private int warehouseId;
                        private String goodsId;
                        private double goodsPayAmount;
                        private String imageUrl;
                        private double goodsTaxAmount;
                        private SkuBean sku;
                        private double goodsUnitPriceWithoutTax;
                        private int goodsBuyNumber;
                        private String skuId;
                        private double composeTaxRate;

                        public int getWarehouseId() {
                            return warehouseId;
                        }

                        public void setWarehouseId(int warehouseId) {
                            this.warehouseId = warehouseId;
                        }

                        public String getGoodsId() {
                            return goodsId;
                        }

                        public void setGoodsId(String goodsId) {
                            this.goodsId = goodsId;
                        }

                        public double getGoodsPayAmount() {
                            return goodsPayAmount;
                        }

                        public void setGoodsPayAmount(double goodsPayAmount) {
                            this.goodsPayAmount = goodsPayAmount;
                        }

                        public String getImageUrl() {
                            return imageUrl;
                        }

                        public void setImageUrl(String imageUrl) {
                            this.imageUrl = imageUrl;
                        }

                        public double getGoodsTaxAmount() {
                            return goodsTaxAmount;
                        }

                        public void setGoodsTaxAmount(double goodsTaxAmount) {
                            this.goodsTaxAmount = goodsTaxAmount;
                        }

                        public SkuBean getSku() {
                            return sku;
                        }

                        public void setSku(SkuBean sku) {
                            this.sku = sku;
                        }

                        public double getGoodsUnitPriceWithoutTax() {
                            return goodsUnitPriceWithoutTax;
                        }

                        public void setGoodsUnitPriceWithoutTax(double goodsUnitPriceWithoutTax) {
                            this.goodsUnitPriceWithoutTax = goodsUnitPriceWithoutTax;
                        }

                        public int getGoodsBuyNumber() {
                            return goodsBuyNumber;
                        }

                        public void setGoodsBuyNumber(int goodsBuyNumber) {
                            this.goodsBuyNumber = goodsBuyNumber;
                        }

                        public String getSkuId() {
                            return skuId;
                        }

                        public void setSkuId(String skuId) {
                            this.skuId = skuId;
                        }

                        public double getComposeTaxRate() {
                            return composeTaxRate;
                        }

                        public void setComposeTaxRate(double composeTaxRate) {
                            this.composeTaxRate = composeTaxRate;
                        }

                        public static class SkuBean {
                            /**
                             * actualCurrentPrice : 220
                             * skuId : 26765638-86614c59eb2d26502e5219d91e1184a5
                             * xyTaxRate : 0
                             */

                            private double actualCurrentPrice;
                            private String skuId;
                            private double xyTaxRate;

                            public double getActualCurrentPrice() {
                                return actualCurrentPrice;
                            }

                            public void setActualCurrentPrice(double actualCurrentPrice) {
                                this.actualCurrentPrice = actualCurrentPrice;
                            }

                            public String getSkuId() {
                                return skuId;
                            }

                            public void setSkuId(String skuId) {
                                this.skuId = skuId;
                            }

                            public double getXyTaxRate() {
                                return xyTaxRate;
                            }

                            public void setXyTaxRate(double xyTaxRate) {
                                this.xyTaxRate = xyTaxRate;
                            }
                        }
                    }
                }
            }
        }
    }
}
