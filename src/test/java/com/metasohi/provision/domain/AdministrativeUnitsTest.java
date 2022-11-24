package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeUnitsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeUnits.class);
        AdministrativeUnits administrativeUnits1 = new AdministrativeUnits();
        administrativeUnits1.setId(1L);
        AdministrativeUnits administrativeUnits2 = new AdministrativeUnits();
        administrativeUnits2.setId(administrativeUnits1.getId());
        assertThat(administrativeUnits1).isEqualTo(administrativeUnits2);
        administrativeUnits2.setId(2L);
        assertThat(administrativeUnits1).isNotEqualTo(administrativeUnits2);
        administrativeUnits1.setId(null);
        assertThat(administrativeUnits1).isNotEqualTo(administrativeUnits2);
    }
}
