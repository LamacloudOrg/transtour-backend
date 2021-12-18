package com.transtour.backend.travel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveTaxesDTO implements Serializable {

    private String orderNumber;
    private String whitingTime;
    private String toll;
    private String parkingAmount;
    private String taxForReturn;
}
