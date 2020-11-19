package com.okestro.openapidemo.repository;

import com.okestro.openapidemo.dto.BusApiBodyDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusApiBodyRepository extends JpaRepository<BusApiBodyDto, Long> {

}
