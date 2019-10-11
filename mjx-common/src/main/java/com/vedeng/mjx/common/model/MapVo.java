package com.vedeng.mjx.common.model;

import java.io.Serializable;

/**
 * @Description: 数据模型
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/7/1
 */
public class MapVo<T, E> implements Serializable {

    private static final long serialVersionUID = 2241534282362769768L;

    private T key;

    private E value;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
