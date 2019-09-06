package com.zlsx.comzlsx.util.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author : houxm
 * @date : 2018/12/26 11:47
 * @description :
 */
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectItem {
    private String label;
    private String value;

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("id")
    public void setValue(String value) {
        this.value = value;
    }
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("name")
    public void setLabel(String label) {
        this.label = label;
    }
}