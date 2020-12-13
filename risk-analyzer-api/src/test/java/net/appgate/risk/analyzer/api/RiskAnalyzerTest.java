package net.appgate.risk.analyzer.api;

import net.appgate.risk.analyzer.api.error.RiskAnalyzerException;
import net.appgate.risk.analyzer.data.dto.RiskConfigDto;
import net.appgate.risk.analyzer.data.repository.RiskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import java.util.Collections;

import static net.appgate.risk.analyzer.api.constants.Constants.IMPACT_VARIABLES;
import static net.appgate.risk.analyzer.api.constants.Constants.URGENCY_VARIABLES;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RiskAnalyzerTest {
    private RiskRepository riskRepository;
    private RiskAnalyzer riskAnalyzer;

    @Before
    public void setUp() {
        riskRepository = Mockito.mock(RiskRepository.class);
        riskAnalyzer = new RiskAnalyzer(riskRepository);
    }

    @Test(expected = RiskAnalyzerException.class)
    public void processRiskExceptionRiskNotValid() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto(null, 1, 1);
        riskAnalyzer.processRisk(riskConfigDto);
    }


    @Test(expected = RiskAnalyzerException.class)
    public void processRiskExceptionUrgencyBelowRange() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("riskDummy", 0, 1);
        riskAnalyzer.processRisk(riskConfigDto);

    }

    @Test(expected = RiskAnalyzerException.class)
    public void processRiskExceptionUrgencyAboveRange() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("riskDummy", URGENCY_VARIABLES + 1, 1);
        riskAnalyzer.processRisk(riskConfigDto);
    }

    @Test(expected = RiskAnalyzerException.class)
    public void processRiskExceptionImpactBelowRange() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("riskDummy", 1, 0);
        riskAnalyzer.processRisk(riskConfigDto);

    }

    @Test(expected = RiskAnalyzerException.class)
    public void processRiskExceptionImpactAboveRange() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("riskDummy", 1, IMPACT_VARIABLES + 1);
        riskAnalyzer.processRisk(riskConfigDto);

    }

    @Test
    public void processRiskWhitOutError() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("riskDummy", 1, 1);
        riskAnalyzer.processRisk(riskConfigDto);
        verify(riskRepository, times(1)).save(any());
    }

    @Test
    public void getRisksMatrixTest() throws RiskAnalyzerException {
        when(riskRepository.findAll()).thenReturn(Collections.emptyList());
        riskAnalyzer.getRisks();
        verify(riskRepository, times(1)).findAll();
    }

    private RiskConfigDto createRiskConfigDto(String risk, int urgency, int impact) {
        final RiskConfigDto riskConfigDto = new RiskConfigDto();
        riskConfigDto.setRisk(risk);
        riskConfigDto.setUrgency(urgency);
        riskConfigDto.setImpact(impact);
        return riskConfigDto;

    }
}