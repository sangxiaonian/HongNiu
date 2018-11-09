package com.hongniu.modulefinance.entity;

import com.hongniu.baselibrary.entity.PageBean;

/**
 * 作者： ${PING} on 2018/10/8.
 * 余额明细
 */
public class AllBalanceOfAccountBean {


    /**
     * accountFlows : {"pageNum":1,"pages":1,"total":8,"pageSize":20,"list":[{"id":103,"accountCode":"HN000329","userName":"asdfasdf","amount":1000,"fundtype":5,"inorexptype":1,"createTime":"2018-10-25 18:44:12","carteUserName":"zhoujg77","relPayBillno":"1231231","relAccountCode":null,"relAccountName":null,"remark":"充值","ref1":"null","ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"收入","income":1000,"expend":null,"surplus":null,"fundtypeStr":"充值","fundsources":4,"ordernumber":"12312313"},{"id":23,"accountCode":"HN000329","userName":"张三","amount":0.3,"fundtype":3,"inorexptype":2,"createTime":"2018-10-17 10:57:10","carteUserName":"张三","relPayBillno":"HT002","relAccountCode":"HN000330","relAccountName":null,"remark":"支出","ref1":"3","ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"支出","income":null,"expend":0.3,"surplus":null,"fundtypeStr":"转账","fundsources":2,"ordernumber":null},{"id":13,"accountCode":"HN000329","userName":"123123","amount":20,"fundtype":2,"inorexptype":2,"createTime":"2018-10-16 10:50:31","carteUserName":"张三","relPayBillno":"1231231","relAccountCode":"HN000449","relAccountName":null,"remark":"提现","ref1":"null","ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"支出","income":null,"expend":20,"surplus":null,"fundtypeStr":"提现","fundsources":4,"ordernumber":null},{"id":8,"accountCode":"HN000329","userName":"张三","amount":0.3,"fundtype":3,"inorexptype":2,"createTime":"2018-10-15 15:28:29","carteUserName":"张三","relPayBillno":"HT002","relAccountCode":"HN000330","relAccountName":null,"remark":"支出","ref1":null,"ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"支出","income":null,"expend":0.3,"surplus":null,"fundtypeStr":"转账","fundsources":2,"ordernumber":null},{"id":7,"accountCode":"HN000329","userName":"123123","amount":10023,"fundtype":2,"inorexptype":2,"createTime":"2018-10-15 15:23:13","carteUserName":"张三","relPayBillno":"1231231","relAccountCode":"HN000329","relAccountName":null,"remark":"提现","ref1":null,"ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"支出","income":null,"expend":10023,"surplus":null,"fundtypeStr":"提现","fundsources":4,"ordernumber":null},{"id":6,"accountCode":"HN000329","userName":"asdfasdf","amount":1002220,"fundtype":5,"inorexptype":1,"createTime":"2018-10-15 15:23:00","carteUserName":"zhoujg77","relPayBillno":"1231231","relAccountCode":null,"relAccountName":null,"remark":"充值","ref1":null,"ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"收入","income":1002220,"expend":null,"surplus":null,"fundtypeStr":"充值","fundsources":4,"ordernumber":"12312313"},{"id":5,"accountCode":"HN000329","userName":"123123","amount":100,"fundtype":2,"inorexptype":2,"createTime":"2018-10-15 15:22:10","carteUserName":"张三","relPayBillno":"1231231","relAccountCode":"HN000329","relAccountName":null,"remark":"提现","ref1":null,"ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"支出","income":null,"expend":100,"surplus":null,"fundtypeStr":"提现","fundsources":4,"ordernumber":null},{"id":4,"accountCode":"HN000329","userName":"asdfasdf","amount":1000,"fundtype":5,"inorexptype":1,"createTime":"2018-10-15 15:14:04","carteUserName":"zhoujg77","relPayBillno":"1231231","relAccountCode":null,"relAccountName":null,"remark":"充值","ref1":null,"ref2":null,"ref3":null,"ref4":null,"inorexptypeStr":"收入","income":1000,"expend":null,"surplus":null,"fundtypeStr":"充值","fundsources":4,"ordernumber":"12312313"}]}
     * tobeaccountFlows : null
     */

    private PageBean<BalanceOfAccountBean> accountFlows;
    private PageBean<BalanceOfAccountBean> tobeaccountFlows;

    public PageBean<BalanceOfAccountBean> getAccountFlows() {
        return accountFlows;
    }

    public void setAccountFlows(PageBean<BalanceOfAccountBean> accountFlows) {
        this.accountFlows = accountFlows;
    }

    public PageBean<BalanceOfAccountBean> getTobeaccountFlows() {
        return tobeaccountFlows;
    }

    public void setTobeaccountFlows(PageBean<BalanceOfAccountBean> tobeaccountFlows) {
        this.tobeaccountFlows = tobeaccountFlows;
    }

}
