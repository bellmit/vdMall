package com.vedeng.mjx.common.goods;

import java.util.List;

/**
 * @ auther:
 * @ date:  2019/8/19 18:27
 */
public class ComBineAttr {
    private List<String> technicalParameterKey;
    private List<String> technicalParameterValue;
    private List<String> specParameterKey;
    private List<String> specParameterValue;
    private List<String> performanceParameterKey;
    private List<String> performanceParameterValue;
    private String comBineAttrStr;

    public List<String> getTechnicalParameterKey() {
        return technicalParameterKey;
    }

    public void setTechnicalParameterKey(List<String> technicalParameterKey) {
        this.technicalParameterKey = technicalParameterKey;
    }

    public List<String> getTechnicalParameterValue() {
        return technicalParameterValue;
    }

    public void setTechnicalParameterValue(List<String> technicalParameterValue) {
        this.technicalParameterValue = technicalParameterValue;
    }

    public List<String> getSpecParameterKey() {
        return specParameterKey;
    }

    public void setSpecParameterKey(List<String> specParameterKey) {
        this.specParameterKey = specParameterKey;
    }

    public List<String> getSpecParameterValue() {
        return specParameterValue;
    }

    public void setSpecParameterValue(List<String> specParameterValue) {
        this.specParameterValue = specParameterValue;
    }

    public List<String> getPerformanceParameterKey() {
        return performanceParameterKey;
    }

    public void setPerformanceParameterKey(List<String> performanceParameterKey) {
        this.performanceParameterKey = performanceParameterKey;
    }

    public List<String> getPerformanceParameterValue() {
        return performanceParameterValue;
    }

    public void setPerformanceParameterValue(List<String> performanceParameterValue) {
        this.performanceParameterValue = performanceParameterValue;
    }

    public String getComBineAttrStr() {
        return comBineAttrStr;
    }

    public void setComBineAttrStr() {
        StringBuilder sb = new StringBuilder();
        if (technicalParameterKey != null) {
            for (int i = 0; i < technicalParameterKey.size(); i++) {
                sb.append(technicalParameterKey.get(i)).append("：").append(technicalParameterValue.get(i)).append(" ");
            }
        }

        if (specParameterKey != null) {
            for (int i = 0; i < specParameterKey.size(); i++) {
                sb.append(specParameterKey.get(i)).append("：").append(specParameterValue.get(i)).append(" ");
            }
        }
        if (performanceParameterKey != null) {
            for (int i = 0; i < performanceParameterKey.size(); i++) {
                sb.append(performanceParameterKey.get(i)).append("：").append(performanceParameterValue.get(i)).append(" ");
            }
        }

        this.comBineAttrStr = sb.toString();
    }
}
