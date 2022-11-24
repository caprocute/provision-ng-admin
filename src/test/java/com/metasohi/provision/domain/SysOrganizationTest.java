package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysOrganization.class);
        SysOrganization sysOrganization1 = new SysOrganization();
        sysOrganization1.setId(1L);
        SysOrganization sysOrganization2 = new SysOrganization();
        sysOrganization2.setId(sysOrganization1.getId());
        assertThat(sysOrganization1).isEqualTo(sysOrganization2);
        sysOrganization2.setId(2L);
        assertThat(sysOrganization1).isNotEqualTo(sysOrganization2);
        sysOrganization1.setId(null);
        assertThat(sysOrganization1).isNotEqualTo(sysOrganization2);
    }
}
