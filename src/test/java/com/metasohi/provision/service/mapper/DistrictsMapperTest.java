package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistrictsMapperTest {

    private DistrictsMapper districtsMapper;

    @BeforeEach
    public void setUp() {
        districtsMapper = new DistrictsMapperImpl();
    }
}
