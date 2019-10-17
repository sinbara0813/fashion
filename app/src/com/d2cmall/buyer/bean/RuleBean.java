package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * 充值规则
 * Author: hrb
 * Date: 2016/08/24 10:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RuleBean extends BaseBean {

    /**
     * data : {"rechargeRule":{"solution":null,"ruleTypeName":"满送(不支持提现)","name":null,"description":null,"startTime":null,"id":null,"endTime":null,"status":0}}
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
         * rechargeRule : {"solution":null,"ruleTypeName":"满送(不支持提现)","name":null,"description":null,"startTime":null,"id":null,"endTime":null,"status":0}
         */

        private RechargeRuleBean rechargeRule;

        public RechargeRuleBean getRechargeRule() {
            return rechargeRule;
        }

        public void setRechargeRule(RechargeRuleBean rechargeRule) {
            this.rechargeRule = rechargeRule;
        }

        public static class RechargeRuleBean {
            /**
             * solution : null
             * ruleTypeName : 满送(不支持提现)
             * name : null
             * description : null
             * startTime : null
             * id : null
             * endTime : null
             * status : 0
             */

            private String solution;
            private String ruleTypeName;
            private String name;
            private String description;
            private String startTime;
            private int limited;
            private long id;
            private String endTime;
            @SerializedName("status")
            private int statusX;

            public int getLimited() {
                return limited;
            }

            public void setLimited(int limited) {
                this.limited = limited;
            }

            public String getSolution() {
                return solution;
            }

            public void setSolution(String solution) {
                this.solution = solution;
            }

            public String getRuleTypeName() {
                return ruleTypeName;
            }

            public void setRuleTypeName(String ruleTypeName) {
                this.ruleTypeName = ruleTypeName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }
        }
    }
}
