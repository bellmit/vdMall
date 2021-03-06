package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.TOpGoodsExt;
import com.vedeng.mjx.domain.TOpGoodsExtExample;
import com.vedeng.mjx.domain.TOpGoodsExtKey;
import com.vedeng.mjx.domain.TOpGoodsExtWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOpGoodsExtMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    long countByExample(TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int deleteByExample(TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(TOpGoodsExtKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int insert(TOpGoodsExtWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int insertSelective(TOpGoodsExtWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    List<TOpGoodsExtWithBLOBs> selectByExampleWithBLOBs(TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    List<TOpGoodsExt> selectByExample(TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    TOpGoodsExtWithBLOBs selectByPrimaryKey(TOpGoodsExtKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TOpGoodsExtWithBLOBs record, @Param("example") TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") TOpGoodsExtWithBLOBs record, @Param("example") TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TOpGoodsExt record, @Param("example") TOpGoodsExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TOpGoodsExtWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(TOpGoodsExtWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OP_GOODS_EXT
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TOpGoodsExt record);
}