package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.domain.TRegionExample;
import com.vedeng.mjx.domain.TRegionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TRegionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    long countByExample(TRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int deleteByExample(TRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(TRegionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int insert(TRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int insertSelective(TRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    List<TRegion> selectByExample(TRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    TRegion selectByPrimaryKey(TRegionKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TRegion record, @Param("example") TRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TRegion record, @Param("example") TRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_REGION
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TRegion record);
}