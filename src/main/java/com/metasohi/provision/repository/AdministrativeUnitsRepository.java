package com.metasohi.provision.repository;

import com.metasohi.provision.domain.AdministrativeUnits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdministrativeUnits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrativeUnitsRepository extends JpaRepository<AdministrativeUnits, Long> {}
