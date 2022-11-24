package com.metasohi.provision.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.metasohi.provision.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SysCameraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysCamera.class);
        SysCamera sysCamera1 = new SysCamera();
        sysCamera1.setId(1L);
        SysCamera sysCamera2 = new SysCamera();
        sysCamera2.setId(sysCamera1.getId());
        assertThat(sysCamera1).isEqualTo(sysCamera2);
        sysCamera2.setId(2L);
        assertThat(sysCamera1).isNotEqualTo(sysCamera2);
        sysCamera1.setId(null);
        assertThat(sysCamera1).isNotEqualTo(sysCamera2);
    }
}
