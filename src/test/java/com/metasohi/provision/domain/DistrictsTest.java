package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Districts.class);
        Districts districts1 = new Districts();
        districts1.setCode("id1");
        Districts districts2 = new Districts();
        districts2.setCode(districts1.getCode());
        assertThat(districts1).isEqualTo(districts2);
        districts2.setCode("id2");
        assertThat(districts1).isNotEqualTo(districts2);
        districts1.setCode(null);
        assertThat(districts1).isNotEqualTo(districts2);
    }
}
