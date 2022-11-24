package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysOrganizationAreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysOrganizationArea.class);
        SysOrganizationArea sysOrganizationArea1 = new SysOrganizationArea();
        sysOrganizationArea1.setId(1L);
        SysOrganizationArea sysOrganizationArea2 = new SysOrganizationArea();
        sysOrganizationArea2.setId(sysOrganizationArea1.getId());
        assertThat(sysOrganizationArea1).isEqualTo(sysOrganizationArea2);
        sysOrganizationArea2.setId(2L);
        assertThat(sysOrganizationArea1).isNotEqualTo(sysOrganizationArea2);
        sysOrganizationArea1.setId(null);
        assertThat(sysOrganizationArea1).isNotEqualTo(sysOrganizationArea2);
    }
}
