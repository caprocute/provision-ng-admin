package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WardsMapperTest {

    private WardsMapper wardsMapper;

    @BeforeEach
    public void setUp() {
        wardsMapper = new WardsMapperImpl();
    }
}
