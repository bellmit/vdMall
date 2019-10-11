package com.vedeng.mjx.service.address.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.vedeng.mjx.common.address.TraderMjxContactAdderss;
import com.vedeng.mjx.common.constant.CommonConstant;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.common.http.HttpsClient;
import com.vedeng.mjx.common.util.StringUtil;
import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.mapper.ext.TOpAccountAddressExtMapper;
import com.vedeng.mjx.service.address.AddressService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 收货地址实现业务层
 * @ auther:Jax
 * @ date:  2019/7 15:38
 */
@Service
public class AddressServiceImpl implements AddressService {


    Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Resource
    private TOpAccountAddressExtMapper tOpAccountAddressExtMapper;

    @Value("${erp.server}")
    protected String erpUrl;

    private static final String HTTP_ADDRESS_SYNC_TO_ERP_URL="/trader/customer/saveMjxContactAdders.do";

    /***
     * 查新省市区
     * @param
     * @param paramEntity flagId:1省份；regionId: 省市区主键
     */
    @Override
    public VAccountAddressExt queryRegionInfo(VAccountAddressExt paramEntity) throws VedengException {
        if (paramEntity == null) {
            return null;
        }
        if (paramEntity.getRegionId() == null && !"1".equals(paramEntity.getFlagId())) {
            throw new VedengException(VedengErrorCode.FAIL_REGSION_ID);//当flagId传入的不是省份时，市、区编号不能为空
        }
        //根据参数 去查省市区
        List<TRegion> regionList = tOpAccountAddressExtMapper.queryRegionInfo(paramEntity);
        VAccountAddressExt entity = new VAccountAddressExt();
        entity.setRegionList(regionList);
        return entity;
    }
   /***
    * describe: 查询当前区域下面所有区域
    * creat_time: 2019/8/15 21:26
    * param: [region]
    * return: java.util.List<com.vedeng.mjx.domain.TRegion>
    */
    @Override
    public List<TRegion> queryRegionInfomation(TRegion region) throws VedengException {

        //根据参数 去查省市区
        List<TRegion> regionList = null;
        try {
            regionList = tOpAccountAddressExtMapper.queryRegionInfomation(region);
        } catch (Exception e) {
            logger.error("queryRegionInfomation error",e);
        }
        return regionList;
    }


    @Override
    public Integer queryCount(Integer userId){
        //根据当前用户id 查询用户所拥有地址数
        return tOpAccountAddressExtMapper.addressCount(userId);
    }



    /***
     * 新增收货地址
     * @param
     * @param paramEntity
     *
     */
    @Override
    @Transactional
    public VAccountAddressExt insertAddressInfo(VAccountAddressExt paramEntity) throws VedengException {
        if (paramEntity==null){
            return null;
        }
        //校验地址
        checkAddress(paramEntity);
        String regionIds = paramEntity.getProvinceCode()+","+paramEntity.getCityCode()+","+paramEntity.getDistrictCode();
        String regionNames = paramEntity.getProvince()+","+paramEntity.getCity()+","+paramEntity.getDistrict();
        paramEntity.setRegionIds(regionIds);
        paramEntity.setRegionName(regionNames);
        if ("1".equals(paramEntity.getIsDefAddress())){
            //看是否市默认地址,如果是， 则update当前用户所有的地址为非默认地址
            int x = tOpAccountAddressExtMapper.updateAddressDefault(paramEntity);
        }
        if ("1".equals(paramEntity.getIsDefTicket())){
            //看是否为收票地址,如果是， 则update当前用户所有的地址为非收票地址
            int x2 = tOpAccountAddressExtMapper.updateTicketDefault(paramEntity);
        }
        int str =0;
        if(paramEntity.getAddressId()==null)
        {
            //新增数据
            str = tOpAccountAddressExtMapper.insertAddressInfo(paramEntity);
        }
        else{
            //修改数据
            str = tOpAccountAddressExtMapper.updateAddressInfo(paramEntity);
        }
        if(str>0) {
            return paramEntity;
        }
        return  null;
    }

