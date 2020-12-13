package net.appgate.risk.analyzer.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = {"net.appgate.risk.analyzer"})
public class Application {

    /**
     * Logger instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);


    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();

        LOG.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running!:\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name")

        );

    }


}
