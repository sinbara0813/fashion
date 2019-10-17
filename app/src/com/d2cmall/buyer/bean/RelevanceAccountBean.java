package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2018/1/31.
 */

public class RelevanceAccountBean extends BaseBean implements Serializable{

    /**
     * data : {"children":[{"unionId":"osc3TwCpLathR7bpEYv4vAvL07i0","source":"Weixin"},{"unionId":"EDA4689787913F27790F4F490C196029","source":"QQ"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable{
        private List<ChildrenBean> children;

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean  implements Serializable{
            /**
             * unionId : osc3TwCpLathR7bpEYv4vAvL07i0
             * source : Weixin
             */

            private String unionId;
            private String source;

            public String getUnionId() {
                return unionId;
            }

            public void setUnionId(String unionId) {
                this.unionId = unionId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }
    }
}
