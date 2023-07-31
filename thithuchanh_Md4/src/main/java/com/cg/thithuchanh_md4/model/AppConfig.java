package com.cg.thithuchanh_md4.model;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
