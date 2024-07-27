//package com.example.register.Config;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    public DataSource dataSource() {
//
//        return DataSourceBuilder.create()
//
//          .driverClassName("com.mysql.cj.jdbc.Driver")
//
//          .url("jdbc:mysql://localhost:3306/kumaunNest?createDatabaseIfNotExist=true")
//
//          .username("root")
//
//          .password("root")
//
//          .build();
//
//    }
//
//}