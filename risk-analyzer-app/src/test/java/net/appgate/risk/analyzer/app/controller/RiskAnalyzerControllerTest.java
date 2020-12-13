package net.appgate.risk.analyzer.app.controller;


import net.appgate.risk.analyzer.api.RiskAnalyzer;
import net.appgate.risk.analyzer.api.error.RiskAnalyzerException;
import net.appgate.risk.analyzer.data.dto.RiskConfigDto;
import net.appgate.risk.analyzer.data.dto.RiskValueDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static net.appgate.risk.analyzer.api.constants.Constants.IMPACT_VARIABLES;
import static net.appgate.risk.analyzer.api.constants.Constants.URGENCY_VARIABLES;
import static org.mockito.Mockito.*;


public class RiskAnalyzerControllerTest {
    private RiskAnalyzer riskAnalyzer;
    private RiskAnalyzerController riskAnalyzerController;

    @Before
    public void setUp() {
        riskAnalyzer = Mockito.mock(RiskAnalyzer.class);
        riskAnalyzerController = new RiskAnalyzerController(riskAnalyzer);
    }


    @Test
    public void responseIsBadRequestRiskNotValid() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto(null, 1, 1);
        final ResponseEntity<Object> responseEntity = riskAnalyzerController.putRisk(riskConfigDto);
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void responseIsBadRequestUrgencyNotValid() throws RiskAnalyzerException {

        final RiskConfigDto riskConfigDto1 = createRiskConfigDto("RiskDummy", 0, 1);
        final ResponseEntity<Object> responseEntity1 = riskAnalyzerController.putRisk(riskConfigDto1);
        Assert.assertTrue(responseEntity1.getStatusCode() == HttpStatus.BAD_REQUEST);

        final RiskConfigDto riskConfigDto2 = createRiskConfigDto("RiskDummy", URGENCY_VARIABLES + 1, 1);
        final ResponseEntity<Object> responseEntity2 = riskAnalyzerController.putRisk(riskConfigDto2);
        Assert.assertTrue(responseEntity2.getStatusCode() == HttpStatus.BAD_REQUEST);

    }

    @Test
    public void responseIsBadRequestImpactNotValid() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto1 = createRiskConfigDto("RiskDummy", 1, 0);
        final ResponseEntity<Object> responseEntity1 = riskAnalyzerController.putRisk(riskConfigDto1);
        Assert.assertTrue(responseEntity1.getStatusCode() == HttpStatus.BAD_REQUEST);

        final RiskConfigDto riskConfigDto2 = createRiskConfigDto("RiskDummy", 1, IMPACT_VARIABLES + 1);
        final ResponseEntity<Object> responseEntity2 = riskAnalyzerController.putRisk(riskConfigDto2);
        Assert.assertTrue(responseEntity2.getStatusCode() == HttpStatus.BAD_REQUEST);


    }

    @Test
    public void riskAnalyzerPutRiskIsCalled() throws RiskAnalyzerException {
        final RiskConfigDto riskConfigDto = createRiskConfigDto("RiskDummy", 1, 1);
        final ResponseEntity<Object> responseEntity = riskAnalyzerController.putRisk(riskConfigDto);
        verify(riskAnalyzer, times(1)).processRisk(riskConfigDto);
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void riskAnalyzerGetRiskMatrixRIsCalled() throws RiskAnalyzerException {
        when(riskAnalyzer.getRisks()).thenReturn(Collections.emptyList());
        final ResponseEntity<List<RiskValueDto>> responseEntity = riskAnalyzerController.getRisksMatrix();
        verify(riskAnalyzer, times(1)).getRisks();
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }


    private RiskConfigDto createRiskConfigDto(String risk, int urgency, int impact) {
        final RiskConfigDto riskConfigDto = new RiskConfigDto();
        riskConfigDto.setRisk(risk);
        riskConfigDto.setUrgency(urgency);
        riskConfigDto.setImpact(impact);
        return riskConfigDto;

    }


}