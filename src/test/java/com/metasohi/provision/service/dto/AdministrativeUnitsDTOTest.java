package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeUnitsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeUnitsDTO.class);
        AdministrativeUnitsDTO administrativeUnitsDTO1 = new AdministrativeUnitsDTO();
        administrativeUnitsDTO1.setId(1L);
        AdministrativeUnitsDTO administrativeUnitsDTO2 = new AdministrativeUnitsDTO();
        assertThat(administrativeUnitsDTO1).isNotEqualTo(administrativeUnitsDTO2);
        administrativeUnitsDTO2.setId(administrativeUnitsDTO1.getId());
        assertThat(administrativeUnitsDTO1).isEqualTo(administrativeUnitsDTO2);
        administrativeUnitsDTO2.setId(2L);
        assertThat(administrativeUnitsDTO1).isNotEqualTo(administrativeUnitsDTO2);
        administrativeUnitsDTO1.setId(null);
        assertThat(administrativeUnitsDTO1).isNotEqualTo(administrativeUnitsDTO2);
    }
}
