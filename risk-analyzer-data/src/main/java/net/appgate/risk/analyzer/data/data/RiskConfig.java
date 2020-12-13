package net.appgate.risk.analyzer.data.data;


import net.appgate.risk.analyzer.data.constants.RiskValueEnum;

import javax.persistence.*;

@Entity
@Table(name = "risk")
public class RiskConfig {

    private String risk;
    private Integer impact;
    private Integer urgency;
    private RiskValueEnum value;



    @Id
    @Column(name = "risk")
    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    @Column(name = "impact")
    public Integer getImpact() {
        return impact;
    }

    public void setImpact(Integer impact) {
        this.impact = impact;
    }

    @Column(name = "urgency")
    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    @Column(name = "value")
    @Enumerated(EnumType.STRING)
    public RiskValueEnum getValue() {
        return value;
    }

    public void setValue(RiskValueEnum value) {
        this.value = value;
    }


}
