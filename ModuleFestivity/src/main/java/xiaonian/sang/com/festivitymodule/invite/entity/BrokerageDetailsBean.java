package xiaonian.sang.com.festivitymodule.invite.entity;

/**
 * 作者： ${PING} on 2018/10/18.
 * 佣金明细
 */
public class BrokerageDetailsBean {


    /**
     * amount : 0.87
     * createTime : 2018-10-28 17:10:27
     * rebateName : 樊鹏举
     * rebateMobile : 19921548317
     * orderNum : HN20181026190432054213
     */

    private double amount;
    private String createTime;
    private String rebateName;
    private String rebateMobile;
    private String orderNum;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRebateName() {
        return rebateName;
    }

    public void setRebateName(String rebateName) {
        this.rebateName = rebateName;
    }

    public String getRebateMobile() {
        return rebateMobile;
    }

    public void setRebateMobile(String rebateMobile) {
        this.rebateMobile = rebateMobile;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
