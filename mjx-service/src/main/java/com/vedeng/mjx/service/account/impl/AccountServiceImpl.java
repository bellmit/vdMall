package com.vedeng.mjx.service.account.impl;

import com.vedeng.mjx.domain.*;
import com.vedeng.mjx.mapper.TopAccountJxMapper;
import com.vedeng.mjx.mapper.VWxPhoneMapper;
import com.vedeng.mjx.mapper.ext.TopAccountJxExtMapper;
import com.vedeng.mjx.service.account.AccountService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AccountServiceImpl
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-07-01 21:38
 * @Version 1.0
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private TopAccountJxExtMapper topAccountJxExtMapper;

    @Resource
    private TopAccountJxMapper topAccountJxMapper;
    @Resource
    private VWxPhoneMapper wxPhoneMapper;

    @Override
    public TopAccountJx selectBySsoAcountId(Integer ssoAccountId) {
        return topAccountJxExtMapper.selectBySsoAcountId(ssoAccountId);
    }

    @Override
    public Integer insertSelective(TopAccountJx topAccountJx) {
        return topAccountJxMapper.insertSelective(topAccountJx);
    }

    @Override
    public Integer updateByMobileSelective(TopAccountJx topAccountJx) {
        return topAccountJxExtMapper.updateByMobileSelective(topAccountJx);
    }

    @Override
    public TopAccountJx selectByAcountId(Integer acountId) {
        return topAccountJxExtMapper.selectByAcountId(acountId);
    }

    @Override
    public boolean updateTopAccountJxByAccountId(TopAccountJx topAccountJx) {
        return topAccountJxExtMapper.updateTopAccountJxByAccountId(topAccountJx);
    }

    @Override
    public TopAccountJx selectTopAccountJxByOpenId(String openId,String phone) {
        return topAccountJxExtMapper.selectTopAccountJxByOpenId(openId,phone);
    }

    @Override
    public TopAccountJx selectByPhone(String phone) {
        return topAccountJxExtMapper.selectByPhone(phone);
    }

    @Override
    public int clearOpenidByPhone(String phone) {
        return topAccountJxExtMapper.clearOpenidByPhone(phone);
    }

    @Override
    public VWxPhone selectWxphoneByPhone(String phone) {
        VWxPhoneExample example = new VWxPhoneExample();
        example.createCriteria().andPhoneEqualTo(phone).andIsDelEqualTo(0);
        List<VWxPhone> wxPhoneList = wxPhoneMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(wxPhoneList)){
            return null;
        }
        return wxPhoneList.get(0);
    }

    @Override
    public VWxPhone selectWxphoneByOpenid(String openid) {

        VWxPhoneExample example = new VWxPhoneExample();
        example.createCriteria().andOpenidEqualTo(openid).andIsDelEqualTo(0);
        List<VWxPhone> wxPhoneList = wxPhoneMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(wxPhoneList)){
            return null;
        }
        return wxPhoneList.get(0);
    }

    @Override
    public VWxPhone selectWxphone(String phone, String openid) {
        VWxPhoneExample example = new VWxPhoneExample();
        example.createCriteria().andPhoneEqualTo(phone).andOpenidEqualTo(openid).andIsDelEqualTo(0);
        List<VWxPhone> wxPhoneList = wxPhoneMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(wxPhoneList)){
            return null;
        }
        return wxPhoneList.get(0);
    }

    @Override
    public int saveWxPhone(String phone, String openid) {
        VWxPhone wxPhone = new VWxPhone();
        wxPhone.setPhone(phone);
        wxPhone.setOpenid(openid);
        wxPhone.setAddTime(new Date());
        wxPhone.setModTime(new Date());
        wxPhone.setIsDel(0);
        return wxPhoneMapper.insert(wxPhone);
    }

    @Override
    public int updatexPhone(String phone, String openid) {
        VWxPhone record = new VWxPhone();
        record.setPhone(phone);
        VWxPhoneExample example = new VWxPhoneExample();
        example.createCriteria().andOpenidEqualTo(openid);
        return wxPhoneMapper.updateByExampleSelective(record,example);
    }

    @Override
    public void initialize() {
        List<TopAccountJx> topAccountJxList = topAccountJxExtMapper.selectAccountList();
        for(TopAccountJx topAccountJx : topAccountJxList){
            VWxPhoneExample example = new VWxPhoneExample();
            example.createCriteria().andPhoneEqualTo(topAccountJx.getMobile()).andOpenidEqualTo(topAccountJx.getWeixinOpenid()).andIsDelEqualTo(0);
            List<VWxPhone> wxPhoneList = wxPhoneMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(wxPhoneList)){
                VWxPhone wxPhone = new VWxPhone();
                wxPhone.setPhone(topAccountJx.getMobile());
                wxPhone.setOpenid(topAccountJx.getWeixinOpenid());
                wxPhone.setAddTime(new Date());
                wxPhone.setModTime(new Date());
                wxPhone.setIsDel(0);
                wxPhoneMapper.insert(wxPhone);
            }
        }
    }

}
