package com.metasohi.provision.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SysOrganizationMapperTest {

    private SysOrganizationMapper sysOrganizationMapper;

    @BeforeEach
    public void setUp() {
        sysOrganizationMapper = new SysOrganizationMapperImpl();
    }
}
