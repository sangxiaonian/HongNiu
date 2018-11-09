package xiaonian.sang.com.festivitymodule.invite.entity;

/**
 * 作者： ${PING} on 2018/10/23.
 * 活动个人邀请需要的信息
 */
public class QueryInvitedInfo {


    /**
     * invitedCount : 21
     * rebateTotalAmount : 0.87
     * invitedUrl : http://47.104.130.110:8080/static/index.html#/invite?inviteid%3D329%26inviter%3D%E5%B9%B3%E4%BA%9A%E9%B9%8F
     * invitedQrCodeUrl : https:/dev.static.hongniudai.cn/images/qr_code/2018/09/invite_329.png
     * inviterName : 平亚鹏
     * title : 泓牛供应链:好友喊你一起挣佣金
     * subtitle : 快速下订单，安心购保险，随时看轨迹
     * shareIcoUrl : https://api.hongniudai.cn/logo.png
     * isContinue : 1
     */

    private int invitedCount;
    private double rebateTotalAmount;
    private String invitedUrl;
    private String invitedQrCodeUrl;
    private String inviterName;
    private String title;
    private String subtitle;
    private String shareIcoUrl;
    private String isContinue;

    public int getInvitedCount() {
        return invitedCount;
    }

    public void setInvitedCount(int invitedCount) {
        this.invitedCount = invitedCount;
    }

    public double getRebateTotalAmount() {
        return rebateTotalAmount;
    }

    public void setRebateTotalAmount(double rebateTotalAmount) {
        this.rebateTotalAmount = rebateTotalAmount;
    }

    public String getInvitedUrl() {
        return invitedUrl;
    }

    public void setInvitedUrl(String invitedUrl) {
        this.invitedUrl = invitedUrl;
    }

    public String getInvitedQrCodeUrl() {
        return invitedQrCodeUrl;
    }

    public void setInvitedQrCodeUrl(String invitedQrCodeUrl) {
        this.invitedQrCodeUrl = invitedQrCodeUrl;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getShareIcoUrl() {
        return shareIcoUrl;
    }

    public void setShareIcoUrl(String shareIcoUrl) {
        this.shareIcoUrl = shareIcoUrl;
    }

    public String getIsContinue() {
        return isContinue;
    }

    public void setIsContinue(String isContinue) {
        this.isContinue = isContinue;
    }
}
