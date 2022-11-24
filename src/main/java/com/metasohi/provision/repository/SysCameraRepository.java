package com.metasohi.provision.repository;

import com.metasohi.provision.domain.SysCamera;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SysCamera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysCameraRepository extends JpaRepository<SysCamera, Long> {}
