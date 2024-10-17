package com.kolos.resourceservice.data.repository;

import com.kolos.resourceservice.data.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
