package com.okestro.openapidemo.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msgHeader")
public class BusApiHeaderDto {
    @XmlElement(name ="headerCd")
    private String headerCd;

    @XmlElement(name ="headerMsg")
    private String headerMsg;

    @XmlElement(name ="itemCount")
    private String itemCount;
}
