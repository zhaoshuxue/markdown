package com.zsx.md.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

//@Configuration
@Component
@PropertySource(value = {"classpath:prop/url.yml"}, encoding = "utf-8", ignoreResourceNotFound = true, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "allowurl")
@Data
public class AllowURL {

    private List<String> urls;

}
