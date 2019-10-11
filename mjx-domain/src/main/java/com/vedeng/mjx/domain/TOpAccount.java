package com.vedeng.mjx.domain;

/**
 * T_OP_ACCOUNT
 */
public class TOpAccount {
    /**
     * <pre>
     * 账户ID
     * 表字段 : T_OP_ACCOUNT.ACCOUNT_ID
     * </pre>
     * 
     */
    private Integer accountId;

    /**
     * <pre>
     * 用户名
     * 表字段 : T_OP_ACCOUNT.ACCOUNT
     * </pre>
     * 
     */
    private String account;

    /**
     * <pre>
     * 公司名称
     * 表字段 : T_OP_ACCOUNT.COMPANY_NAME
     * </pre>
     * 
     */
    private String companyName;

    /**
     * <pre>
     * 邮箱地址
     * 表字段 : T_OP_ACCOUNT.EMAIL
     * </pre>
     * 
     */
    private String email;

    /**
     * <pre>
     * 手机号码
     * 表字段 : T_OP_ACCOUNT.MOBILE
     * </pre>
     * 
     */
    private String mobile;

    /**
     * <pre>
     * 昵称
     * 表字段 : T_OP_ACCOUNT.NICKNAME
     * </pre>
     * 
     */
    private String nickname;

    /**
     * <pre>
     * 支付密码
     * 表字段 : T_OP_ACCOUNT.PAY_PASSWORD
     * </pre>
     * 
     */
    private String payPassword;

    /**
     * <pre>
     * 经验成长总值
     * 表字段 : T_OP_ACCOUNT.EXPERIENCE
     * </pre>
     * 
     */
    private Integer experience;

    /**
     * <pre>
     * 是否开通商家0未申请1申请待审核2审核通过3审核不通过
     * 表字段 : T_OP_ACCOUNT.IS_OPEN_STORE
     * </pre>
     * 
     */
    private Boolean isOpenStore;

    /**
     * <pre>
     * 账户状态：0 停用 1正常
     * 表字段 : T_OP_ACCOUNT.STATUS
     * </pre>
     * 
     */
    private Boolean status;

    /**
     * <pre>
     * 账户类型
     * 表字段 : T_OP_ACCOUNT.ACCOUNT_TYPE_ID
     * </pre>
     * 
     */
    private Integer accountTypeId;

    /**
     * <pre>
     * 0待审核1审核通过2审核不通过（三证审核状态）
     * 表字段 : T_OP_ACCOUNT.COMPANY_STATUS
     * </pre>
     * 
     */
    private Boolean companyStatus;

    /**
     * <pre>
     * 0待审核1审核通过2审核不通过（资质审核状态）
     * 表字段 : T_OP_ACCOUNT.INDENTITY_STATUS
     * </pre>
     * 
     */
    private Boolean indentityStatus;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_OP_ACCOUNT.ADD_TIME
     * </pre>
     * 
     */
    private Long addTime;

    /**
     * <pre>
     * 创建人(后台创建者ID)
     * 表字段 : T_OP_ACCOUNT.CREATOR
     * </pre>
     * 
     */
    private Integer creator;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_OP_ACCOUNT.MOD_TIME
     * </pre>
     * 
     */
    private Long modTime;

    /**
     * <pre>
     * 更新人
     * 表字段 : T_OP_ACCOUNT.UPDATER
     * </pre>
     * 
     */
    private Integer updater;

    /**
     * <pre>
     * sso账户ID
     * 表字段 : T_OP_ACCOUNT.SSO_ACCOUNT_ID
     * </pre>
     * 
     */
    private Integer ssoAccountId;

    /**
     * <pre>
     * 唯一ID
     * 表字段 : T_OP_ACCOUNT.UUID
     * </pre>
     * 
     */
    private String uuid;

    /**
     * <pre>
     * 微信OPENID
     * 表字段 : T_OP_ACCOUNT.WEIXIN_OPENID
     * </pre>
     * 
     */
    private String weixinOpenid;

    /**
     * <pre>
     * 最后登录时间
     * 表字段 : T_OP_ACCOUNT.LAST_LOGIN_TIME
     * </pre>
     * 
     */
    private Long lastLoginTime;

    /**
     * <pre>
     * erp联系人ID
     * 表字段 : T_OP_ACCOUNT.EMPLOYEE_ID
     * </pre>
     * 
     */
    private Integer employeeId;

