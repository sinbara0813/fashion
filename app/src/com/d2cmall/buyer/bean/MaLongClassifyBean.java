package com.d2cmall.buyer.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27.
 * Description : MaLongClassifyBean
 * 码隆识别分类
 */

public class MaLongClassifyBean {

    /**
     * classify_time : 0.392
     * download_time : 0.000
     * preprocess_time : 0.000
     * request_id : 957cfaa0-f1f3-11e8-b12b-0242ac1c2b04
     * results : [{"category":"配饰|首饰","score":"0.8199239373"},{"category":"配饰|太阳镜","score":"0.0843017772"},{"category":"上装|吊带衫背心","score":"0.0789231360"},{"category":"上装|卫衣","score":"0.0121794995"},{"category":"上装|T恤","score":"0.0029245925"},{"category":"上装|上衣","score":"0.0013017851"},{"category":"上装|衬衫","score":"0.0004452909"}]
     */

    private String classify_time;
    private String download_time;
    private String preprocess_time;
    private String request_id;
    private List<ResultsBean> results;

    public String getClassify_time() {
        return classify_time;
    }

    public void setClassify_time(String classify_time) {
        this.classify_time = classify_time;
    }

    public String getDownload_time() {
        return download_time;
    }

    public void setDownload_time(String download_time) {
        this.download_time = download_time;
    }

    public String getPreprocess_time() {
        return preprocess_time;
    }

    public void setPreprocess_time(String preprocess_time) {
        this.preprocess_time = preprocess_time;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * category : 配饰|首饰
         * score : 0.8199239373
         */

        private String category;
        private String score;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
