package xiaonian.sang.com.festivitymodule.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.ConvertUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import xiaonian.sang.com.festivitymodule.invite.entity.BrokerageDetailsBean;
import xiaonian.sang.com.festivitymodule.invite.entity.InviteDetailBean;
import xiaonian.sang.com.festivitymodule.invite.entity.QueryInvitedInfo;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpFestivityFactory {


    public static Observable<CommonBean<QueryInvitedInfo>> queryInvitedInfor(){
        return FestivityClient.getInstance().getService()
                .queryInvitedInfo()
                .compose(RxUtils.<CommonBean<QueryInvitedInfo>>getSchedulersObservableTransformer())
                ;
    }


    /**
     * 搜索邀请人详情
     *
     * @return
     * @param bean
     */
    public static Observable<CommonBean<PageBean<InviteDetailBean>>> getInviteDetails(PagerParambean bean) {
        return FestivityClient.getInstance().getService()
                .getInviteDetails(bean)
                .compose(RxUtils.<CommonBean<PageBean<InviteDetailBean>>>getSchedulersObservableTransformer());
    }

    /**
     * 获取我的佣金明细
     *
     * @return
     * @param currentPage
     */
    public static Observable<CommonBean<PageBean<BrokerageDetailsBean>>> getBrokerageDetails(int currentPage) {
        PagerParambean bean =new PagerParambean(currentPage);
        return FestivityClient.getInstance().getService()
                .getRebateFlows(bean)
                .compose(RxUtils.<CommonBean<PageBean<BrokerageDetailsBean>>>getSchedulersObservableTransformer());


    }
}
