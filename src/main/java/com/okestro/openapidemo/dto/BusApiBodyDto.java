package com.okestro.openapidemo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.sql.Timestamp;
import java.text.DateFormat;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "test_busapi_master")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "itemList")
public class BusApiBodyDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid", insertable = false, updatable = false)
    @XmlTransient
    private Long oid = 0L;

    @Column(name = "bus_route_id")
    @XmlTransient
    private String busRouteId;
//
//    @Column(name = "bus_route_nm")
//    @XmlTransient
//    private String busRouteNm;

    @Column(name = "sect_ord")
    @XmlElement(name = "sectOrd")
    private Integer sectOrd;         //구간순번

    @Column(name = "full_sect_dist")
    @XmlElement(name = "fullSectDist")
    private Float fullSectDist; 	//정류소간 거리

    @Column(name = "sect_dist")
    @XmlElement(name = "sectDist")
    private Float sectDist; 	    //구간옵셋거리 (Km)

    @Column(name = "rt_dist")
    @XmlElement(name = "rtDist")
    private Float rtDist;   	    //노선옵셋거리 (Km)

    @Column(name = "stop_flag")
    @XmlElement(name = "stopFlag")
    private Boolean stopFlag; 	    //정류소도착여부 (0:운행중, 1:도착)

    @Column(name = "section_id")
    @XmlElement(name = "sectionId")
    private String sectionId;	    //구간ID

    @Column(name = "data_tm")
    @XmlElement(name = "dataTm")
    private String dataTm;	    //제공시간

    @Column(name = "gps_x")
    @XmlElement(name = "gpsX")
    private Float gpsX;	            //맵매칭X좌표 (WGS84)

    @Column(name = "gps_y")
    @XmlElement(name = "gpsY")
    private Float gpsY;	            //맵매칭Y좌표 (WGS84)

    @Column(name = "veh_id")
    @XmlElement(name = "vehId")
    private String vehId;    	    //버스 ID

    @Column(name = "plain_no")
    @XmlElement(name = "plainNo")
    private String plainNo;  	    //차량번호

    @Column(name = "bus_type")
    @XmlElement(name = "busType")
    private Integer busType;  	    //차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)

    @Column(name = "last_st_tm")
    @XmlElement(name = "lastStTm")
    private Integer lastStTm; 	    //종점도착소요시간

    @Column(name = "next_st_tm")
    @XmlElement(name = "nextStTm")
    private Integer nextStTm; 	    //다음정류소도착소요시간

    @Column(name = "isrun_yn")
    @XmlElement(name = "isrunyn")
    private Boolean isrunyn;  	    //해당차량 운행여부 (0: 운행종료, 1: 운행)

    @Column(name = "trnstn_id")
    @XmlElement(name = "trnstnid")
    private String trnstnid; 	    //회차지 정류소ID

    @Column(name = "is_last_yn")
    @XmlElement(name = "islastyn")
    private Boolean islastyn; 	    //막차여부 (0 : 막차아님, 1: 막차)

    @Column(name = "is_full_flag")
    @XmlElement(name = "isFullFlag")
    private Boolean isFullFlag;	    //만차여부 (0 : 만차아님, 1: 차)

    @Column(name = "pos_x")
    @XmlElement(name = "posX")
    private Float posX;	            //맵매칭X좌표 (GRS80)

    @Column(name = "pos_y")
    @XmlElement(name = "posY")
    private Float posY;	            //맵매칭Y좌표 (GRS80)

    @Column(name = "last_stn_id")
    @XmlElement(name = "lastStnId")
    private String lastStnId;	    //최종정류장 ID

    @Column(name = "congetion")
    @XmlElement(name = "congetion")
    private Integer congetion;	    //혼잡도(0: 없음, 3: 여유, 4: 보통, 5: 혼잡, 6: 매우혼잡)

    @Column(name = "next_st_id")
    @XmlElement(name = "nextStId")
    private String nextStId;	    //다음정류소아이디

//    @Column(name = "reg_tm", columnDefinition = "Timestamp default now()")
//    @XmlTransient
//    private Timestamp regTm;

    // kafka sender 구성 시 직렬
//    public static class SerializeImpl implements Serializer<GetBusPosByRtidVO> {
//
//    }
}
