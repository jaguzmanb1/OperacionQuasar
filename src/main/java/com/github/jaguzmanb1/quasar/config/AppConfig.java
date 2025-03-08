package com.github.jaguzmanb1.quasar.config;

import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.location.TrilaterationCalculator;
import com.github.jaguzmanb1.quasar.service.messages.DefaultMessageDecoder;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;


@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocationCalculatorInterface locationCalculator() {
        return new TrilaterationCalculator();
    }

    @Bean
    public MessageDecoderInterface messageDecoder() {
        return new DefaultMessageDecoder();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String url = env.getProperty("url");

        if (url == null || url.trim().isEmpty()) {
            url = "jdbc:sqlite:./database.db";
        }

        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        dataSource.setUrl(url);
        dataSource.setUsername(env.getProperty("username", "sa"));
        dataSource.setPassword(env.getProperty("password", "sa"));

        return dataSource;
    }

}