    /***
     * 查询收货地址列表
     * @param paramEntity
     * @return
     */
    @Override
    public List<VAccountAddressExt> queryAddressList(VAccountAddressExt paramEntity) throws VedengException {

        if (paramEntity.getPagerNumber() != null && paramEntity.getPageSize() != null) {
            PageHelper.startPage(paramEntity.getPagerNumber(), paramEntity.getPageSize());
        }
        List<VAccountAddressExt> list = tOpAccountAddressExtMapper.queryAddressList(paramEntity);
        //modby glenn
        if (list != null && list.size() > 0) {
            VAccountAddressExt defaultShAddr = new VAccountAddressExt();
            VAccountAddressExt defaultSpAddr = new VAccountAddressExt();
            for (VAccountAddressExt entity : list) {
                if (entity != null) {
                    //封装bean
                    packageAddressBean(entity);
                    if ("1".equals(entity.getIsDefAddress())) {
                        //默认收货地址
                        defaultShAddr = entity;
                    }
                    if ("1".equals(entity.getIsDefTicket())) {
                        //默认收票地址
                        defaultSpAddr = entity;
                    }

                }
            }
            list = sortAddr(list, defaultShAddr, defaultSpAddr);
        }
        return list;
    }
    /***
     * 查询收货地址列表
     * @param paramEntity
     * @return
     */
    @Override
    public List<VAccountAddressExt> appQueryAddressList(VAccountAddressExt paramEntity) throws VedengException {

        if (paramEntity.getPagerNumber() != null && paramEntity.getPageSize() != null) {
            PageHelper.startPage(paramEntity.getPagerNumber(), paramEntity.getPageSize());
        }
        List<VAccountAddressExt> list = tOpAccountAddressExtMapper.queryAddressList(paramEntity);
        //modby glenn
        if (list != null && list.size() > 0) {
            VAccountAddressExt defaultShAddr = new VAccountAddressExt();
            VAccountAddressExt defaultSpAddr = new VAccountAddressExt();
            for (VAccountAddressExt entity : list) {
                if (entity != null) {
                    //封装bean
                    packageAddressBeanforapp(entity);
                    if ("1".equals(entity.getIsDefAddress())) {
                        //默认收货地址
                        defaultShAddr = entity;
                    }
                    if ("1".equals(entity.getIsDefTicket())) {
                        //默认收票地址
                        defaultSpAddr = entity;
                    }

                }
            }
            list = sortAddr(list, defaultShAddr, defaultSpAddr);
        }
        return list;
    }

    /**
     * 查询地址详情
     * @param paramEntity
     * @return
     */
    @Override
    public VAccountAddressExt queryAddressInfo(VAccountAddressExt paramEntity) throws VedengException {
        if(paramEntity !=null && paramEntity.getAddressId() == null){
            throw new VedengException(VedengErrorCode.FAIL_ADDRESS_ID);//地址ID不能为空
        }
        VAccountAddressExt entity = tOpAccountAddressExtMapper.queryAddressInfo(paramEntity);
        if(entity != null){
            String regionIds = entity.getRegionIds()==null?"":entity.getRegionIds();
            String regionNames = entity.getRegionName()==null?"":entity.getRegionName();
            String[] regIds = regionIds.split(",");
            entity.setProvinceCode(regIds[0]);//省code
            entity.setCityCode(regIds[1]);//城市code
            entity.setDistrictCode(regIds[2]);//大区code
            String[] regIdName = regionNames.split(",");
            entity.setProvince(regIdName[0]);
            entity.setCity(regIdName[1]);
            entity.setDistrict(regIdName[2]);
        }
        return entity;
    }

    /**
     * 删除地址信息
     * @param paramEntity
     * @return
     */
    @Override
    @Transactional
    public int deleteAddressInfo(VAccountAddressExt paramEntity) throws VedengException {
        if(paramEntity !=null &&paramEntity.getAddressId()==null){
            throw new VedengException(VedengErrorCode.FAIL_ADDRESS_ID);//地址ID不能为空
        }
        Map<String, Object> addr = tOpAccountAddressExtMapper.queryNewAddressInfo(paramEntity.getAddressId());
        if(addr==null ||addr.isEmpty()){
            //地址不存在
            throw new VedengException(VedengErrorCode.FAIL_ADDRESS_NOT_EXIST);
        }
        return tOpAccountAddressExtMapper.deleteAddressInfo(paramEntity);
    }

