package com.epam.esm.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan(basePackages = "com.epam.esm")
@Import(PersistenceConfig.class)
@EnableTransactionManagement
public class ServiceConfig {
}
