package rongyun.sang.com.chactmodule.ui.fragment;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.sang.common.utils.JLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import rongyun.sang.com.chactmodule.R;

/**
 * 作者： ${桑小年} on 2018/11/25.
 * 努力，为梦长留
 *
 * 聊天信息 framgent
 */
public class ChactListFragment extends BaseFragment {
    private ConversationListFragment messageFragment;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_chatlist, null);

        return inflate;
    }


    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
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
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
        }
    }

    @Override
    protected boolean getUseEventBus() {
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
        JLog.i("手工刀消息，开始更新界面"+messageFragment);

    }
}
