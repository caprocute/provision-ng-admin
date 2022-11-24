package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministrativeUnitsMapperTest {

    private AdministrativeUnitsMapper administrativeUnitsMapper;

    @BeforeEach
    public void setUp() {
        administrativeUnitsMapper = new AdministrativeUnitsMapperImpl();
    }
}
