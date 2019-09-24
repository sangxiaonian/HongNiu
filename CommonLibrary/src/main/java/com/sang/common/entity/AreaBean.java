package com.sang.common.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/9/12.
 */
public class AreaBean {

    /**
     * citys : [{"areas":[{"id":500,"name":"东城区"},{"id":501,"name":"西城区"},{"id":502,"name":"海淀区"},{"id":503,"name":"朝阳区"},{"id":504,"name":"崇文区"},{"id":505,"name":"宣武区"},{"id":506,"name":"丰台区"},{"id":507,"name":"石景山区"},{"id":508,"name":"房山区"},{"id":509,"name":"门头沟区"},{"id":510,"name":"通州区"},{"id":511,"name":"顺义区"},{"id":512,"name":"昌平区"},{"id":513,"name":"怀柔区"},{"id":514,"name":"平谷区"},{"id":515,"name":"大兴区"},{"id":516,"name":"密云县"},{"id":517,"name":"延庆县"}],"id":52,"name":"北京"}]
     * id : 2
     * name : 北京
     */

    private int id;
    private String name;
    private List<CitysBean> citys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class CitysBean {
        /**
         * areas : [{"id":500,"name":"东城区"},{"id":501,"name":"西城区"},{"id":502,"name":"海淀区"},{"id":503,"name":"朝阳区"},{"id":504,"name":"崇文区"},{"id":505,"name":"宣武区"},{"id":506,"name":"丰台区"},{"id":507,"name":"石景山区"},{"id":508,"name":"房山区"},{"id":509,"name":"门头沟区"},{"id":510,"name":"通州区"},{"id":511,"name":"顺义区"},{"id":512,"name":"昌平区"},{"id":513,"name":"怀柔区"},{"id":514,"name":"平谷区"},{"id":515,"name":"大兴区"},{"id":516,"name":"密云县"},{"id":517,"name":"延庆县"}]
         * id : 52
         * name : 北京
         */

        private int id;
        private String name;
        private List<AreasBean> areas;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AreasBean> getAreas() {
            return areas;
        }

        public void setAreas(List<AreasBean> areas) {
            this.areas = areas;
        }

        public static class AreasBean {
            /**
             * id : 500
             * name : 东城区
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
