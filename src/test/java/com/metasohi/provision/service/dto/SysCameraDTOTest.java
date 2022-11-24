package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysCameraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysCameraDTO.class);
        SysCameraDTO sysCameraDTO1 = new SysCameraDTO();
        sysCameraDTO1.setId(1L);
        SysCameraDTO sysCameraDTO2 = new SysCameraDTO();
        assertThat(sysCameraDTO1).isNotEqualTo(sysCameraDTO2);
        sysCameraDTO2.setId(sysCameraDTO1.getId());
        assertThat(sysCameraDTO1).isEqualTo(sysCameraDTO2);
        sysCameraDTO2.setId(2L);
        assertThat(sysCameraDTO1).isNotEqualTo(sysCameraDTO2);
        sysCameraDTO1.setId(null);
        assertThat(sysCameraDTO1).isNotEqualTo(sysCameraDTO2);
    }
}
