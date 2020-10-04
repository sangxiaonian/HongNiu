package com.sang.common.widget.popu;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sang.common.R;
import com.sang.common.entity.NewAreaBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class OrderCitysPop extends BasePopu {


    private NewAreaBean msgLeft;
    RecyclerView rvLeft;
    private List<NewAreaBean> datasLeft;
    XAdapter<NewAreaBean> adapterLeft;

    private NewAreaBean msgRight;
    RecyclerView rvRight;
    private List<NewAreaBean> datasRight;
    XAdapter<NewAreaBean> adapterRight;
    private List<List<NewAreaBean>> shis;//所有的城市

    public OrderCitysPop(Context context) {
        super(context);
        initView(context);
        initDatas(context);
    }

    void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_citys_pop, null);
        rvLeft = inflate.findViewById(R.id.rv_left);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLeft.setLayoutManager(manager);

        rvRight = inflate.findViewById(R.id.rv_right);
        LinearLayoutManager manager1 = new LinearLayoutManager(context);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rvRight.setLayoutManager(manager1);
        setContentView(inflate);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        viewById.setFocusable(true);
        viewById.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

    }


    void initDatas(Context context) {
        datasLeft = new ArrayList<>();
        adapterLeft = new XAdapter<NewAreaBean>(context, datasLeft) {
            @Override
            public BaseHolder<NewAreaBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<NewAreaBean>(context, parent, R.layout.order_mian_pop_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final NewAreaBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        final View img = itemView.findViewById(R.id.img);
                        tvTitle.setText(data.toString());
                        if (datasLeft.indexOf(msgLeft) == position) {
                            img.setVisibility(View.VISIBLE);
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_light));
                        } else {
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_title_dark));
                            img.setVisibility(View.GONE);
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datasLeft.indexOf(msgLeft)!=position) {

                                    if (datasLeft.size()>position) {
                                        msgLeft = datasLeft.get(position);
                                    }else {
                                        msgLeft=null;
                                    }
                                    adapterLeft.notifyDataSetChanged();
                                    datasRight.clear();
                                    if (shis.size()>position&&shis.get(position)!=null){
                                        datasRight.addAll(shis.get(position));
                                    }
                                    adapterRight.notifyDataSetChanged();
                                }

                            }
                        });
                    }
                };
            }
        };
        rvLeft.setAdapter(adapterLeft);
        datasRight = new ArrayList<>();
        adapterRight = new XAdapter<NewAreaBean>(context, datasRight) {
            @Override
            public BaseHolder<NewAreaBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<NewAreaBean>(context, parent, R.layout.order_mian_pop_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final NewAreaBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        final View img = itemView.findViewById(R.id.img);
                        tvTitle.setText(data.toString());
                        if (datasRight.indexOf(msgRight) == position) {
                            img.setVisibility(View.VISIBLE);
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_light));
                        } else {
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_title_dark));
                            img.setVisibility(View.GONE);
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datasRight.indexOf(msgRight) != position) {
                                    if (listener != null) {
                                        listener.onSelectCity(OrderCitysPop.this, tragetView, data,position);
                                    }
                                    msgRight = datasRight.get(position);
                                    adapterRight.notifyDataSetChanged();
                                }

                            }
                        });
                    }
                };
            }
        };
        rvRight.setAdapter(adapterRight);
    }


    @Override
    public void show(View view) {
        tragetView = view;
        adapterLeft.notifyDataSetChanged();
        if (!isShow()) {
            pop.showAsDropDown(view);
        }

    }

    public void setProvince(List<NewAreaBean> province, List<List<NewAreaBean>> shis) {
        datasLeft.clear();
        this.shis = shis;
        if (province != null) {
            datasLeft.addAll(province);
        }
    }

    public void setCitys(List<List<NewAreaBean>> shis) {

    }


    OnCityClickListener listener;

    public void setListener(OnCityClickListener listener) {
        this.listener = listener;
    }


    public interface OnCityClickListener {
        void onSelectCity(OrderCitysPop pop, View target, NewAreaBean data, int position);
    }


}
