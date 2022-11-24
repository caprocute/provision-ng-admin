package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysAreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysArea.class);
        SysArea sysArea1 = new SysArea();
        sysArea1.setId(1L);
        SysArea sysArea2 = new SysArea();
        sysArea2.setId(sysArea1.getId());
        assertThat(sysArea1).isEqualTo(sysArea2);
        sysArea2.setId(2L);
        assertThat(sysArea1).isNotEqualTo(sysArea2);
        sysArea1.setId(null);
        assertThat(sysArea1).isNotEqualTo(sysArea2);
    }
}
