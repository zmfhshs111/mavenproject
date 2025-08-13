package com.rookies4.MySpringBoot.runner;

import com.rookies4.MySpringBoot.config.MyPropProperties;
import com.rookies4.MySpringBoot.config.MyEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Autowired
    private MyPropProperties myPropProperties;

    @Autowired(required = false)
    private MyEnvironment myEnvironment;

    @Override
    public void run(String... args) throws Exception {
        logger.info("MyPropProperties: {}", myPropProperties.getUsername());
        logger.debug("MyPropProperties: {}", myPropProperties.getPort());

        if (myEnvironment != null) {
            logger.info("MyEnvironment: {}", myEnvironment.getMode());
        }
    }
}
