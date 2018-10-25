package xiaonian.sang.com.festivitymodule.invite.entity;

/**
 * 作者： ${PING} on 2018/10/23.
 * 活动个人邀请需要的信息
 */
public class QueryInvitedInfo {


    /**
     * invitedCount : 0
     * invitedUrl : http://47.104.130.110:8080/static/index.html#/invite?inviteid%3D329%26inviter%3D%E5%B9%B3%E4%BA%9A%E9%B9%8F
     * invitedQrCodeUrl : https:/dev.static.hongniudai.cn/images/qr_code/2018/09/invite_329.png
     */

    private int invitedCount;
    private String invitedUrl;
    private String invitedQrCodeUrl;

    public int getInvitedCount() {
        return invitedCount;
    }

    public void setInvitedCount(int invitedCount) {
        this.invitedCount = invitedCount;
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
}
