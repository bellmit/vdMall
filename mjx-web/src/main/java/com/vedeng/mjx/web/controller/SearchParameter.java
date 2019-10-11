package com.vedeng.mjx.web.controller;

import java.io.Serializable;

/**
 * @author ：Gavin.li
 * @date ：Created in 2019/6/30 19:53
 * @description：
 * @modified By：
 * @version:
 */
public class SearchParameter implements Serializable {

    /**
     *  搜索关键词
     */
    private String keywords;

    /**
     *  一级分类id
     */
    private Integer category=0;

    /**
     *  首页的一级分类名称
     */
    private String category_name;

    /**
     *  点击首页进入的品牌
     */
    private Integer brand = 0;

    /**
     *  点击首页的品牌名称
     */
    private String brand_name;


    /**
     *  筛选中的分类id
     */
    private Integer filter_category = 0;

    /**
     * 筛选中的品牌id,多选时，用都好隔开
     */
    private String filter_brands;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Integer getFilter_category() {
        return filter_category;
    }

    public void setFilter_category(Integer filter_category) {
        this.filter_category = filter_category;
    }

    public String getFilter_brands() {
        return filter_brands;
    }

    public void setFilter_brands(String filter_brands) {
        this.filter_brands = filter_brands;
    }
}
