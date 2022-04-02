package com.wcreators.ersstrategy.dto.stochastic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StochasticRequestDTO {
  private Double value;
  private Double prevKPoint;
  private Integer period;
  private List<Double> lastPoints;
}
