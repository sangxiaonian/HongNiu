package xiaonian.sang.com.festivitymodule.invite.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PagerParambean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;

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
        PagerParambean bean = new PagerParambean(currentPage);
        return HttpFestivityFactory.getInviteDetails(bean);
    }

    @Override
    protected XAdapter<InviteDetailBean> getAdapter(List<InviteDetailBean> datas) {
        return new XAdapter<InviteDetailBean>(mContext, datas) {
            @Override
            public BaseHolder<InviteDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<InviteDetailBean>(mContext, parent, R.layout.festivity_item_invite) {
                    @Override
                    public void initView(View itemView, int position, InviteDetailBean data) {
                        super.initView(itemView, position, data);
                        TextView tvName = itemView.findViewById(R.id.tv_name);
                        TextView tvPhone = itemView.findViewById(R.id.tv_phone);
                        StringBuffer name = new StringBuffer();
                        if (!TextUtils.isEmpty(data.getContact())) {

                            if (CommonUtils.isPhone(data.getContact())){
                                name.append(data.contact).replace(3, 7, "****");
                            }else {
                                name.append(data.getContact());
                                if (name.length() <= 2) {
                                    name.replace(0, 1, "*");
                                } else {
                                    String re = "";
                                    for (int i = 1; i < name.length() - 1; i++) {
                                        re += "*";
                                    }
                                    name.replace(1, name.length() - 1, re);
                                }
                            }
                        }
                        tvName.setText(String.format(getString(R.string.username_infor), name));
                        String format;
                        if (data.getMobile() != null) {
                            if (data.getMobile().length() >= 11) {
                                format = String.format(getString(R.string.username_infor), new StringBuffer(data.mobile).replace(3, 7, "****"));
                            } else {
                                format = String.format(getString(R.string.username_infor), data.mobile);

                            }
                        } else {
                            format = String.format(getString(R.string.username_infor), "");
                        }
                        tvPhone.setText(format);
                    }
                };
            }
        };
    }
}
