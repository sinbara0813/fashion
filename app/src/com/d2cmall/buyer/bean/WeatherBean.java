package com.d2cmall.buyer.bean;

import java.util.List;

/**
 * 作者:Created by sinbara on 2018/11/22.
 * 邮箱:hrb940258169@163.com
 */

public class WeatherBean {

    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * aqi : {"city":{"aqi":"82","qlty":"良","pm25":"60","pm10":"108","no2":"49","so2":"15","co":"1.0","o3":"52"}}
         * basic : {"city":"杭州","cnty":"中国","id":"CN101210101","lat":"30.28745842","lon":"120.15357971","update":{"loc":"2018-11-23 10:45","utc":"2018-11-23 02:45"}}
         * daily_forecast : [{"astro":{"mr":"17:17","ms":"06:07","sr":"06:32","ss":"16:59"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2018-11-23","hum":"63","pcpn":"0.0","pop":"0","pres":"1027","tmp":{"max":"17","min":"8"},"uv":"6","vis":"20","wind":{"deg":"149","dir":"东南风","sc":"1-2","spd":"1"}},{"astro":{"mr":"18:06","ms":"07:12","sr":"06:33","ss":"16:59"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2018-11-24","hum":"73","pcpn":"0.0","pop":"0","pres":"1025","tmp":{"max":"19","min":"9"},"uv":"4","vis":"20","wind":{"deg":"83","dir":"东风","sc":"1-2","spd":"10"}},{"astro":{"mr":"19:01","ms":"08:17","sr":"06:34","ss":"16:59"},"cond":{"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"},"date":"2018-11-25","hum":"63","pcpn":"0.0","pop":"0","pres":"1024","tmp":{"max":"19","min":"11"},"uv":"1","vis":"20","wind":{"deg":"73","dir":"东北风","sc":"1-2","spd":"9"}}]
         * now : {"cond":{"code":"100","txt":"晴"},"fl":"12","hum":"51","pcpn":"0.0","pres":"1027","tmp":"14","vis":"8","wind":{"deg":"172","dir":"南风","sc":"2","spd":"6"}}
         * status : ok
         * suggestion : {"air":{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},"comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},"flu":{"brf":"较易发","txt":"天凉，昼夜温差较大，较易发生感冒，请适当增减衣服，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"适宜","txt":"天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。"},"trav":{"brf":"适宜","txt":"天气较好，温度适宜，是个好天气哦。这样的天气适宜旅游，您可以尽情地享受大自然的风光。"},"uv":{"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}}
         */

        private NowBean now;
        private String status;
        private SuggestionBean suggestion;



        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }




        public static class NowBean {
            /**
             * cond : {"code":"100","txt":"晴"}
             * fl : 12
             * hum : 51
             * pcpn : 0.0
             * pres : 1027
             * tmp : 14
             * vis : 8
             * wind : {"deg":"172","dir":"南风","sc":"2","spd":"6"}
             */

            private CondBean cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private WindBean wind;

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class CondBean {
                /**
                 * code : 100
                 * txt : 晴
                 */

                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class WindBean {
                /**
                 * deg : 172
                 * dir : 南风
                 * sc : 2
                 * spd : 6
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class SuggestionBean {
            /**
             * air : {"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}
             * comf : {"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"}
             * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
             * drsg : {"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"}
             * flu : {"brf":"较易发","txt":"天凉，昼夜温差较大，较易发生感冒，请适当增减衣服，体质较弱的朋友请注意适当防护。"}
             * sport : {"brf":"适宜","txt":"天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。"}
             * trav : {"brf":"适宜","txt":"天气较好，温度适宜，是个好天气哦。这样的天气适宜旅游，您可以尽情地享受大自然的风光。"}
             * uv : {"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}
             */

            private DrsgBean drsg;



            public DrsgBean getDrsg() {
                return drsg;
            }

            public void setDrsg(DrsgBean drsg) {
                this.drsg = drsg;
            }





            public static class DrsgBean {
                /**
                 * brf : 较舒适
                 * txt : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }




        }

    }
}
