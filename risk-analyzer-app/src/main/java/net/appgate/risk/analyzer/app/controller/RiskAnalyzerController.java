package net.appgate.risk.analyzer.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.appgate.risk.analyzer.api.error.RiskAnalyzerException;
import net.appgate.risk.analyzer.data.dto.RiskConfigDto;
import net.appgate.risk.analyzer.data.dto.RiskValueDto;
import net.appgate.risk.analyzer.api.RiskAnalyzer;
import net.appgate.risk.analyzer.app.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.appgate.risk.analyzer.api.constants.Constants.IMPACT_VARIABLES;
import static net.appgate.risk.analyzer.api.constants.Constants.URGENCY_VARIABLES;


@Api(value = "Risk Analyzer Service Controller")
@RequestMapping("/v1")
@RestController
public class RiskAnalyzerController {
    private static final Logger LOG = LoggerFactory.getLogger(RiskAnalyzerController.class);

    private final RiskAnalyzer riskAnalyzer;

    @Autowired
    public RiskAnalyzerController(RiskAnalyzer riskAnalyzer) {
        this.riskAnalyzer = riskAnalyzer;
    }

    @ApiOperation(value = "Add or update a risk", response = ResponseEntity.class)
    @PutMapping(value = {"/"})
    public ResponseEntity<Object> putRisk(
            @ApiParam(name = "riskConfig", value = "Object.\n Structure: [\n{risk:impact:urgent}\n]", required = true)
            @RequestBody final RiskConfigDto riskConfig) {
        try {
            validateRiskConfig(riskConfig);

            riskAnalyzer.processRisk(riskConfig);


        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpUtils.noCacheHeaders(), HttpStatus.BAD_REQUEST);
        } catch (RiskAnalyzerException e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpUtils.noCacheHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpUtils.noCacheHeaders(), HttpStatus.OK);

    }

    @ApiOperation(value = "Get List risk", response = ResponseEntity.class)
    @GetMapping(value = {"/"})
    public ResponseEntity<List<RiskValueDto>> getRisksMatrix() {
        return new ResponseEntity<>(riskAnalyzer.getRisks(), HttpStatus.OK);
    }

    private void validateRiskConfig(RiskConfigDto riskConfig){
        if (riskConfig == null) {
            throw new IllegalArgumentException("risk config object is null");
        }

        if (riskConfig.getRisk() == null || StringUtils.isEmpty(riskConfig.getRisk())) {
            throw new IllegalArgumentException("risk value not valid ");
        }

        if (riskConfig.getImpact() <= 0 || riskConfig.getImpact() > IMPACT_VARIABLES) {
            throw new IllegalArgumentException("impact should be a value between 1 and "+IMPACT_VARIABLES+", recieved: "+riskConfig.getImpact());
        }
        if (riskConfig.getUrgency() <= 0 || riskConfig.getUrgency() > URGENCY_VARIABLES) {
            throw new IllegalArgumentException("urgency should be a value between 1 and "+URGENCY_VARIABLES+", recieved: "+riskConfig.getUrgency());
        }
    }
}
