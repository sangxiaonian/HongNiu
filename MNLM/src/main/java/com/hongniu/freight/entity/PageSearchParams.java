package com.hongniu.freight.entity;

/**
 * 作者：  on 2020/3/8.
 */
public class PageSearchParams extends PageParams {
   private String searchText;
   private String carStatus;

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
