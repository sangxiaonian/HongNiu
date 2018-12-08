package com.hongniu.baselibrary.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者： ${PING} on 2018/12/6.
 */
public class TruckGudieSwitchBean {


    /**
     * vehicleSize : [{"name":"微型货车","type":1},{"name":"轻型货车","type":2},{"name":"中型货车","type":3},{"name":"重型货车","type":4}]
     * axis : [{"name":"1轴","type":0},{"name":"2轴","type":1},{"name":"3轴","type":2},{"name":"4轴","type":3},{"name":"5轴","type":4},{"name":"6轴","type":5},{"name":"6轴以上","type":6}]
     * switch : true
     */

    @SerializedName(value = "state", alternate = {"switch"})
    private boolean state;
    private List<DataInfor> vehicleSize;
    private List<DataInfor> axis;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<DataInfor> getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(List<DataInfor> vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    public List<DataInfor> getAxis() {
        return axis;
    }

    public void setAxis(List<DataInfor> axis) {
        this.axis = axis;
    }

    public static class DataInfor {
        /**
         * name : 微型货车
         * type : 1
         */

        private String name;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return name==null?"":name;
        }
    }


}
