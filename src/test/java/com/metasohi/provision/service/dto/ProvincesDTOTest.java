package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvincesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvincesDTO.class);
        ProvincesDTO provincesDTO1 = new ProvincesDTO();
        provincesDTO1.setCode("id1");
        ProvincesDTO provincesDTO2 = new ProvincesDTO();
        assertThat(provincesDTO1).isNotEqualTo(provincesDTO2);
        provincesDTO2.setCode(provincesDTO1.getCode());
        assertThat(provincesDTO1).isEqualTo(provincesDTO2);
        provincesDTO2.setCode("id2");
        assertThat(provincesDTO1).isNotEqualTo(provincesDTO2);
        provincesDTO1.setCode(null);
        assertThat(provincesDTO1).isNotEqualTo(provincesDTO2);
    }
}
