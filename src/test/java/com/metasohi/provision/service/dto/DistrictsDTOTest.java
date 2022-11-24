package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictsDTO.class);
        DistrictsDTO districtsDTO1 = new DistrictsDTO();
        districtsDTO1.setCode("id1");
        DistrictsDTO districtsDTO2 = new DistrictsDTO();
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
        districtsDTO2.setCode(districtsDTO1.getCode());
        assertThat(districtsDTO1).isEqualTo(districtsDTO2);
        districtsDTO2.setCode("id2");
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
        districtsDTO1.setCode(null);
        assertThat(districtsDTO1).isNotEqualTo(districtsDTO2);
    }
}
