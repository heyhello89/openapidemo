package com.okestro.openapidemo.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "ServiceResult")
public class JaxbDto {
    @XmlElement(name = "msgHeader")
    private MsgHeader msgHeader;

    @Data
    @XmlRootElement(name = "msgHeader")
    private static class MsgHeader {
        @XmlElement(name ="headerCd")
        private String headerCd;

        @XmlElement(name ="headerMsg")
        private String headerMsg;

        @XmlElement(name ="itemCount")
        private String itemCount;
    }

    @XmlElement(name = "msgBody")
    private MsgBody msgBody;

    @Data
    @XmlRootElement(name = "msgBody")
    private static class MsgBody {

        @XmlElement(name = "items")
        private BasisItems basisItems;

        @XmlElement(name = "numOfRows")
        private Integer numOfRows;

        @XmlElement(name = "pageNo")
        private Integer pageNo;

        @XmlElement(name = "totalCount")
        private Integer totalCount;
    }

    @Data
    @XmlRootElement(name = "items")
    private static class BasisItems {

        @XmlElement(name = "item")
        private List<BusResultDto> basis;

    }

}
