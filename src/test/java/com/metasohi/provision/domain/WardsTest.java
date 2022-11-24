package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WardsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wards.class);
        Wards wards1 = new Wards();
        wards1.setCode("id1");
        Wards wards2 = new Wards();
        wards2.setCode(wards1.getCode());
        assertThat(wards1).isEqualTo(wards2);
        wards2.setCode("id2");
        assertThat(wards1).isNotEqualTo(wards2);
        wards1.setCode(null);
        assertThat(wards1).isNotEqualTo(wards2);
    }
}
