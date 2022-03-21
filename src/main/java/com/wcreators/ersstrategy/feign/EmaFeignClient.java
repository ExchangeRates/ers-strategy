package com.wcreators.ersstrategy.feign;

import com.wcreators.ersstrategy.dto.EmaRequestDTO;
import com.wcreators.ersstrategy.dto.EmaResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ema-feign-cleint", url = "http://localhost:8080")
public interface EmaFeignClient {

  @PostMapping("/calculate")
  EmaResponseDTO calculate(@RequestBody EmaRequestDTO request);
}
