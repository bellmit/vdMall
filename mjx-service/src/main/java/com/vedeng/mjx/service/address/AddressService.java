package com.vedeng.mjx.service.address;

import com.vedeng.mjx.common.address.TraderMjxContactAdderss;
import com.vedeng.mjx.common.exception.VedengException;
import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ auther:
 * @ date:  2019/7/1 13:41
 */
public interface AddressService {

    VAccountAddressExt queryRegionInfo(VAccountAddressExt flagId);

    List<TRegion> queryRegionInfomation(TRegion region) throws VedengException;

    Integer queryCount(Integer userId);

    VAccountAddressExt insertAddressInfo(VAccountAddressExt paramEntity);

    List<VAccountAddressExt> queryAddressList(VAccountAddressExt paramEntity);

    List<VAccountAddressExt> appQueryAddressList(VAccountAddressExt paramEntity) throws VedengException;

    VAccountAddressExt queryAddressInfo(VAccountAddressExt paramEntity);

    int deleteAddressInfo(VAccountAddressExt paramEntity);

    Map<String,Object> queryDefaultAddress(VAccountAddressExt paramEntity);

    Map<String, Object> queryDefaultAddressForApp(VAccountAddressExt paramEntity) throws VedengException;

    VAccountAddressExt selectVAccountAddressById(Integer accountAddressId);

    List<TraderMjxContactAdderss> queryNoSyncToErpAddr(Map param);

    JSONObject httpSyncToErp(TraderMjxContactAdderss address);

    @Transactional
    Integer updateSyncAddr(Map param);
}
