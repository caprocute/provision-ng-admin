package com.metasohi.provision.repository;

import com.metasohi.provision.domain.SysOrganizationArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SysOrganizationArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysOrganizationAreaRepository extends JpaRepository<SysOrganizationArea, Long> {}