    /**
     * 查询默认地址
     * @param paramEntity
     * @return
     */
    @Override
    public Map<String, Object> queryDefaultAddress(VAccountAddressExt paramEntity) throws VedengException {
        Map<String, Object> resultMap = null;
        if (paramEntity.getAddressId() != null) {
            //根据最新的地址主键去查
            resultMap = tOpAccountAddressExtMapper.queryNewAddressInfo(paramEntity.getAddressId());
            if(resultMap==null){
                resultMap = addressRule(paramEntity);
            }
        } else {
            //没传地址主键就查默认收货地址
            resultMap = addressRule(paramEntity);
        }
        if (resultMap != null && StringUtil.isNotBlank(MapUtils.getString(resultMap, "regionName"))) {
            String addressname = MapUtils.getString(resultMap, "regionName");
            String[] str = addressname.split(",");
            //处理直辖市
            str = dealZxs(str);

            StringBuffer sb=new StringBuffer();
            for (String strs : str) {
                sb.append(strs).append(" ");
            }
            sb.append(MapUtils.getString(resultMap, "addressInfo"));//省市区+ 详细地址
            String phoneNumber = MapUtils.getString(resultMap, "phone").replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

            String accountName = MapUtils.getString(resultMap, "accountName");
            if (accountName.length() > 8) {
                accountName = accountName.substring(0, 8) + "...";
            }
            resultMap.put("accountName", accountName);
            resultMap.put("phone", phoneNumber);
            resultMap.put("newAddressInfo", sb.toString());
        }
        return resultMap;
    }


    /**
     * 查询默认地址for APP
     * @param paramEntity
     * @return
     */
    @Override
    public Map<String, Object> queryDefaultAddressForApp(VAccountAddressExt paramEntity) throws VedengException {
        Map<String, Object> resultMap = null;
        if (paramEntity.getAddressId() != null) {
            //根据最新的地址主键去查
            resultMap = tOpAccountAddressExtMapper.queryAddressInfoApp(paramEntity.getAddressId());
            if(resultMap==null){
                resultMap = addressRule(paramEntity);
            }
        } else {
            //没传地址主键就查默认收货地址
            resultMap = addressRuleForApp(paramEntity);
        }
        if (resultMap != null && StringUtil.isNotBlank(MapUtils.getString(resultMap, "regionName"))) {
            String addressname = MapUtils.getString(resultMap, "regionName");
            String[] str = addressname.split(",");
            //处理直辖市
            str = dealZxs(str);

            StringBuffer sb=new StringBuffer();
            for (String strs : str) {
                sb.append(strs).append(" ");
            }
//            sb.append(MapUtils.getString(resultMap, "addressInfo"));//省市区+ 详细地址
            String phoneNumber = MapUtils.getString(resultMap, "phone").replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

            String accountName = MapUtils.getString(resultMap, "accountName");
            if (accountName.length() > 8) {
                accountName = accountName.substring(0, 8) + "...";
            }
            resultMap.put("regionName",sb.toString());
            resultMap.put("accountName", accountName);
            resultMap.put("phone", phoneNumber);
            int isDefAddress = MapUtils.getBoolean(resultMap, "isDefAddress") ? 1 : 0;
            resultMap.put("isDefAddress",isDefAddress);
        }
        return resultMap;
    }

    @Override
    public VAccountAddressExt selectVAccountAddressById(Integer accountAddressId) {
        return tOpAccountAddressExtMapper.selectAddressById(accountAddressId);
    }
    @Override
    public List<TraderMjxContactAdderss> queryNoSyncToErpAddr(Map param) {
        //查出需要推送到erp的地址信息
        List<VAccountAddressExt> exts = tOpAccountAddressExtMapper.queryNoSyncErpIds(param);

        List addersses=null;
        //封装
        if(!CollectionUtils.isEmpty(exts)){
            addersses = new ArrayList<TraderMjxContactAdderss>();
            for (VAccountAddressExt ext : exts) {
                TraderMjxContactAdderss traderMjxContactAdderss=new TraderMjxContactAdderss();
                //封装 TraderMjxContactAdderss
                packageAccountAddress(ext, traderMjxContactAdderss);
                addersses.add(traderMjxContactAdderss);
            }
        }
        return addersses;

    }
    @Override
    public JSONObject httpSyncToErp(TraderMjxContactAdderss address) {
        logger.info("HTTP START HTTP_ADDRESS_SYNC_TO_ERP_URL IS {},label is {}",erpUrl+HTTP_ADDRESS_SYNC_TO_ERP_URL,address.getTitleName());

        JSONObject result = HttpsClient.httpPost(erpUrl+HTTP_ADDRESS_SYNC_TO_ERP_URL, JSONObject.fromObject(address));
        logger.info("HTTP END");
        return result;

    }
    @Override
    @Transactional
    public Integer updateSyncAddr(Map param) {
        if(param.get("accountAddressId")!=null && StringUtil.isNotBlank(param.get("accountAddressId").toString())){
            //不为空时去更新
            return tOpAccountAddressExtMapper.updateSyncAddr(param);
        }else{
            return -1;
        }
    }

