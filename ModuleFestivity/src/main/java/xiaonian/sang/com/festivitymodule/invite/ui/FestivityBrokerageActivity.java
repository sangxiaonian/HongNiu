package xiaonian.sang.com.festivitymodule.invite.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

import io.reactivex.Observable;
import xiaonian.sang.com.festivitymodule.R;
import xiaonian.sang.com.festivitymodule.invite.entity.BrokerageDetailsBean;
import xiaonian.sang.com.festivitymodule.net.HttpFestivityFactory;

/**
 * @data 2018/10/18
 * @Author PING
 * @Description 佣金明细
 */
@Route(path = ArouterParamFestivity.activity_festivity_brokerage)
public class FestivityBrokerageActivity extends RefrushActivity<BrokerageDetailsBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivity_brokerage);
        setToolbarTitle("我的佣金明细");
        initData();
        queryData(true,true);
    }

    @Override
    protected Observable<CommonBean<PageBean<BrokerageDetailsBean>>> getListDatas() {
        return HttpFestivityFactory.getBrokerageDetails(currentPage);
    }

    @Override
    protected XAdapter<BrokerageDetailsBean> getAdapter(List<BrokerageDetailsBean> datas) {
        return new XAdapter<BrokerageDetailsBean>(mContext, datas) {
            @Override
            public BaseHolder<BrokerageDetailsBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BrokerageDetailsBean>(mContext, parent, R.layout.festivity_item_brokerage) {
                    @Override
                    public void initView(View itemView, int position, BrokerageDetailsBean data) {
                        super.initView(itemView, position, data);
                        TextView tvName  =itemView.findViewById(R.id.tv_name );
                        TextView tvOrder =itemView.findViewById(R.id.tv_order);
                        TextView tvTime  =itemView.findViewById(R.id.tv_time );
                        TextView tvMoney  =itemView.findViewById(R.id.tv_money );

                        tvName.setText("好友保费返佣（"+data.getRebateName()+"）");
                        tvOrder.setText("订单号："+data.getOrderNum());
                        tvTime.setText("收款时间："+data.getCreateTime());
                        tvMoney.setText("+"+data.getAmount());
                    }
                };
            }
        };
    }
}
