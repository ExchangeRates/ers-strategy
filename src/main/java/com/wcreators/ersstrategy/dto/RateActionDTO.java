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
public class RateActionDTO {
    private String major;
    private String minor;
    private String action;
    private Date created;
    private String strategy;
    private double rate;
}
