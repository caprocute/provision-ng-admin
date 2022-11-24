package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeRegionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeRegionsDTO.class);
        AdministrativeRegionsDTO administrativeRegionsDTO1 = new AdministrativeRegionsDTO();
        administrativeRegionsDTO1.setId(1L);
        AdministrativeRegionsDTO administrativeRegionsDTO2 = new AdministrativeRegionsDTO();
        assertThat(administrativeRegionsDTO1).isNotEqualTo(administrativeRegionsDTO2);
        administrativeRegionsDTO2.setId(administrativeRegionsDTO1.getId());
        assertThat(administrativeRegionsDTO1).isEqualTo(administrativeRegionsDTO2);
        administrativeRegionsDTO2.setId(2L);
        assertThat(administrativeRegionsDTO1).isNotEqualTo(administrativeRegionsDTO2);
        administrativeRegionsDTO1.setId(null);
        assertThat(administrativeRegionsDTO1).isNotEqualTo(administrativeRegionsDTO2);
    }
}
