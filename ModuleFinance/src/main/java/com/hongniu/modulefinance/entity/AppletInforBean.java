package com.hongniu.modulefinance.entity;

/**
 * 作者： ${PING} on 2018/10/26.
 * 从小程序获取的数据
 */
public class AppletInforBean {

    /**
     * errMsg : getUserInfo:ok
     * rawData : {"nickName":"桑小年","gender":1,"language":"zh_CN","city":"Zhengzhou","province":"Henan","country":"China","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqToWkDdSGoR7nHCLS5Ix8ZSEl5TvGnWB6elaE1972IXVWoGKUzQ9p9ic5A2m9nyZ1mTpenIz4wdyQ/132"}
     * signature : 94606041476d68e1c1f569d608f1cbf364a6ccba
     * encryptedData : rMAKjcbBrh7CFoQnR3xevJik5OwcfGM0Gs6X6HC9Q6qqWy/VSBz1EaqzzHvxyYgCBBNKmyMoijV/oqiDcb6hNKy3/CoBxKRQeppLpomzMR24FeDpDFS4zYJPrTq4H+TYy3owxDDKGIw/L+QEc9eDio3SNA87ElpO6U2IKRWQodXHByfAdqWpD8VbLemJJQNvK98knQ7prysCwbRZaWrl0xBP4EbYTtneZvxUxO5ljQtQyU0tofSVCl0UGynFJ5QeOwY5Kczr48QZv14bAfXMk+AC4Es3hLaNdQ+/1eEPKdvON6Aizp66qv6WhB5NEWlLU7tgCpvkG/B4yrj5R1dxwGVkoOzu+apJYXB6cIQzeDB0853dZWCU02HuqyttJdqjgIbCjXChFKIZ7rNI+KYyStuW5zGgnoR7HmPpe35BDI/DTCBCDqJONvagQeSHv2bnMgrpAuqCTzm6Dij9aKTKbqO1IHnIJ1k9W7nBrBUAzX0MmLKohOUEKRGqLCrXvAed42ua2Lg39P1Z1B4aCfLrjw==
     * iv : G9s06bgcw6OkQHJ1UB9eSA==
     * userInfo : {"nickName":"桑小年","gender":1,"language":"zh_CN","city":"Zhengzhou","province":"Henan","country":"China","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqToWkDdSGoR7nHCLS5Ix8ZSEl5TvGnWB6elaE1972IXVWoGKUzQ9p9ic5A2m9nyZ1mTpenIz4wdyQ/132"}
     * code : 081ezqzg29fysA0kybzg2YGwzg2ezqzY
     */

    private String errMsg;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
    private UserInfoBean userInfo;
    private String code;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class UserInfoBean {
        /**
         * nickName : 桑小年
         * gender : 1
         * language : zh_CN
         * city : Zhengzhou
         * province : Henan
         * country : China
         * avatarUrl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqToWkDdSGoR7nHCLS5Ix8ZSEl5TvGnWB6elaE1972IXVWoGKUzQ9p9ic5A2m9nyZ1mTpenIz4wdyQ/132
         */

        private String nickName;
        private int gender;
        private String language;
        private String city;
        private String province;
        private String country;
        private String avatarUrl;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
