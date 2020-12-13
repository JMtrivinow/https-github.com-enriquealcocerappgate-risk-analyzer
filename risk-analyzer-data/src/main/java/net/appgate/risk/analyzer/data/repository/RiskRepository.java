package net.appgate.risk.analyzer.data.repository;

import net.appgate.risk.analyzer.data.data.RiskConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRepository extends JpaRepository<RiskConfig, Long>,
        JpaSpecificationExecutor<RiskConfig>{
}
