package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.VMessageType;
import com.vedeng.mjx.domain.VMessageTypeExample;
import com.vedeng.mjx.domain.VMessageTypeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VMessageTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    long countByExample(VMessageTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int deleteByExample(VMessageTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VMessageTypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int insert(VMessageType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int insertSelective(VMessageType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    List<VMessageType> selectByExample(VMessageTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    VMessageType selectByPrimaryKey(VMessageTypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VMessageType record, @Param("example") VMessageTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VMessageType record, @Param("example") VMessageTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VMessageType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_MESSAGE_TYPE
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VMessageType record);
}