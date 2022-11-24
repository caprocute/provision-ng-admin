package com.metasohi.provision.repository;

import com.metasohi.provision.domain.SysOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SysOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysOrganizationRepository extends JpaRepository<SysOrganization, Long> {}
