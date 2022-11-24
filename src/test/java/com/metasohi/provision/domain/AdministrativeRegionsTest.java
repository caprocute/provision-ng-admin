package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeRegionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeRegions.class);
        AdministrativeRegions administrativeRegions1 = new AdministrativeRegions();
        administrativeRegions1.setId(1L);
        AdministrativeRegions administrativeRegions2 = new AdministrativeRegions();
        administrativeRegions2.setId(administrativeRegions1.getId());
        assertThat(administrativeRegions1).isEqualTo(administrativeRegions2);
        administrativeRegions2.setId(2L);
        assertThat(administrativeRegions1).isNotEqualTo(administrativeRegions2);
        administrativeRegions1.setId(null);
        assertThat(administrativeRegions1).isNotEqualTo(administrativeRegions2);
    }
}
