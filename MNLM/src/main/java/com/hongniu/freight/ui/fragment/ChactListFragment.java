package com.hongniu.freight.ui.fragment;


import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.Event;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 *
 * 聊天信息 framgent
 */
public class ChactListFragment extends CompanyBaseFragment {
    private ConversationListFragment messageFragment;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_chatlist, null);

        return inflate;
    }


    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE, true);
          messageFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .build();
        messageFragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性
        getChildFragmentManager()
                .beginTransaction().replace
                (R.id.content, messageFragment).commit();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE, true);
        }
    }



    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpchactFragment(Event.UpChactFragment event) {
        if (messageFragment!=null) {
            Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                    .build();
            messageFragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性
            messageFragment.onRestoreUI();
        }

    }
}
