package com.wcreators.ersstrategy.dto.rsi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsiRequestDTO {
    private Double value;
    private Double prev;
    private Double prevD;
    private Double prevU;
    private Integer period;
}
