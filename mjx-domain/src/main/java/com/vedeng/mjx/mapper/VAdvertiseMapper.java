package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.*;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VAdvertiseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    long countByExample(VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int deleteByExample(VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VAdvertiseKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int insert(VAdvertiseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int insertSelective(VAdvertiseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    List<VAdvertiseWithBLOBs> selectByExampleWithBLOBs(VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    List<VAdvertise> selectByExample(VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    VAdvertiseWithBLOBs selectByPrimaryKey(VAdvertiseKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VAdvertiseWithBLOBs record, @Param("example") VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") VAdvertiseWithBLOBs record, @Param("example") VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VAdvertise record, @Param("example") VAdvertiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VAdvertiseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(VAdvertiseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ADVERTISE
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VAdvertise record);

    List<BannerSourceData> selectBannerList(@Param("platfromPortId") Integer platfromPortId,@Param("comments") String comments);

    List<BannerSourceData> selectBannerSpare(@Param("platfromPortId") Integer platfromPortId,@Param("comments") String comments);

}