    /**
     * <pre>
     * 来源1WEB,2微信,3APP(ios),4APP(android),5其它
     * 表字段 : T_OP_ACCOUNT.FROM
     * </pre>
     * 
     */
    private Boolean from;

    /**
     * <pre>
     * 0未同步 1已同步 2同步失败
     * 表字段 : T_OP_ACCOUNT.SYNC_STATUS
     * </pre>
     * 
     */
    private Integer syncStatus;

    /**
     * <pre>
     * 联系人
     * 表字段 : T_OP_ACCOUNT.CONTACT
     * </pre>
     * 
     */
    private String contact;

    /**
     * <pre>
     * 邀请码
     * 表字段 : T_OP_ACCOUNT.INVITE_CODE
     * </pre>
     * 
     */
    private String inviteCode;

    /**
     * <pre>
     * 用户头像域名
     * 表字段 : T_OP_ACCOUNT.AVATAR_DOMAIN
     * </pre>
     * 
     */
    private String avatarDomain;

    /**
     * <pre>
     * 用户头像地址
     * 表字段 : T_OP_ACCOUNT.AVATAR_URI
     * </pre>
     * 
     */
    private String avatarUri;

    /**
     * <pre>
     * 客户来源 0个人注册 1邀请注册 2后台注册
     * 表字段 : T_OP_ACCOUNT.ACCOUNT_FROM
     * </pre>
     * 
     */
    private Boolean accountFrom;

    /**
     * <pre>
     * 客户种类 0贝登精选 1医械购 2运营后台
     * 表字段 : T_OP_ACCOUNT.ACCOUNT_TYPE
     * </pre>
     * 
     */
    private Boolean accountType;

    /**
     * 账户ID
     * @return ACCOUNT_ID 账户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 账户ID
     * @param accountId 账户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 用户名
     * @return ACCOUNT 用户名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 用户名
     * @param account 用户名
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 公司名称
     * @return COMPANY_NAME 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 公司名称
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 邮箱地址
     * @return EMAIL 邮箱地址
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱地址
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 手机号码
     * @return MOBILE 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 昵称
     * @return NICKNAME 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 支付密码
     * @return PAY_PASSWORD 支付密码
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 支付密码
     * @param payPassword 支付密码
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * 经验成长总值
     * @return EXPERIENCE 经验成长总值
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * 经验成长总值
     * @param experience 经验成长总值
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * 是否开通商家0未申请1申请待审核2审核通过3审核不通过
     * @return IS_OPEN_STORE 是否开通商家0未申请1申请待审核2审核通过3审核不通过
     */
    public Boolean getIsOpenStore() {
        return isOpenStore;
    }

    /**
     * 是否开通商家0未申请1申请待审核2审核通过3审核不通过
     * @param isOpenStore 是否开通商家0未申请1申请待审核2审核通过3审核不通过
     */
    public void setIsOpenStore(Boolean isOpenStore) {
        this.isOpenStore = isOpenStore;
    }

    /**
     * 账户状态：0 停用 1正常
     * @return STATUS 账户状态：0 停用 1正常
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 账户状态：0 停用 1正常
     * @param status 账户状态：0 停用 1正常
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 账户类型
     * @return ACCOUNT_TYPE_ID 账户类型
     */
    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    /**
     * 账户类型
     * @param accountTypeId 账户类型
     */
    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    /**
     * 0待审核1审核通过2审核不通过（三证审核状态）
     * @return COMPANY_STATUS 0待审核1审核通过2审核不通过（三证审核状态）
     */
    public Boolean getCompanyStatus() {
        return companyStatus;
    }

    /**
     * 0待审核1审核通过2审核不通过（三证审核状态）
     * @param companyStatus 0待审核1审核通过2审核不通过（三证审核状态）
     */
    public void setCompanyStatus(Boolean companyStatus) {
        this.companyStatus = companyStatus;
    }

    /**
     * 0待审核1审核通过2审核不通过（资质审核状态）
     * @return INDENTITY_STATUS 0待审核1审核通过2审核不通过（资质审核状态）
     */
    public Boolean getIndentityStatus() {
        return indentityStatus;
    }

    /**
     * 0待审核1审核通过2审核不通过（资质审核状态）
     * @param indentityStatus 0待审核1审核通过2审核不通过（资质审核状态）
     */
    public void setIndentityStatus(Boolean indentityStatus) {
        this.indentityStatus = indentityStatus;
    }

