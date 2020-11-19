package com.okestro.openapidemo.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServiceResult")
public class BusApiResultDto {

    @XmlElement(name = "msgHeader")
    private BusApiHeaderDto msgHeader;

    @XmlElement(name = "msgBody")
    private MsgBody msgBody;

    public static class MsgBody {
        @XmlElement(name = "itemList")
        public ArrayList<BusApiBodyDto> busApiBodyDtos;
    }

}
