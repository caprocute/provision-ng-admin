package com.metasohi.provision.repository;

import com.metasohi.provision.domain.Provinces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Provinces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, String> {}
