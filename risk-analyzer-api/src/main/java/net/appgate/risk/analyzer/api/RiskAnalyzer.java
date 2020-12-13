package net.appgate.risk.analyzer.api;


import net.appgate.risk.analyzer.api.error.RiskAnalyzerException;
import net.appgate.risk.analyzer.data.repository.RiskRepository;
import net.appgate.risk.analyzer.data.constants.RiskValueEnum;
import net.appgate.risk.analyzer.data.data.RiskConfig;
import net.appgate.risk.analyzer.data.dto.RiskConfigDto;
import net.appgate.risk.analyzer.data.dto.RiskValueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static net.appgate.risk.analyzer.api.constants.Constants.IMPACT_VARIABLES;
import static net.appgate.risk.analyzer.api.constants.Constants.URGENCY_VARIABLES;
import static net.appgate.risk.analyzer.data.constants.RiskValueEnum.*;

@Service
public class RiskAnalyzer {

    private final RiskRepository riskRepository;
    private static final RiskValueEnum[][] RISK_MATRIX = {
            {LOW, LOW, MEDIUM},
            {LOW, MEDIUM, HIGH},
            {MEDIUM, HIGH, HIGH},
            {HIGH, CRITICAL, CRITICAL},
    };

    @Autowired
    public RiskAnalyzer(RiskRepository riskRepository) {
        this.riskRepository = riskRepository;

    }

    public void processRisk(RiskConfigDto riskConfigDto) throws RiskAnalyzerException {
        if(riskConfigDto==null){
            throw new RiskAnalyzerException("risk config object is null");
        }

        if (riskConfigDto.getRisk() == null || StringUtils.isEmpty(riskConfigDto.getRisk())) {
            throw new RiskAnalyzerException("risk value not valid ");
        }
        final RiskConfig riskConfig = new RiskConfig();
        riskConfig.setImpact(riskConfigDto.getImpact());
        riskConfig.setUrgency(riskConfigDto.getUrgency());
        riskConfig.setRisk(riskConfigDto.getRisk());
        riskConfig.setValue(findRiskValue(riskConfigDto.getImpact() , riskConfigDto.getUrgency() ));
        riskRepository.save(riskConfig);

    }

    public List<RiskValueDto> getRisks() {
        final List<RiskValueDto> riskValueList = new ArrayList<>();
        final List<RiskConfig> risks = riskRepository.findAll();
        for (RiskConfig riskConfig : risks) {
            final RiskValueDto riskValueDto = new RiskValueDto();
            riskValueDto.setRisk(riskConfig.getRisk());
            riskValueDto.setValue(riskConfig.getValue());
            riskValueList.add(riskValueDto);
        }

        return riskValueList;

    }

    private RiskValueEnum findRiskValue(int impact, int urgency) throws RiskAnalyzerException {
        if(impact<=0 || impact>IMPACT_VARIABLES){
            throw  new RiskAnalyzerException("impact should be a value between 1 and "+IMPACT_VARIABLES+", recieved: "+impact);
        }
        if(urgency<=0 || urgency>URGENCY_VARIABLES){
            throw  new RiskAnalyzerException("urgency should be a value between 1 and "+URGENCY_VARIABLES+", recieved: "+urgency);
        }
        return RISK_MATRIX[urgency-1][impact-1];
    }
}
