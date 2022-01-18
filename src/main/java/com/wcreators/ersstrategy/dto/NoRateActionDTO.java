package com.wcreators.ersstrategy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoRateActionDTO {
    private String major;
    private String minor;
    private String stopAction;
    private Date created;
    private String strategy;
    private double rate;
}