    private void packageAccountAddress(VAccountAddressExt ext, TraderMjxContactAdderss traderMjxContactAdderss) {
        traderMjxContactAdderss.setPhone(ext.getCurUserPhoneNum());
        traderMjxContactAdderss.setDeliveryUserName(ext.getAccountName());
        traderMjxContactAdderss.setDeliveryUserAddress(ext.getAddressInfo());
        traderMjxContactAdderss.setDeliveryUserPhone(ext.getPhone());
        if(StringUtil.isNotBlank(ext.getRegionIds())){
            traderMjxContactAdderss.setDeliveryLevel1Id(ext.getRegionIds().split(",")[0]);
            traderMjxContactAdderss.setDeliveryLevel2Id(ext.getRegionIds().split(",")[1]);
            traderMjxContactAdderss.setDeliveryLevel3Id(ext.getRegionIds().split(",")[2]);
        }
        traderMjxContactAdderss.setDeliveryAreaIds(ext.getRegionIds());
        traderMjxContactAdderss.setIsDeliveryDefault(Integer.valueOf(ext.getIsDefAddress()));
        traderMjxContactAdderss.setIsInvoiceDefault(Integer.valueOf(ext.getIsDefTicket()));
        traderMjxContactAdderss.setMjxContactAdderssId(ext.getAccountAddressId());
        traderMjxContactAdderss.setIsEnable(ext.getDelFlag().equals(CommonConstant.STATUS_1)?CommonConstant.STATUS_0:CommonConstant.STATUS_1);
        traderMjxContactAdderss.setTitleName(ext.getLabel());
    }


    /**
     * describe: 排序规则：默认的收货地址排最前面
     *           默认的收票地址此之
     *           其余的按照新增时间降序排列
     * creat_time: 2019/7/22 16:34
     * param: [list, defaultShAddr, defaultSpAddr][需要排序的List,默认收票地址bean,默认收货地址bean]
     * return: java.util.List[排序后的list]
     */
    private List sortAddr(List<VAccountAddressExt> list, VAccountAddressExt defaultShAddr, VAccountAddressExt defaultSpAddr) {
        Boolean isShBlank = StringUtil.isBlank(defaultShAddr.getIsDefAddress());
        Boolean isSpBlank = StringUtil.isBlank(defaultSpAddr.getIsDefAddress());
        if (defaultShAddr.equals(defaultSpAddr)) {
            //若为同一对象
            list.remove(defaultShAddr);
            list.add(0, defaultShAddr);
        } else {
            if (!isShBlank && !isSpBlank) {
                //有默认收票地址也有默认收货地址
                list.remove(defaultShAddr);
                list.remove(defaultSpAddr);
                list.add(0, defaultShAddr);
                list.add(1, defaultSpAddr);
            }

            if (!isShBlank && isSpBlank) {
                //没有默认收票地址 有默认收货地址
                list.remove(defaultShAddr);
                list.add(0, defaultShAddr);
            }

            if (isShBlank && !isSpBlank) {
                //有默认收票地址 没有默认收货地址
                list.remove(defaultSpAddr);
                list.add(0, defaultSpAddr);
            }
        }

        return list;
    }


