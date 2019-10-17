package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

public class AddressListBean extends BaseBean {

    /**
     * addresses : {"index":1,"pageSize":40,"total":0,"previous":false,"next":false,"list":[{"id":168394,"isdefault":true,"name":"李三","mobile":"18796542358","street":"201","province":"江西省","city":"南昌市","district":"东湖区"},{"id":168399,"isdefault":false,"name":"安溪县","mobile":"15248795648","street":"3612","province":"北京市","city":"县","district":"延庆县"},{"id":168398,"isdefault":false,"name":"王五","mobile":"15869324587","street":"321","province":"吉林省","city":"长春市","district":"宽城区"},{"id":168397,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301c","province":"河北省","city":"石家庄市","district":"桥东区"},{"id":168396,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301c","province":"河北省","city":"石家庄市","district":"桥东区"},{"id":168395,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301","province":"河北省","city":"石家庄市","district":"桥东区"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 40
         * total : 0
         * previous : false
         * next : false
         * list : [{"id":168394,"isdefault":true,"name":"李三","mobile":"18796542358","street":"201","province":"江西省","city":"南昌市","district":"东湖区"},{"id":168399,"isdefault":false,"name":"安溪县","mobile":"15248795648","street":"3612","province":"北京市","city":"县","district":"延庆县"},{"id":168398,"isdefault":false,"name":"王五","mobile":"15869324587","street":"321","province":"吉林省","city":"长春市","district":"宽城区"},{"id":168397,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301c","province":"河北省","city":"石家庄市","district":"桥东区"},{"id":168396,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301c","province":"河北省","city":"石家庄市","district":"桥东区"},{"id":168395,"isdefault":false,"name":"李四","mobile":"15236548956","street":"301","province":"河北省","city":"石家庄市","district":"桥东区"}]
         */

        private AddressesEntity addresses;

        public void setAddresses(AddressesEntity addresses) {
            this.addresses = addresses;
        }

        public AddressesEntity getAddresses() {
            return addresses;
        }

        public static class AddressesEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 168394
             * isdefault : true
             * name : 李三
             * mobile : 18796542358
             * street : 201
             * province : 江西省
             * city : 南昌市
             * district : 东湖区
             */

            private List<ListEntity> list;

            public void setIndex(int index) {
                this.index = index;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public int getIndex() {
                return index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getTotal() {
                return total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public boolean isNext() {
                return next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private boolean isdefault;
                private String name;
                private String mobile;
                private String street;
                private String province;
                private String city;
                private String district;
                private String provinceCode;
                private String cityCode;
                private String districtCode;
                private String email;
                private String weixin;
                private Double longitude;
                public Double latitude;

                public Double getLongitude() {
                    return longitude;
                }

                public void setLongitude(Double longitude) {
                    this.longitude = longitude;
                }

                public Double getLatitude() {
                    return latitude;
                }

                public void setLatitude(Double latitude) {
                    this.latitude = latitude;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public void setIsdefault(boolean isdefault) {
                    this.isdefault = isdefault;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }

                public long getId() {
                    return id;
                }

                public boolean isIsdefault() {
                    return isdefault;
                }

                public String getName() {
                    return name;
                }

                public String getMobile() {
                    return mobile;
                }

                public String getStreet() {
                    return street;
                }

                public String getProvince() {
                    return province;
                }

                public String getCity() {
                    return city;
                }

                public String getDistrict() {
                    return district;
                }

                public int getProvinceCode() {
                    if (Util.isEmpty(provinceCode)){
                        return 0;
                    }else {
                        return Integer.valueOf(provinceCode);
                    }
                }

                public void setProvinceCode(String provinceCode) {
                    this.provinceCode = provinceCode;
                }

                public int getCityCode() {
                    if (Util.isEmpty(cityCode)){
                        return 0;
                    }else {
                        return Integer.valueOf(cityCode);
                    }
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public boolean isdefault() {
                    return isdefault;
                }

                public int getDistrictCode() {
                    if (Util.isEmpty(districtCode)){
                        return 0;
                    }else {
                        return Integer.valueOf(districtCode);
                    }
                }

                public void setDistrictCode(String districtCode) {
                    this.districtCode = districtCode;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getWeixin() {
                    return weixin;
                }

                public void setWeixin(String weixin) {
                    this.weixin = weixin;
                }
            }
        }
    }
}
