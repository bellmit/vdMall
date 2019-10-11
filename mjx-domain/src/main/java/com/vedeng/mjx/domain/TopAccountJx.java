package com.vedeng.mjx.domain;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class TopAccountJx implements Serializable {
    private static final long serialVersionUID = 5987238618287488632L;
    /** 账户ID  ACCOUNT_ID **/
    private Integer accountId;

    /** 用户名  ACCOUNT **/
    private String account;

    /** 公司名称  COMPANY_NAME **/
    private String companyName;

    /** 邮箱地址  EMAIL **/
    private String email;

    /** 手机号码  MOBILE **/
    private String mobile;
    private String hiddenMobile;

    /** 昵称  NICKNAME **/
    private String nickname;

    /** 支付密码  PAY_PASSWORD **/
    private String payPassword;

    /** 经验成长总值  EXPERIENCE **/
    private Integer experience;

    /** 是否开通商家0未申请1申请待审核2审核通过3审核不通过  IS_OPEN_STORE **/
    private Integer isOpenStore;

    /** 账户状态：0 停用 1正常  STATUS **/
    private Integer status;

    /** 账户类型  ACCOUNT_TYPE_ID **/
    private Integer accountTypeId;

    /** 0待审核1审核通过2审核不通过（三证审核状态）  COMPANY_STATUS **/
    private Integer companyStatus;

    /** 0待审核1审核通过2审核不通过（资质审核状态）  INDENTITY_STATUS **/
    private Integer indentityStatus;

    /** 创建时间  ADD_TIME **/
    private Long addTime;

    /** 创建人(后台创建者ID)  CREATOR **/
    private Integer creator;

    /** 更新时间  MOD_TIME **/
    private Long modTime;

    /** 更新人  UPDATER **/
    private Integer updater;

    /** sso账户ID  SSO_ACCOUNT_ID **/
    private Integer ssoAccountId;

    /** 唯一ID  UUID **/
    private String uuid;

    /** 微信OPENID  WEIXIN_OPENID **/
    private String weixinOpenid;

    /** 最后登录时间  LAST_LOGIN_TIME **/
    private Long lastLoginTime;

    /** erp联系人ID  EMPLOYEE_ID **/
    private Integer employeeId;

    /** 来源1WEB,2微信,3APP(ios),4APP(android),5其它  FROM **/
    private Integer from;

    /** 0未同步 1已同步 2同步失败  SYNC_STATUS **/
    private Integer syncStatus;

    /** 联系人  CONTACT **/
    private String contact;

    /** 邀请码  INVITE_CODE **/
    private String inviteCode;

    /** 用户头像域名  AVATAR_DOMAIN **/
    private String avatarDomain;

    /** 用户头像地址  AVATAR_URI **/
    private String avatarUri;

    /** 客户来源 0个人注册 1邀请注册 2后台注册  ACCOUNT_FROM **/
    private Integer accountFrom;

    /** 客户种类 0贝登精选 1医械购 2运营后台  ACCOUNT_TYPE **/
    private Integer accountType;

    /**   账户ID  ACCOUNT_ID   **/
    public Integer getAccountId() {
        return accountId;
    }

    /**   账户ID  ACCOUNT_ID   **/
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**   用户名  ACCOUNT   **/
    public String getAccount() {
        return account;
    }

    /**   用户名  ACCOUNT   **/
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**   公司名称  COMPANY_NAME   **/
    public String getCompanyName() {
        return companyName;
    }

    /**   公司名称  COMPANY_NAME   **/
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**   邮箱地址  EMAIL   **/
    public String getEmail() {
        return email;
    }

    /**   邮箱地址  EMAIL   **/
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**   手机号码  MOBILE   **/
    public String getMobile() {
        return mobile;
    }

    /**   手机号码  MOBILE   **/
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**   昵称  NICKNAME   **/
    public String getNickname() {
        return nickname;
    }

    /**   昵称  NICKNAME   **/
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**   支付密码  PAY_PASSWORD   **/
    public String getPayPassword() {
        return payPassword;
    }

    /**   支付密码  PAY_PASSWORD   **/
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**   经验成长总值  EXPERIENCE   **/
    public Integer getExperience() {
        return experience;
    }

    /**   经验成长总值  EXPERIENCE   **/
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**   是否开通商家0未申请1申请待审核2审核通过3审核不通过  IS_OPEN_STORE   **/
    public Integer getIsOpenStore() {
        return isOpenStore;
    }

    /**   是否开通商家0未申请1申请待审核2审核通过3审核不通过  IS_OPEN_STORE   **/
    public void setIsOpenStore(Integer isOpenStore) {
        this.isOpenStore = isOpenStore;
    }

    /**   账户状态：0 停用 1正常  STATUS   **/
    public Integer getStatus() {
        return status;
    }

    /**   账户状态：0 停用 1正常  STATUS   **/
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**   账户类型  ACCOUNT_TYPE_ID   **/
    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    /**   账户类型  ACCOUNT_TYPE_ID   **/
    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    /**   0待审核1审核通过2审核不通过（三证审核状态）  COMPANY_STATUS   **/
    public Integer getCompanyStatus() {
        return companyStatus;
    }

    /**   0待审核1审核通过2审核不通过（三证审核状态）  COMPANY_STATUS   **/
    public void setCompanyStatus(Integer companyStatus) {
        this.companyStatus = companyStatus;
    }

    /**   0待审核1审核通过2审核不通过（资质审核状态）  INDENTITY_STATUS   **/
    public Integer getIndentityStatus() {
        return indentityStatus;
    }

    /**   0待审核1审核通过2审核不通过（资质审核状态）  INDENTITY_STATUS   **/
    public void setIndentityStatus(Integer indentityStatus) {
        this.indentityStatus = indentityStatus;
    }

    /**   创建时间  ADD_TIME   **/
    public Long getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**   创建人(后台创建者ID)  CREATOR   **/
    public Integer getCreator() {
        return creator;
    }

    /**   创建人(后台创建者ID)  CREATOR   **/
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**   更新时间  MOD_TIME   **/
    public Long getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**   更新人  UPDATER   **/
    public Integer getUpdater() {
        return updater;
    }

    /**   更新人  UPDATER   **/
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**   sso账户ID  SSO_ACCOUNT_ID   **/
    public Integer getSsoAccountId() {
        return ssoAccountId;
    }

    /**   sso账户ID  SSO_ACCOUNT_ID   **/
    public void setSsoAccountId(Integer ssoAccountId) {
        this.ssoAccountId = ssoAccountId;
    }

    /**   唯一ID  UUID   **/
    public String getUuid() {
        return uuid;
    }

    /**   唯一ID  UUID   **/
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**   微信OPENID  WEIXIN_OPENID   **/
    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    /**   微信OPENID  WEIXIN_OPENID   **/
    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid == null ? null : weixinOpenid.trim();
    }

    /**   最后登录时间  LAST_LOGIN_TIME   **/
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**   最后登录时间  LAST_LOGIN_TIME   **/
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**   erp联系人ID  EMPLOYEE_ID   **/
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**   erp联系人ID  EMPLOYEE_ID   **/
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**   来源1WEB,2微信,3APP(ios),4APP(android),5其它  FROM   **/
    public Integer getFrom() {
        return from;
    }

    /**   来源1WEB,2微信,3APP(ios),4APP(android),5其它  FROM   **/
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**   0未同步 1已同步 2同步失败  SYNC_STATUS   **/
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**   0未同步 1已同步 2同步失败  SYNC_STATUS   **/
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**   联系人  CONTACT   **/
    public String getContact() {
        return contact;
    }

    /**   联系人  CONTACT   **/
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**   邀请码  INVITE_CODE   **/
    public String getInviteCode() {
        return inviteCode;
    }

    /**   邀请码  INVITE_CODE   **/
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    /**   用户头像域名  AVATAR_DOMAIN   **/
    public String getAvatarDomain() {
        return avatarDomain;
    }

    /**   用户头像域名  AVATAR_DOMAIN   **/
    public void setAvatarDomain(String avatarDomain) {
        this.avatarDomain = avatarDomain == null ? null : avatarDomain.trim();
    }

    /**   用户头像地址  AVATAR_URI   **/
    public String getAvatarUri() {
        return avatarUri;
    }

    /**   用户头像地址  AVATAR_URI   **/
    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri == null ? null : avatarUri.trim();
    }

    /**   客户来源 0个人注册 1邀请注册 2后台注册  ACCOUNT_FROM   **/
    public Integer getAccountFrom() {
        return accountFrom;
    }

    /**   客户来源 0个人注册 1邀请注册 2后台注册  ACCOUNT_FROM   **/
    public void setAccountFrom(Integer accountFrom) {
        this.accountFrom = accountFrom;
    }

    /**   客户种类 0贝登精选 1医械购 2运营后台  ACCOUNT_TYPE   **/
    public Integer getAccountType() {
        return accountType;
    }

    /**   客户种类 0贝登精选 1医械购 2运营后台  ACCOUNT_TYPE   **/
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getHiddenMobile() {
        if(StringUtils.isBlank(mobile)){
            return "";
        }
        try {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }catch (Exception e){
            return "";
        }
    }

    public void setHiddenMobile(String hiddenMobile) {
        this.hiddenMobile = hiddenMobile;
    }

}