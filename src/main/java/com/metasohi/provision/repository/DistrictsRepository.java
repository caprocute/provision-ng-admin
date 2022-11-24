package com.metasohi.provision.repository;

import com.metasohi.provision.domain.Districts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Districts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictsRepository extends JpaRepository<Districts, String> {}
