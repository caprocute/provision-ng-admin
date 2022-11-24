package com.metasohi.provision.repository;

import com.metasohi.provision.domain.SysArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SysArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysAreaRepository extends JpaRepository<SysArea, Long> {}
