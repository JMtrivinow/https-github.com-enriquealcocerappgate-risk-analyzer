package net.appgate.risk.analyzer.data.config;


import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EntityScan("net.appgate.risk.analyzer.data")
@EnableJpaRepositories({"net.appgate.risk.analyzer.data.repository"})
@EnableConfigurationProperties(DataSourceProperties.class)
public class DatabaseConfiguration {

    // -----------------------------------------------------------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Environment resource
     */
    private final Environment env;



    // -----------------------------------------------------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------------------------------------------------

    public DatabaseConfiguration(@Autowired Environment env                                 ) {
        this.env = env;

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Bean definitions
    // -----------------------------------------------------------------------------------------------------------------


    /**
     * Hibernate 5 Module Bean definition
     *
     * @return A bean definition of {@link Hibernate5Module} instance
     */
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
