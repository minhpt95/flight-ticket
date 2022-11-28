package com.catdev.project.respository;

import com.catdev.project.entity.CronEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CronRepository extends JpaRepository<CronEntity,Long>, JpaSpecificationExecutor<CronEntity> {
    Optional<CronEntity> getCronEntityByCronCode(String cronCode);

    Optional<CronEntity> getCronEntityByCronName(String cronName);

    List<CronEntity> getAllByCronNameContainsIgnoreCase(String cronName);


}
