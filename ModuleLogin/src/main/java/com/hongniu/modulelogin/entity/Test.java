package com.hongniu.modulelogin.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/20.
 */
public class Test {

    /**
     * citys : [{"districts":[{"infor":{"areaCode":"110100","areaId":2,"areaName":"北京城区","center":"116.407394,39.904211","cityCode":"010","level":2,"parentId":1}},{"infor":{"areaCode":"110101","areaId":3,"areaName":"东城区","center":"116.41649,39.928341","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110102","areaId":4,"areaName":"西城区","center":"116.365873,39.912235","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110105","areaId":5,"areaName":"朝阳区","center":"116.443205,39.921506","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110106","areaId":6,"areaName":"丰台区","center":"116.287039,39.858421","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110107","areaId":7,"areaName":"石景山区","center":"116.222933,39.906611","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110108","areaId":8,"areaName":"海淀区","center":"116.298262,39.95993","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110109","areaId":9,"areaName":"门头沟区","center":"116.101719,39.940338","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110111","areaId":10,"areaName":"房山区","center":"116.143486,39.748823","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110112","areaId":11,"areaName":"通州区","center":"116.656434,39.909946","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110113","areaId":12,"areaName":"顺义区","center":"116.654642,40.130211","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110114","areaId":13,"areaName":"昌平区","center":"116.231254,40.220804","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110115","areaId":14,"areaName":"大兴区","center":"116.341483,39.726917","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110116","areaId":15,"areaName":"怀柔区","center":"116.631931,40.316053","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110117","areaId":16,"areaName":"平谷区","center":"117.121351,40.140595","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110118","areaId":17,"areaName":"密云区","center":"116.843047,40.376894","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110119","areaId":18,"areaName":"延庆区","center":"115.974981,40.456591","cityCode":"010","level":3,"parentId":2}}],"infor":{"areaCode":"110100","areaId":2,"areaName":"北京城区","center":"116.407394,39.904211","cityCode":"010","level":2,"parentId":1}}]
     * infor : {"areaCode":"110000","areaId":1,"areaName":"北京市","center":"116.407394,39.904211","cityCode":"010","level":1,"parentId":-1}
     */

    private InforBean infor;
    private List<CitysBean> citys;

    public InforBean getInfor() {
        return infor;
    }

    public void setInfor(InforBean infor) {
        this.infor = infor;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class InforBean {
        /**
         * areaCode : 110000
         * areaId : 1
         * areaName : 北京市
         * center : 116.407394,39.904211
         * cityCode : 010
         * level : 1
         * parentId : -1
         */

        private String areaCode;
        private int areaId;
        private String areaName;
        private String center;
        private String cityCode;
        private int level;
        private int parentId;

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCenter() {
            return center;
        }

        public void setCenter(String center) {
            this.center = center;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }

    public static class CitysBean {
        /**
         * districts : [{"infor":{"areaCode":"110100","areaId":2,"areaName":"北京城区","center":"116.407394,39.904211","cityCode":"010","level":2,"parentId":1}},{"infor":{"areaCode":"110101","areaId":3,"areaName":"东城区","center":"116.41649,39.928341","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110102","areaId":4,"areaName":"西城区","center":"116.365873,39.912235","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110105","areaId":5,"areaName":"朝阳区","center":"116.443205,39.921506","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110106","areaId":6,"areaName":"丰台区","center":"116.287039,39.858421","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110107","areaId":7,"areaName":"石景山区","center":"116.222933,39.906611","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110108","areaId":8,"areaName":"海淀区","center":"116.298262,39.95993","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110109","areaId":9,"areaName":"门头沟区","center":"116.101719,39.940338","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110111","areaId":10,"areaName":"房山区","center":"116.143486,39.748823","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110112","areaId":11,"areaName":"通州区","center":"116.656434,39.909946","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110113","areaId":12,"areaName":"顺义区","center":"116.654642,40.130211","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110114","areaId":13,"areaName":"昌平区","center":"116.231254,40.220804","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110115","areaId":14,"areaName":"大兴区","center":"116.341483,39.726917","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110116","areaId":15,"areaName":"怀柔区","center":"116.631931,40.316053","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110117","areaId":16,"areaName":"平谷区","center":"117.121351,40.140595","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110118","areaId":17,"areaName":"密云区","center":"116.843047,40.376894","cityCode":"010","level":3,"parentId":2}},{"infor":{"areaCode":"110119","areaId":18,"areaName":"延庆区","center":"115.974981,40.456591","cityCode":"010","level":3,"parentId":2}}]
         * infor : {"areaCode":"110100","areaId":2,"areaName":"北京城区","center":"116.407394,39.904211","cityCode":"010","level":2,"parentId":1}
         */

        private InforBeanX infor;
        private List<DistrictsBean> districts;

        public InforBeanX getInfor() {
            return infor;
        }

        public void setInfor(InforBeanX infor) {
            this.infor = infor;
        }

        public List<DistrictsBean> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictsBean> districts) {
            this.districts = districts;
        }

        public static class InforBeanX {
            /**
             * areaCode : 110100
             * areaId : 2
             * areaName : 北京城区
             * center : 116.407394,39.904211
             * cityCode : 010
             * level : 2
             * parentId : 1
             */

            private String areaCode;
            private int areaId;
            private String areaName;
            private String center;
            private String cityCode;
            private int level;
            private int parentId;

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getCenter() {
                return center;
            }

            public void setCenter(String center) {
                this.center = center;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }
        }

        public static class DistrictsBean {
            /**
             * infor : {"areaCode":"110100","areaId":2,"areaName":"北京城区","center":"116.407394,39.904211","cityCode":"010","level":2,"parentId":1}
             */

            private InforBeanXX infor;

            public InforBeanXX getInfor() {
                return infor;
            }

            public void setInfor(InforBeanXX infor) {
                this.infor = infor;
            }

            public static class InforBeanXX {
                /**
                 * areaCode : 110100
                 * areaId : 2
                 * areaName : 北京城区
                 * center : 116.407394,39.904211
                 * cityCode : 010
                 * level : 2
                 * parentId : 1
                 */

                private String areaCode;
                private int areaId;
                private String areaName;
                private String center;
                private String cityCode;
                private int level;
                private int parentId;

                public String getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(String areaCode) {
                    this.areaCode = areaCode;
                }

                public int getAreaId() {
                    return areaId;
                }

                public void setAreaId(int areaId) {
                    this.areaId = areaId;
                }

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public String getCenter() {
                    return center;
                }

                public void setCenter(String center) {
                    this.center = center;
                }

                public String getCityCode() {
                    return cityCode;
                }

                public void setCityCode(String cityCode) {
                    this.cityCode = cityCode;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }
            }
        }
    }
}
