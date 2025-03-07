package com.github.jaguzmanb1.quasar.config;

import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.location.TrilaterationCalculator;
import com.github.jaguzmanb1.quasar.service.messages.DefaultMessageDecoder;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public LocationCalculatorInterface locationCalculator() {
        return new TrilaterationCalculator();
    }

    @Bean
    public MessageDecoderInterface messageDecoder() {
        return new DefaultMessageDecoder();
    }
}
