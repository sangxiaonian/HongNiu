package com.sang.modulebreakbulk.entity;

/**
 * 作者：  on 2019/9/22.
 */
public class BreakbulkCompanyInfoParam {
    private  String city	; //城市
    private  String sortType	; //排序方式（1智能排序，2销量最高，3好评优先，4离我最近）  默认1
    private  String destination	; //目的地  sortType=4时必填     规则： lon，lat（经度，纬度）， “,”分割     如117.500244, 40.417801     经纬度小数点不超过6位

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
