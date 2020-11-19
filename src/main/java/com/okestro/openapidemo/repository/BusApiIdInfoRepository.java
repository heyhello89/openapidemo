package com.okestro.openapidemo.repository;

import com.okestro.openapidemo.dto.BusApiIdInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusApiIdInfoRepository extends JpaRepository<BusApiIdInfoDto, Long> {

    List findAll();

    @Query(value = "select bus_route_id from test_busapi_id_info", nativeQuery = true)
    List<String> findBusRouteId();

}
