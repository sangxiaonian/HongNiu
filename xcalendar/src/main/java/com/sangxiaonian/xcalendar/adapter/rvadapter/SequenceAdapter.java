package com.sangxiaonian.xcalendar.adapter.rvadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sangxiaonian.xcalendar.R;
import com.sangxiaonian.xcalendar.calendar.SequenceCalendarView;
import com.sangxiaonian.xcalendar.entity.DateBean;
import com.sangxiaonian.xcalendar.inter.CalendarControl;
import com.sangxiaonian.xcalendar.utils.JLog;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class SequenceAdapter extends RecyclerView.Adapter implements CalendarControl.OnCalendarClickListener {

    Context context;
    List<DateBean> dateBeans;

    DateBean startDatebean,endDateBean;

    public SequenceAdapter(Context context, List<DateBean> dateBeans) {
        this.context = context;
        this.dateBeans = dateBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SequenceCalendarHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SequenceCalendarHolder holder = (SequenceCalendarHolder) viewHolder;
        DateBean dateBean = dateBeans.get(i);
        holder.initView(i, dateBean);
        holder.setListener(this);
        holder.calendarView.setSequenceSelect(startDatebean,endDateBean);
    }

    @Override
    public int getItemCount() {
        return dateBeans.size();
    }

    /**
     * 当日历被点击的时候
     *
     * @param clickBean
     * @param selects 当前选中的全部日期
     */
    @Override
    public void onSelectChange(DateBean clickBean, List<DateBean> selects) {
        if (selects!=null&&!selects.isEmpty()) {
            JLog.i(selects.toString());
            if (endDateBean==null){
                //没有没有初始日期，加入初始日期
                if (startDatebean==null){
                    setStart(clickBean);
                    //初始日期已经存在，此时为确认当前日期
                }else {
                    setEnd(clickBean);
                }
            }else {
                endDateBean=null;
                setStart(clickBean);
            }
            notifyDataSetChanged();
        }
    }
    private void setEnd(DateBean i) {
        if (startDatebean.compareTo(i)>0){
            endDateBean=startDatebean;
            startDatebean=i;
        }else if (startDatebean.compareTo(i)<0){
            endDateBean=i;
        }

    }

    private void setStart(DateBean bean) {
        startDatebean=bean;
        endDateBean=null;
    }

    /**
     * 清空选中
     */
    public void clearSelects() {
       startDatebean=null;
       endDateBean=null;
       notifyDataSetChanged();
    }



    /**
     * 获取到选中的起始日期
     * @return
     */
    public DateBean getStartDatebean() {
        return startDatebean;
    }

    /**
     * 获取到选中的终点日期
     * @return
     */
    public DateBean getEndDateBean() {
        return endDateBean;
    }

    public static class SequenceCalendarHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public   SequenceCalendarView calendarView;

        public SequenceCalendarHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            calendarView = itemView.findViewById(R.id.calendar);

        }

        public void initView(int position, DateBean dateBean) {
            tvTitle.setText(dateBean.getYear() + "年" + (dateBean.getMonth() + 1) + "月");
            calendarView.setCurrentDate(dateBean.getYear(),dateBean.getMonth());



        }

        public void setListener(CalendarControl.OnCalendarClickListener lisener){
            calendarView.setCalendarClickListener(lisener);
        }

    }


}
