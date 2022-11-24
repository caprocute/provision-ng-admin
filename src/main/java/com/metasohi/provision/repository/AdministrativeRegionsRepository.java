package com.metasohi.provision.repository;

import com.metasohi.provision.domain.AdministrativeRegions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdministrativeRegions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrativeRegionsRepository extends JpaRepository<AdministrativeRegions, Long> {}
