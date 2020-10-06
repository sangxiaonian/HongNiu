package com.hongniu.freight.utils;

import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.companylibrary.config.Param;
import com.google.gson.Gson;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.thirdlibrary.chact.ChactHelper;

/**
 * 作者：  on 2020/2/23.
 * 个人信息相关
 */
public class InfoUtils {
    /**
     * 获取登录信息
     *
     * @return
     */
    public static LoginInfo getLoginInfo() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.LOGIN);
        LoginInfo loginInfo = null;
        try {
            loginInfo = new Gson().fromJson(string, LoginInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginInfo;
    }

    /**
     * 获取登录信息
     */
    public static void saveLoginInfo(LoginInfo loginInfo) {
        if (loginInfo == null) {
            return;
        }
        SharedPreferencesUtils.getInstance().putString(Param.LOGIN, new Gson().toJson(loginInfo));

    }

    /**
     * 获取个人信息
     */
    public static void saveMyInfo(PersonInfor loginInfo) {
        if (loginInfo == null) {
            return;
        }
        SharedPreferencesUtils.getInstance().putString(Param.MY, new Gson().toJson(loginInfo));

    }

    /**
     * 获取个人信息
     *
     * @return
     */
    public static PersonInfor getMyInfo() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.MY);
        PersonInfor myInfo = null;
        try {
            myInfo = new Gson().fromJson(string, PersonInfor.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myInfo;
    }

    /**
     * 退出登录
     */
    public static void loginOut() {
        SharedPreferencesUtils.getInstance().clear();
        ChactHelper.getHelper().disConnect();
    }


    /**
     * 获取当前认证状态
     *
     * @return 0未提交审核资料 1已提交审核资料 2系统自动审核中 3人工后台审核中 4认证成功 5认证失败
     */
    public static int getState(PersonInfor info) {
        int status = 0;
        if (info.getIsStaff() == 1) {
            //平台员工
            status = 4;
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status = info.getIsDriverStatus();
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status = info.getIsPersonCarrierStatus();
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status = info.getIsCompanyCarrierStatus();
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status = info.getIsCompanyShipperStatus();
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status = info.getIsPersonShipperStatus();
        }
        return status;
    }

    /**
     * 获取当前认证状态
     *
     * @return
     */
    public static int getState(LoginInfo info) {
        int status = 0;
        if (info.isStaff()) {
            //平台员工
            status = 4;
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status = info.getIsDriverStatus();
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status = info.getIsPersonCarrierStatus();
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status = info.getIsCompanyCarrierStatus();
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status = info.getIsCompanyShipperStatus();
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status = info.getIsPersonShipperStatus();
        }
        return status;
    }

    /**
     * 获取当前认证状态
     *
     * @return
     */
    public static String getStateName(int status) {
        String msg = "";
//        认证个人托运人状态 0 未提交审核资料 1 已提交审核资料 2 系统自动审核中 3 人工后台审核中 4认证成功 5 认证失败
        if (status == 0) {
            msg = "未提交审核资料";
        } else if (status == 1) {
            msg = "已提交审核资料";
        } else if (status == 2) {
            msg = "系统自动审核中";
        } else if (status == 3) {
            msg = "人工后台审核中";
        } else if (status == 4) {
//            msg="认证成功";
        } else if (status == 5) {
            msg = "认证失败";
        }
        return msg;
    }

    /**
     * 获取当前认证状态
     *
     * @return -1 未认证角色
     */
    public static Role getRole(PersonInfor info) {
        Role status;
        if (info == null) {
            return Role.UNKNOW;
        }
        if (info.getIsStaff() == 1) {
            //平台员工
            status = Role.PLATFORM;
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status = Role.DRIVER;
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status = Role.CARRIER_PERSONAL;
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status = Role.CARRIER_COMPANY;
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status = Role.SHIPPER_COMPANY;
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status = Role.SHIPPER_PERSONAL;
        } else {
            status = Role.UNKNOW;
        }
        return status;
    }

    /**
     * 获取当前认证状态
     *
     * @return -1 未认证角色
     */
    public static Role getRole(LoginInfo info) {
        Role status;
        if (info == null) {
            return Role.UNKNOW;
        }
        if (info.isStaff()) {
            //平台员工
            status = Role.PLATFORM;
        } else if (info.getIsDriver() == 1) {
            //如果是司机
            status = Role.DRIVER;
        } else if (info.getIsPersonCarrier() == 1) {
            //个人承运人
            status = Role.CARRIER_PERSONAL;
        } else if (info.getIsCompanyCarrier() == 1) {
            //公司承运人
            status = Role.CARRIER_COMPANY;
        } else if (info.getIsCompanyShipper() == 1) {
            //公司托运人
            status = Role.SHIPPER_COMPANY;
        } else if (info.getIsPersonShipper() == 1) {
            //个人托运人
            status = Role.SHIPPER_PERSONAL;
        } else {
            status = Role.UNKNOW;
        }
        return status;
    }

    /**
     * 获取当前认证状态
     *
     * @return -1 未认证角色
     */
    public static String getRoleName(PersonInfor info) {
        return getRole(info).getName();
    }

    /**
     * 是否实名认证成功
     * @return false 未进行实名认证或者未进行人脸识别
     */
    public static boolean isShowAlert() {
        PersonInfor myInfo = InfoUtils.getMyInfo();
        if (myInfo!=null) {
            Role role = getRole(myInfo);
            if (role == Role.UNKNOW || myInfo.getIsRealname() != 1||InfoUtils.getState(myInfo)==5) {
                return true;
            }
        }else {
            LoginInfo loginInfo = InfoUtils.getLoginInfo();
            Role role = getRole(loginInfo);
            if (role == Role.UNKNOW || loginInfo.getIsRealname() != 1||InfoUtils.getState(loginInfo)==5) {
                return true;
            }
        }
        return false;
    }

    public static RoleOrder chagetRoleOrder(Role role) {
        RoleOrder roleOrder;
        if (role == Role.CARRIER_COMPANY || role == Role.CARRIER_PERSONAL) {
            roleOrder = RoleOrder.CARRIER;
        } else if (role == Role.DRIVER) {
            roleOrder = RoleOrder.DRIVER;
        } else {
            roleOrder = RoleOrder.SHIPPER;
        }
        return roleOrder;
    }
}
