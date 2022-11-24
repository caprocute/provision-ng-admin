package com.metasohi.provision.repository;

import com.metasohi.provision.domain.Wards;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Wards entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WardsRepository extends JpaRepository<Wards, String> {}
