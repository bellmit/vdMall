package com.vedeng.mjx.mapper;

import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.mjx.domain.*;

import java.util.List;

import com.vedeng.mjx.domain.ext.VCategoryDto;
import org.apache.ibatis.annotations.Param;

public interface VCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    long countByExample(VCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int deleteByExample(VCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VCategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int insert(VCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int insertSelective(VCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    List<VCategory> selectByExample(VCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    VCategory selectByPrimaryKey(VCategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VCategory record, @Param("example") VCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VCategory record, @Param("example") VCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_CATEGORY
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VCategory record);

    List<VCategoryDto> queryCategorybyparentId(@Param("parentid") Integer parentid,@Param("isReCommend") Integer isReCommend);

    List<CategoryDTO> queryLevel3Category();

    CategoryDTO queryById(Integer categoryId);

    List<OneCategoryData> selectLevel1CategoryList(List<String> list);

    List<OneCategoryData> selectLevel3CategoryList(Integer random);

}