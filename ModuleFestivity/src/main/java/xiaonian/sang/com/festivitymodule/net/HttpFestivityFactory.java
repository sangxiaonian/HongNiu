package xiaonian.sang.com.festivitymodule.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.common.utils.ConvertUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import xiaonian.sang.com.festivitymodule.invite.entity.BrokerageDetailsBean;
import xiaonian.sang.com.festivitymodule.invite.entity.InviteDetailBean;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpFestivityFactory {


    /**
     * 搜索订单
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<InviteDetailBean>>> getInviteDetails() {
        return Observable.just(1)
                .map(new Function<Integer, CommonBean<PageBean<InviteDetailBean>>>() {
                    @Override
                    public CommonBean<PageBean<InviteDetailBean>> apply(Integer integer) throws Exception {

                        CommonBean<PageBean<InviteDetailBean>> bean = new CommonBean<>();
                        bean.setCode(200);

                        PageBean<InviteDetailBean> pageBean = new PageBean<>();
                        ArrayList<InviteDetailBean> balanceOfAccountBeans = new ArrayList<>();
                        int random = ConvertUtils.getRandom(19, 21);
                        for (int i = 0; i < random; i++) {
                            balanceOfAccountBeans.add(new InviteDetailBean());
                        }
                        pageBean.setList(balanceOfAccountBeans);
                        bean.setData(pageBean);
                        return bean;
                    }
                });
    }

    /**
     * 获取我的佣金明细
     *
     * @return
     */
    public static Observable<CommonBean<PageBean<BrokerageDetailsBean>>> getBrokerageDetails() {
        return Observable.just(1)
                .map(new Function<Integer, CommonBean<PageBean<BrokerageDetailsBean>>>() {
                    @Override
                    public CommonBean<PageBean<BrokerageDetailsBean>> apply(Integer integer) throws Exception {

                        CommonBean<PageBean<BrokerageDetailsBean>> bean = new CommonBean<>();
                        bean.setCode(200);

                        PageBean<BrokerageDetailsBean> pageBean = new PageBean<>();
                        ArrayList<BrokerageDetailsBean> balanceOfAccountBeans = new ArrayList<>();
                        int random = ConvertUtils.getRandom(19, 21);
                        for (int i = 0; i < random; i++) {
                            balanceOfAccountBeans.add(new BrokerageDetailsBean());
                        }
                        pageBean.setList(balanceOfAccountBeans);
                        bean.setData(pageBean);
                        return bean;
                    }
                });
    }
}
