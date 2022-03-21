package com.wcreators.ersstrategy.feign;

import com.wcreators.ersstrategy.dto.rsi.RsiRequestDTO;
import com.wcreators.ersstrategy.dto.rsi.RsiResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "rsi-feign-client", url = "http://localhost:8081")
public interface RsiFeignClient {

  @PostMapping("/calculate")
  RsiResponseDTO calculate(@RequestBody RsiRequestDTO request);
}
