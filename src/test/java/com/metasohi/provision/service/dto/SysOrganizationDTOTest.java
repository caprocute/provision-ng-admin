package com.metasohi.provision.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysOrganizationDTO.class);
        SysOrganizationDTO sysOrganizationDTO1 = new SysOrganizationDTO();
        sysOrganizationDTO1.setId(1L);
        SysOrganizationDTO sysOrganizationDTO2 = new SysOrganizationDTO();
        assertThat(sysOrganizationDTO1).isNotEqualTo(sysOrganizationDTO2);
        sysOrganizationDTO2.setId(sysOrganizationDTO1.getId());
        assertThat(sysOrganizationDTO1).isEqualTo(sysOrganizationDTO2);
        sysOrganizationDTO2.setId(2L);
        assertThat(sysOrganizationDTO1).isNotEqualTo(sysOrganizationDTO2);
        sysOrganizationDTO1.setId(null);
        assertThat(sysOrganizationDTO1).isNotEqualTo(sysOrganizationDTO2);
    }
}
