package xiaonian.sang.com.festivitymodule.invite.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

import io.reactivex.Observable;
import xiaonian.sang.com.festivitymodule.R;
import xiaonian.sang.com.festivitymodule.invite.entity.InviteDetailBean;
import xiaonian.sang.com.festivitymodule.net.HttpFestivityFactory;

/**
 * @data 2018/10/18
 * @Author PING
 * @Description 邀请成功明细
 */
@Route(path = ArouterParamFestivity.activity_festivity_invite_detail)
public class FestivityInviteDetailActivity extends RefrushActivity<InviteDetailBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivity_invite_detail);
        setToolbarTitle("邀请成功明细");
        initData();
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<InviteDetailBean>>> getListDatas() {
        return HttpFestivityFactory.getInviteDetails();
    }

    @Override
    protected XAdapter<InviteDetailBean> getAdapter(List<InviteDetailBean> datas) {
        return new XAdapter<InviteDetailBean>(mContext,datas) {
            @Override
            public BaseHolder<InviteDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<InviteDetailBean>(mContext,parent,R.layout.festivity_item_invite){
                    @Override
                    public void initView(View itemView, int position, InviteDetailBean data) {
                        super.initView(itemView, position, data);
                        TextView tvName = itemView.findViewById(R.id.tv_name);
                        TextView tvPhone= itemView.findViewById(R.id.tv_phone);

                        tvName.setText("用户名："+new StringBuffer("桑测试").replace(0,1,"*"));
                        tvPhone.setText("手机号："+new StringBuffer("15515871516").replace(3,7,"****"));
                    }
                };
            }
        };
    }
}
