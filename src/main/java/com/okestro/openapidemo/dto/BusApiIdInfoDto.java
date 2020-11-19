package com.okestro.openapidemo.dto;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "test_busapi_id_info")
public class BusApiIdInfoDto {
    @Id
    @Column(name = "oid")
    private Long oid;

    @Column(name = "bus_route_id")
    private String busRouteId;

    @Column(name = "bus_route_nm")
    private String busRouteNm;
}
