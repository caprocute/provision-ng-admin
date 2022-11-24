package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministrativeRegionsMapperTest {

    private AdministrativeRegionsMapper administrativeRegionsMapper;

    @BeforeEach
    public void setUp() {
        administrativeRegionsMapper = new AdministrativeRegionsMapperImpl();
    }
}
