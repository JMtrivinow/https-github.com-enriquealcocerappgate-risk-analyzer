package net.appgate.risk.analyzer.api.error;

public class RiskAnalyzerException  extends  Exception{
    public RiskAnalyzerException(String ex){
        super(ex);
    }

    public RiskAnalyzerException(Exception ex){
        super(ex);
    }
}
