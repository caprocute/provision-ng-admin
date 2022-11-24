package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysAreaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysAreaDTO.class);
        SysAreaDTO sysAreaDTO1 = new SysAreaDTO();
        sysAreaDTO1.setId(1L);
        SysAreaDTO sysAreaDTO2 = new SysAreaDTO();
        assertThat(sysAreaDTO1).isNotEqualTo(sysAreaDTO2);
        sysAreaDTO2.setId(sysAreaDTO1.getId());
        assertThat(sysAreaDTO1).isEqualTo(sysAreaDTO2);
        sysAreaDTO2.setId(2L);
        assertThat(sysAreaDTO1).isNotEqualTo(sysAreaDTO2);
        sysAreaDTO1.setId(null);
        assertThat(sysAreaDTO1).isNotEqualTo(sysAreaDTO2);
    }
}
