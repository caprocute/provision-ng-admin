package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProvincesMapperTest {

    private ProvincesMapper provincesMapper;

    @BeforeEach
    public void setUp() {
        provincesMapper = new ProvincesMapperImpl();
    }
}
