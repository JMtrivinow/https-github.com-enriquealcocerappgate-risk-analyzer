package net.appgate.risk.analyzer.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.appgate.risk.analyzer.data.constants.RiskValueEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskValueDto {
    private String risk;
    private RiskValueEnum value;


    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }


    public RiskValueEnum getValue() {
        return value;
    }

    public void setValue(RiskValueEnum value) {
        this.value = value;
    }
}