    /**
     * 创建时间
     * @return ADD_TIME 创建时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     * 创建时间
     * @param addTime 创建时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**
     * 创建人(后台创建者ID)
     * @return CREATOR 创建人(后台创建者ID)
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 创建人(后台创建者ID)
     * @param creator 创建人(后台创建者ID)
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 更新时间
     * @return MOD_TIME 更新时间
     */
    public Long getModTime() {
        return modTime;
    }

    /**
     * 更新时间
     * @param modTime 更新时间
     */
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**
     * 更新人
     * @return UPDATER 更新人
     */
    public Integer getUpdater() {
        return updater;
    }

    /**
     * 更新人
     * @param updater 更新人
     */
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**
     * sso账户ID
     * @return SSO_ACCOUNT_ID sso账户ID
     */
    public Integer getSsoAccountId() {
        return ssoAccountId;
    }

    /**
     * sso账户ID
     * @param ssoAccountId sso账户ID
     */
    public void setSsoAccountId(Integer ssoAccountId) {
        this.ssoAccountId = ssoAccountId;
    }

    /**
     * 唯一ID
     * @return UUID 唯一ID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 唯一ID
     * @param uuid 唯一ID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 微信OPENID
     * @return WEIXIN_OPENID 微信OPENID
     */
    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    /**
     * 微信OPENID
     * @param weixinOpenid 微信OPENID
     */
    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid == null ? null : weixinOpenid.trim();
    }

    /**
     * 最后登录时间
     * @return LAST_LOGIN_TIME 最后登录时间
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最后登录时间
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * erp联系人ID
     * @return EMPLOYEE_ID erp联系人ID
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * erp联系人ID
     * @param employeeId erp联系人ID
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 来源1WEB,2微信,3APP(ios),4APP(android),5其它
     * @return FROM 来源1WEB,2微信,3APP(ios),4APP(android),5其它
     */
    public Boolean getFrom() {
        return from;
    }

    /**
     * 来源1WEB,2微信,3APP(ios),4APP(android),5其它
     * @param from 来源1WEB,2微信,3APP(ios),4APP(android),5其它
     */
    public void setFrom(Boolean from) {
        this.from = from;
    }

    /**
     * 0未同步 1已同步 2同步失败
     * @return SYNC_STATUS 0未同步 1已同步 2同步失败
     */
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**
     * 0未同步 1已同步 2同步失败
     * @param syncStatus 0未同步 1已同步 2同步失败
     */
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * 联系人
     * @return CONTACT 联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 联系人
     * @param contact 联系人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 邀请码
     * @return INVITE_CODE 邀请码
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * 邀请码
     * @param inviteCode 邀请码
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    /**
     * 用户头像域名
     * @return AVATAR_DOMAIN 用户头像域名
     */
    public String getAvatarDomain() {
        return avatarDomain;
    }

    /**
     * 用户头像域名
     * @param avatarDomain 用户头像域名
     */
    public void setAvatarDomain(String avatarDomain) {
        this.avatarDomain = avatarDomain == null ? null : avatarDomain.trim();
    }

    /**
     * 用户头像地址
     * @return AVATAR_URI 用户头像地址
     */
    public String getAvatarUri() {
        return avatarUri;
    }

    /**
     * 用户头像地址
     * @param avatarUri 用户头像地址
     */
    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri == null ? null : avatarUri.trim();
    }

    /**
     * 客户来源 0个人注册 1邀请注册 2后台注册
     * @return ACCOUNT_FROM 客户来源 0个人注册 1邀请注册 2后台注册
     */
    public Boolean getAccountFrom() {
        return accountFrom;
    }

    /**
     * 客户来源 0个人注册 1邀请注册 2后台注册
     * @param accountFrom 客户来源 0个人注册 1邀请注册 2后台注册
     */
    public void setAccountFrom(Boolean accountFrom) {
        this.accountFrom = accountFrom;
    }

    /**
     * 客户种类 0贝登精选 1医械购 2运营后台
     * @return ACCOUNT_TYPE 客户种类 0贝登精选 1医械购 2运营后台
     */
    public Boolean getAccountType() {
        return accountType;
    }

    /**
     * 客户种类 0贝登精选 1医械购 2运营后台
     * @param accountType 客户种类 0贝登精选 1医械购 2运营后台
     */
    public void setAccountType(Boolean accountType) {
        this.accountType = accountType;
    }
}