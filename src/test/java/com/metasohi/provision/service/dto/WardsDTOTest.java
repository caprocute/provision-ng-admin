package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WardsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WardsDTO.class);
        WardsDTO wardsDTO1 = new WardsDTO();
        wardsDTO1.setCode("id1");
        WardsDTO wardsDTO2 = new WardsDTO();
        assertThat(wardsDTO1).isNotEqualTo(wardsDTO2);
        wardsDTO2.setCode(wardsDTO1.getCode());
        assertThat(wardsDTO1).isEqualTo(wardsDTO2);
        wardsDTO2.setCode("id2");
        assertThat(wardsDTO1).isNotEqualTo(wardsDTO2);
        wardsDTO1.setCode(null);
        assertThat(wardsDTO1).isNotEqualTo(wardsDTO2);
    }
}
