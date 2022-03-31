package com.wcreators.ersstrategy.dto.rsi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsiResponseDTO {
  private Double value;
  private Double maOfU;
  private Double maOfD;
}
