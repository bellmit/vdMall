package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.VAccountAddress;
import com.vedeng.mjx.domain.VAccountAddressExample;
import com.vedeng.mjx.domain.VAccountAddressKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VAccountAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    long countByExample(VAccountAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int deleteByExample(VAccountAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VAccountAddressKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int insert(VAccountAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int insertSelective(VAccountAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    List<VAccountAddress> selectByExample(VAccountAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    VAccountAddress selectByPrimaryKey(VAccountAddressKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VAccountAddress record, @Param("example") VAccountAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VAccountAddress record, @Param("example") VAccountAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VAccountAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ACCOUNT_ADDRESS
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VAccountAddress record);
}