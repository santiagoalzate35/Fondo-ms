package com.bts.api.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CorsConfigTest {
    private CorsConfig corsConfig;
    @BeforeEach
    public void setUp() {
        corsConfig = new CorsConfig();
    }
    @Test
    public void corsFilterWithOriginsAsteriskTest(){
        Assertions.assertNotNull(corsConfig.corsFilter("*"));
    }
    @Test
    public void corsFilterWithOriginsDifferentAsteriskTest(){
        Assertions.assertNotNull(corsConfig.corsFilter("test"));
    }
}