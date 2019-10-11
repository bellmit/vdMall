package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.VGoodsCarMx;
import com.vedeng.mjx.domain.VGoodsCarMxExample;
import com.vedeng.mjx.domain.VGoodsCarMxKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VGoodsCarMxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    long countByExample(VGoodsCarMxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int deleteByExample(VGoodsCarMxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VGoodsCarMxKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int insert(VGoodsCarMx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int insertSelective(VGoodsCarMx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    List<VGoodsCarMx> selectByExample(VGoodsCarMxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    VGoodsCarMx selectByPrimaryKey(VGoodsCarMxKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VGoodsCarMx record, @Param("example") VGoodsCarMxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VGoodsCarMx record, @Param("example") VGoodsCarMxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VGoodsCarMx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_GOODS_CAR_MX
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VGoodsCarMx record);
}