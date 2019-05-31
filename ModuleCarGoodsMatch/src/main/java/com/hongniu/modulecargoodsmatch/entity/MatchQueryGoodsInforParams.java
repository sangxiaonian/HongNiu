package com.hongniu.modulecargoodsmatch.entity;

import com.hongniu.baselibrary.entity.PagerParambean;

/**
 * 作者： ${PING} on 2019/5/21.
 * 查询车货匹配信息
 */
public class MatchQueryGoodsInforParams extends PagerParambean {
  public int queryType	;//true	string	1.车货匹配列表(只包含可已抢单) 2.我的发布列表
  public String carTypeId	;//false	int	车辆类型id
  public String deliveryDateType	;//false	string	发车日期(today-今天 tomorrow-明天 thisweek-本周 nextweek-下周)
  public String amountMin	;//false	string	查询最小金额
  public String amountMax	;//false	string	查询最大金额



  public MatchQueryGoodsInforParams(int currentPage) {
        super(currentPage);
    }
}