    /***
     * describe: 封装地址bean
     * creat_time: 2019/8/20 9:34
     * param: [entity]
     * return: void
     */
    private void packageAddressBean(VAccountAddressExt entity) {
        String regionIds = entity.getRegionIds() == null ? "" : entity.getRegionIds();
        String regionNames = entity.getRegionName() == null ? "" : entity.getRegionName();
        String[] regIds = regionIds.split(",");
        entity.setProvinceCode(regIds[0]);//省code
        entity.setCityCode(regIds[1]);//城市code
        entity.setDistrictCode(regIds[2]);//大区code
        String[] regIdName = regionNames.split(",");
        entity.setProvince(regIdName[0]);
        entity.setCity(regIdName[1]);
        entity.setDistrict(regIdName[2]);
        //收件人
        String receiver = entity.getAccountName() == null ? "" : entity.getAccountName();
        //如果长度大于8，则后面都是...
        if (receiver.length() > 8) {
            receiver = receiver.substring(0,8)+ "...";
        }
        entity.setAccountName(receiver);
        //新电话样式
        String phoneNumber = entity.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        entity.setPhone(phoneNumber);
    }
    /***
     * describe: 封装地址bean
     * creat_time: 2019/8/20 9:34
     * param: [entity]
     * return: void
     */
    private void packageAddressBeanforapp(VAccountAddressExt entity) {
        String regionIds = entity.getRegionIds() == null ? "" : entity.getRegionIds();
        String regionNames = entity.getRegionName() == null ? "" : entity.getRegionName();
        String[] regIds = regionIds.split(",");
        entity.setProvinceCode(regIds[0]);//省code
        entity.setCityCode(regIds[1]);//城市code
        entity.setDistrictCode(regIds[2]);//大区code
        String[] regIdName = regionNames.split(",");
        //处理直辖市
        String[] dealedRegionName = dealZxs(regIdName);


        StringBuffer sb=new StringBuffer();
        for (String strs : dealedRegionName) {
            sb.append(strs).append(" ");
        }
        entity.setRegionName(sb.toString());
        entity.setProvince(regIdName[0]);
        entity.setCity(regIdName[1]);
        entity.setDistrict(regIdName[2]);
        //收件人
        String receiver = entity.getAccountName() == null ? "" : entity.getAccountName();
        //如果长度大于8，则后面都是...
        if (receiver.length() > 8) {
            receiver = receiver.substring(0,8)+ "...";
        }
        entity.setAccountName(receiver);
        //新电话样式
        String phoneNumber = entity.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        entity.setPhone(phoneNumber);
    }

    /***
     * describe: 封装查询规则方法
     * creat_time: 2019/8/20 9:35
     * param: [paramEntity]
     * return: java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> addressRule(VAccountAddressExt paramEntity) {
        Map<String, Object> resultMap;
        //查询默认地址
        resultMap = tOpAccountAddressExtMapper.queryDefaultAddress(paramEntity);
        if (resultMap == null) {
            //没有默认收货地址则去查最近订单中的收货地址
            String addressId = tOpAccountAddressExtMapper.queryOrderAddress(paramEntity);
            if (StringUtil.isNotBlank(addressId)) {
                //查询此订单中的地址
                resultMap = tOpAccountAddressExtMapper.queryNewAddressInfo(Integer.valueOf(addressId));
                if (resultMap == null) {
                    //如果此订单中的地址不存在了 查询最近更新的地址
                    resultMap = tOpAccountAddressExtMapper.queryNewAddress(paramEntity);
                }
            } else {
                //查询最近更新的地址
                resultMap = tOpAccountAddressExtMapper.queryNewAddress(paramEntity);
            }
        }
        return resultMap;
    }
    /***
     * describe: 封装查询规则方法
     * creat_time: 2019/8/20 9:35
     * param: [paramEntity]
     * return: java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> addressRuleForApp(VAccountAddressExt paramEntity) {
        Map<String, Object> resultMap;
        //查询默认地址
        resultMap = tOpAccountAddressExtMapper.queryDefaultAddressForApp(paramEntity);
        if (resultMap == null) {
            //没有默认收货地址则去查最近订单中的收货地址
            String addressId = tOpAccountAddressExtMapper.queryOrderAddress(paramEntity);
            if (StringUtil.isNotBlank(addressId)) {
                //查询此订单中的地址
                resultMap = tOpAccountAddressExtMapper.queryAddressInfoApp(Integer.valueOf(addressId));
                if (resultMap == null) {
                    //如果此订单中的地址不存在了 查询最近更新的地址
                    resultMap = tOpAccountAddressExtMapper.queryNewAddressForApp(paramEntity);
                }
            } else {
                //查询最近更新的地址
                resultMap = tOpAccountAddressExtMapper.queryNewAddressForApp(paramEntity);
            }
        }
        return resultMap;
    }


    /**
     *
     * 地址校验
     */
    public void checkAddress(VAccountAddressExt paramEntity)throws VedengException {
        String receiverName = paramEntity.getAccountName();
        if (StringUtils.isEmpty(receiverName))
        {
            throw new VedengException(VedengErrorCode.FAIL_RECEIVER_NAME);//收货人不能为空
        }
        receiverName = receiverName.trim();
        String newStr = "";
        if(receiverName.length()>8)
        {
            //如果长度大于8，则后面都是***
            newStr = receiverName.replace( receiverName.substring(8),"...");
        }
        if(receiverName.length()>20){
            throw new VedengException(VedengErrorCode.FAIL_MORE_WORD);//收件人最多20个字
        }

        if (StringUtils.isEmpty(paramEntity.getAddressInfo())){
            throw new VedengException(VedengErrorCode.FAIL_ADDRESS);//详情地址不能为空
        }

        if (paramEntity.getAddressInfo().trim().length()>50){
            throw new VedengException(VedengErrorCode.FAIL_ADDRESS_COUNT);//详细地址最多可输入50个字
        }

        if (!StringUtils.isEmpty(paramEntity.getLabel())){
            String label = paramEntity.getLabel().trim();
            if(label.length()>10){
                throw new VedengException(VedengErrorCode.FAIL_LABEL_COUNT);//标签名称最多可输入10个字
            }
        }

        if (StringUtils.isEmpty(paramEntity.getProvince()))
        {
            throw new VedengException(VedengErrorCode.FAIL_PROVINCE);//省不能为空
        }
        if (StringUtils.isEmpty(paramEntity.getCity()))
        {
            throw new VedengException(VedengErrorCode.FAIL_CITY);//市不能为空
        }
        if (StringUtils.isEmpty(paramEntity.getDistrict()))
        {
            throw new VedengException(VedengErrorCode.FAIL_DISTRICT);//区县不能为空
        }

        if (StringUtils.isEmpty(paramEntity.getPhone())){
            throw new VedengException(VedengErrorCode.FAIL_PHONE);//手机号码不能为空
        }
        //手机号格式校验 规则:手机号格式为第一位数字1开头的11位数字手机号码，且前两位不为“10”“11”“12”。格式不正确则报错“手机号码格式不正确”
        String oneTwo = paramEntity.getPhone().trim().substring(0, 2);
        String one = paramEntity.getPhone().trim().substring(0, 1);
        boolean wrongPhFlag=!CommonConstant.STRING_ONE.equals(one) || CommonConstant.STRING_ONE_ONE.equals(oneTwo) ||
                CommonConstant.STRING_ONE_ZERO.equals(oneTwo) || CommonConstant.STRING_ONE_TWO.equals(oneTwo) || paramEntity.getPhone().trim().length() > 11;
        if (wrongPhFlag){
            throw new VedengException(VedengErrorCode.FAIL_PHONE_ISTRUE);//请输入正确的手机号码
        }
        if (StringUtils.isEmpty(paramEntity.getProvinceCode()) || StringUtils.isEmpty(paramEntity.getCityCode())
                || StringUtils.isEmpty(paramEntity.getDistrictCode())){
            throw new VedengException(VedengErrorCode.FAIL_REGSION_CODE);//请传入对应的Code
        }

        //传入省市区code码与名称是否对应
        Boolean cityFlag = existsRegionName(Integer.valueOf(paramEntity.getCityCode()), paramEntity.getCity());
        Boolean districtFlag = existsRegionName(Integer.valueOf(paramEntity.getDistrictCode()), paramEntity.getDistrict());
        Boolean provinceCodeFlag = existsRegionName(Integer.valueOf(paramEntity.getProvinceCode()), paramEntity.getProvince());
        //
        if(!cityFlag || !districtFlag || !provinceCodeFlag){
            throw new VedengException(VedengErrorCode.REGION_CODE_NAME_DISMATCH);
        }

    }

