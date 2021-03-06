package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.VCancel;
import com.vedeng.mjx.domain.VCancelExample;
import com.vedeng.mjx.domain.VCancelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VCancelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    long countByExample(VCancelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int deleteByExample(VCancelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VCancelKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int insert(VCancel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int insertSelective(VCancel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    List<VCancel> selectByExample(VCancelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    VCancel selectByPrimaryKey(VCancelKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VCancel record, @Param("example") VCancelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VCancel record, @Param("example") VCancelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VCancel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CANCEL
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VCancel record);

    List<VCancel> cancelList();
}