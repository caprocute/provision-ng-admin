package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SysCameraMapperTest {

    private SysCameraMapper sysCameraMapper;

    @BeforeEach
    public void setUp() {
        sysCameraMapper = new SysCameraMapperImpl();
    }
}