    /***
     * describe: 查询区域code码与区域名称是否相对应
     * creat_time: 2019/8/20 9:36
     * param: [regionId, regionName]
     * return: java.lang.Boolean
     */
    private Boolean existsRegionName(Integer regionId,String regionName){
        TRegion tRegion=new TRegion();
        tRegion.setRegionId(regionId);
        TRegion region = tOpAccountAddressExtMapper.queryRegionInfoById(tRegion);
        if(region==null){
            return false;
        }
        if(!region.getRegionName().equals(regionName)){
            return false;
        }
        return true;
    }

    /***
     * describe: 处理直辖市
     * creat_time: 2019/8/10 17:25
     * param: [ssq]
     * return: java.lang.String[]
     */
    private String[] dealZxs(String[] ssq) {
        boolean a = CommonConstant.ZHIXIASHI_BEIJING.equals(ssq[0]);
        boolean b = CommonConstant.ZHIXIASHI_SHANGHAI.equals(ssq[0]);
        boolean c = CommonConstant.ZHIXIASHI_TIANJING.equals(ssq[0]);
        boolean d = CommonConstant.ZHIXIASHI_CHONGQING.equals(ssq[0]);
        if (a || b || c || d) {
            //有直辖市
            String[] newSsq = new String[ssq.length - 1];
            for (int i = 0; i < ssq.length; i++) {
                if (i != 0) {
                    newSsq[i - 1] = ssq[i];
                }
            }
            return newSsq;
        } else {
            return ssq;
        }
    }
}
