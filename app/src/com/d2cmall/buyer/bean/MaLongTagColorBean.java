package com.d2cmall.buyer.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27.
 * Description : MaLongClassifyBean
 * 码隆识别颜色标签等
 */

public class MaLongTagColorBean {

    /**
     * is_err : 0
     * labels : ["浅红","深红","字母图案","皮草","都市优雅","秋冬"]
     * labels-en : ["Light Red","Dark Red","Letters","Fur","Urban_elegance","Fall_winter_season"]
     * request_id : 132d133c-f1f8-11e8-9dea-0242ac1c0905
     * results : [{"box":[0.07125307125307126,0.038,0.9054054054054054,0.752],"colors":[{"basic":"浅红","basic-cn":"浅红","basic-en":"Light Red","ncs":"2020-Y70R","pantone":"peach-beige","percent":0.67158,"rgb":[216,167,146],"w3c":"深鲑鱼红","w3c-cn":"深鲑鱼红","w3c-en":"DarkSalmon"},{"basic":"深红","basic-cn":"深红","basic-en":"Dark Red","ncs":"6020-Y80R","pantone":"mink","percent":0.32842,"rgb":[116,74,68],"w3c":"棕色","w3c-cn":"棕色","w3c-en":"Brown"}],"item":"皮草","item-en":"Fur","item-puid":"fur","item-score":0.8795130252838135,"textures":["字母图案"],"textures-en":["Letters"]}]
     * time : 1.0670881271362305
     */

    private int is_err;
    private String request_id;
    private double time;
    private List<String> labels;
    @SerializedName("labels-en")
    private List<String> labelsen;
    private List<ResultsBean> results;

    public int getIs_err() {
        return is_err;
    }

    public void setIs_err(int is_err) {
        this.is_err = is_err;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabelsen() {
        return labelsen;
    }

    public void setLabelsen(List<String> labelsen) {
        this.labelsen = labelsen;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * box : [0.07125307125307126,0.038,0.9054054054054054,0.752]
         * colors : [{"basic":"浅红","basic-cn":"浅红","basic-en":"Light Red","ncs":"2020-Y70R","pantone":"peach-beige","percent":0.67158,"rgb":[216,167,146],"w3c":"深鲑鱼红","w3c-cn":"深鲑鱼红","w3c-en":"DarkSalmon"},{"basic":"深红","basic-cn":"深红","basic-en":"Dark Red","ncs":"6020-Y80R","pantone":"mink","percent":0.32842,"rgb":[116,74,68],"w3c":"棕色","w3c-cn":"棕色","w3c-en":"Brown"}]
         * item : 皮草
         * item-en : Fur
         * item-puid : fur
         * item-score : 0.8795130252838135
         * textures : ["字母图案"]
         * textures-en : ["Letters"]
         */

        private String item;
        @SerializedName("item-en")
        private String itemen;
        @SerializedName("item-puid")
        private String itempuid;
        @SerializedName("item-score")
        private double itemscore;
        private List<Double> box;
        private List<ColorsBean> colors;
        private List<String> textures;
        @SerializedName("textures-en")
        private List<String> texturesen;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getItemen() {
            return itemen;
        }

        public void setItemen(String itemen) {
            this.itemen = itemen;
        }

        public String getItempuid() {
            return itempuid;
        }

        public void setItempuid(String itempuid) {
            this.itempuid = itempuid;
        }

        public double getItemscore() {
            return itemscore;
        }

        public void setItemscore(double itemscore) {
            this.itemscore = itemscore;
        }

        public List<Double> getBox() {
            return box;
        }

        public void setBox(List<Double> box) {
            this.box = box;
        }

        public List<ColorsBean> getColors() {
            return colors;
        }

        public void setColors(List<ColorsBean> colors) {
            this.colors = colors;
        }

        public List<String> getTextures() {
            return textures;
        }

        public void setTextures(List<String> textures) {
            this.textures = textures;
        }

        public List<String> getTexturesen() {
            return texturesen;
        }

        public void setTexturesen(List<String> texturesen) {
            this.texturesen = texturesen;
        }

        public static class ColorsBean {
            /**
             * basic : 浅红
             * basic-cn : 浅红
             * basic-en : Light Red
             * ncs : 2020-Y70R
             * pantone : peach-beige
             * percent : 0.67158
             * rgb : [216,167,146]
             * w3c : 深鲑鱼红
             * w3c-cn : 深鲑鱼红
             * w3c-en : DarkSalmon
             */

            private String basic;
            @SerializedName("basic-cn")
            private String basiccn;
            @SerializedName("basic-en")
            private String basicen;
            private String ncs;
            private String pantone;
            private double percent;
            private String w3c;
            @SerializedName("w3c-cn")
            private String w3ccn;
            @SerializedName("w3c-en")
            private String w3cen;
            private List<Integer> rgb;

            public String getBasic() {
                return basic;
            }

            public void setBasic(String basic) {
                this.basic = basic;
            }

            public String getBasiccn() {
                return basiccn;
            }

            public void setBasiccn(String basiccn) {
                this.basiccn = basiccn;
            }

            public String getBasicen() {
                return basicen;
            }

            public void setBasicen(String basicen) {
                this.basicen = basicen;
            }

            public String getNcs() {
                return ncs;
            }

            public void setNcs(String ncs) {
                this.ncs = ncs;
            }

            public String getPantone() {
                return pantone;
            }

            public void setPantone(String pantone) {
                this.pantone = pantone;
            }

            public double getPercent() {
                return percent;
            }

            public void setPercent(double percent) {
                this.percent = percent;
            }

            public String getW3c() {
                return w3c;
            }

            public void setW3c(String w3c) {
                this.w3c = w3c;
            }

            public String getW3ccn() {
                return w3ccn;
            }

            public void setW3ccn(String w3ccn) {
                this.w3ccn = w3ccn;
            }

            public String getW3cen() {
                return w3cen;
            }

            public void setW3cen(String w3cen) {
                this.w3cen = w3cen;
            }

            public List<Integer> getRgb() {
                return rgb;
            }

            public void setRgb(List<Integer> rgb) {
                this.rgb = rgb;
            }
        }
    }
}
