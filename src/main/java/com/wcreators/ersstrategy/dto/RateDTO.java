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
public class RateDTO {
    private String major;
    private String minor;
    private Double sell;
    private Double buy;
    private Date date;
}
