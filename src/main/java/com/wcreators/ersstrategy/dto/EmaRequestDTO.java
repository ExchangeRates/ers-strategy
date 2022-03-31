package com.wcreators.ersstrategy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmaRequestDTO {
  private Double prev;
  private Double value;
  private Integer period;
}
