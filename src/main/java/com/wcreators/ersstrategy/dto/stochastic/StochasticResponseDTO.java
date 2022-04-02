package com.wcreators.ersstrategy.dto.stochastic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StochasticResponseDTO {
  private Double pointK;
  private Double pointD;
  private List<Double> lastPoints;
}
