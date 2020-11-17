package com.okestro.openapidemo.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "item")
public class BusResultDto {

    @XmlElement(name = "sectOrd")
    private String sectOrd;         //구간순번

    @XmlElement(name = "fullSectDist")
    private String fullSectDist; 	//정류소간 거리

    @XmlElement(name = "sectDist")
    private String sectDist; 	    //구간옵셋거리 (Km)

    @XmlElement(name = "rtDist")
    private String rtDist;   	    //노선옵셋거리 (Km)

    @XmlElement(name = "stopFlag")
    private Integer stopFlag; 	    //정류소도착여부 (0:운행중, 1:도착)

    @XmlElement(name = "sectionId")
    private String sectionId;	    //구간ID

    @XmlElement(name = "dataTm")
    private String dataTm;	        //제공시간

    @XmlElement(name = "gpsX")
    private String gpsX;	        //맵매칭X좌표 (WGS84)

    @XmlElement(name = "gpsY")
    private String gpsY;	        //맵매칭Y좌표 (WGS84)

    @XmlElement(name = "vehId")
    private String vehId;    	    //버스 ID

    @XmlElement(name = "plainNo")
    private String plainNo;  	    //차량번호

    @XmlElement(name = "busType")
    private Integer busType;  	    //차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)

    @XmlElement(name = "lastStTm")
    private String lastStTm; 	    //종점도착소요시간

    @XmlElement(name = "nextStTm")
    private String nextStTm; 	    //다음정류소도착소요시간

    @XmlElement(name = "isrunyn")
    private String isrunyn;  	    //해당차량 운행여부 (0: 운행종료, 1: 운행)

    @XmlElement(name = "trnstnid")
    private String trnstnid; 	    //회차지 정류소ID

    @XmlElement(name = "islastyn")
    private Integer islastyn; 	    //막차여부 (0 : 막차아님, 1: 막차)

    @XmlElement(name = "isFullFlag")
    private Integer isFullFlag;	    //만차여부 (0 : 만차아님, 1: 차)

    @XmlElement(name = "posX")
    private String posX;	        //맵매칭X좌표 (GRS80)

    @XmlElement(name = "posY")
    private String posY;	        //맵매칭Y좌표 (GRS80)

    @XmlElement(name = "lastStnId")
    private String lastStnId;	    //최종정류장 ID

    @XmlElement(name = "congetion")
    private Integer congetion;	    //혼잡도(0: 없음, 3: 여유, 4: 보통, 5: 혼잡, 6: 매우혼잡)

    @XmlElement(name = "nextStId")
    private String nextStId;	    //다음정류소아이디

    // kafka sender 구성 시 직렬
//    public static class SerializeImpl implements Serializer<GetBusPosByRtidVO> {
//
//    }
}
