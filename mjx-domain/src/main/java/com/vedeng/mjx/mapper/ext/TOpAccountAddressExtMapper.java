package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.domain.ext.VAccountAddressExt;
import com.vedeng.mjx.mapper.TOpAccountAddressMapper;

import java.util.List;
import java.util.Map;

/**
 * @ auther:Jax
 * @ date:  2019/7/1 14:28
 */
public interface TOpAccountAddressExtMapper extends TOpAccountAddressMapper {
    List<TRegion> queryRegionInfo(VAccountAddressExt entity);
    List<TRegion> queryRegionInfomation(TRegion region);
    TRegion queryRegionInfoById(TRegion region);
    List<TRegion> queryRegionInfoOneTime(Integer parentId);

    int insertAddressInfo(VAccountAddressExt paramEntity);

    List<VAccountAddressExt> queryAddressList(VAccountAddressExt paramEntity);

    int updateAddressDefault(VAccountAddressExt paramEntity);

    int updateTicketDefault(VAccountAddressExt paramEntity);

    VAccountAddressExt queryAddressInfo(VAccountAddressExt paramEntity);

    int updateAddressInfo(VAccountAddressExt paramEntity);

    int deleteAddressInfo(VAccountAddressExt paramEntity);

    Map<String,Object> queryDefaultAddress(VAccountAddressExt paramEntity);
    Map<String,Object> queryDefaultAddressForApp(VAccountAddressExt paramEntity);

    String queryOrderAddress(VAccountAddressExt paramEntity);

    Map<String,Object> queryNewAddress(VAccountAddressExt paramEntity);
    Map<String,Object> queryNewAddressForApp(VAccountAddressExt paramEntity);

    Map<String, Object> queryNewAddressInfo(Integer addressId);
    Map<String, Object> queryAddressInfoApp(Integer addressId);

    VAccountAddressExt selectAddressById(Integer accountAddressId);

    Integer addressCount(Integer accountAddressId);

    List<VAccountAddressExt> queryNoSyncErpIds(Map param);
    Integer updateSyncAddr(Map param);
}
