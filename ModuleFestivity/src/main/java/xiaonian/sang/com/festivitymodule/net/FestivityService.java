package xiaonian.sang.com.festivitymodule.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import xiaonian.sang.com.festivitymodule.invite.entity.BrokerageDetailsBean;
import xiaonian.sang.com.festivitymodule.invite.entity.InviteDetailBean;
import xiaonian.sang.com.festivitymodule.invite.entity.QueryInvitedInfo;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public interface FestivityService {


    /**
     * 查询个人活动邀请信息
     *
     * @return
     */
    @POST("hongniu//api/user/queryInvitedInfo")
    Observable<CommonBean<QueryInvitedInfo>> queryInvitedInfo();

    /**
     * 查询个人邀请用户信息
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/user/queryInvitedUsers")
    Observable<CommonBean<PageBean<InviteDetailBean>>> getInviteDetails(@Body PagerParambean bean);

    /**
     * 查询个人佣金明细
     *
     * @param bean
     * @return
     */
    @POST("hongniu/api/account/rebateFlows")
    Observable<CommonBean<PageBean<BrokerageDetailsBean>>> getRebateFlows(@Body PagerParambean bean);

}
