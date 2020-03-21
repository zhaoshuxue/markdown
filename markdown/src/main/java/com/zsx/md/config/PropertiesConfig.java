package com.zsx.md.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesConfig {


    @Value("${mdFilePath}")
    private String mdFilePath;



}
