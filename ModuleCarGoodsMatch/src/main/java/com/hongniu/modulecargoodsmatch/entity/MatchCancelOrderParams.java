package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/24.
 */
public class MatchCancelOrderParams {
   private String id	;//true	number	订单id
   private int cancelCategory	;//false	number	订单取消原因分类 1:本人原因 2:司机原因    (订单已接单状态 必填)
   private String cancelCause	;//false	string	订单取消原因  (订单已接单状态 必填)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCancelCategory() {
        return cancelCategory;
    }

    public void setCancelCategory(int cancelCategory) {
        this.cancelCategory = cancelCategory;
    }

    public String getCancelCause() {
        return cancelCause;
    }

    public void setCancelCause(String cancelCause) {
        this.cancelCause = cancelCause;
    }
}
