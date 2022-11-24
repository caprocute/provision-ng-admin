package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvincesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provinces.class);
        Provinces provinces1 = new Provinces();
        provinces1.setCode("id1");
        Provinces provinces2 = new Provinces();
        provinces2.setCode(provinces1.getCode());
        assertThat(provinces1).isEqualTo(provinces2);
        provinces2.setCode("id2");
        assertThat(provinces1).isNotEqualTo(provinces2);
        provinces1.setCode(null);
        assertThat(provinces1).isNotEqualTo(provinces2);
    }
}
