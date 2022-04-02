package com.wcreators.ersstrategy.feign;

import com.wcreators.ersstrategy.dto.stochastic.StochasticRequestDTO;
import com.wcreators.ersstrategy.dto.stochastic.StochasticResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stochastic-feign-cleint", url = "http://localhost:8082")
public interface StochasticFeignClient {

  @PostMapping("/calculate")
  StochasticResponseDTO calculate(@RequestBody StochasticRequestDTO requeset);
}

