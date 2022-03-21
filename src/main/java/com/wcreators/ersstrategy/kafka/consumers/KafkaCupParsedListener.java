package com.wcreators.ersstrategy.kafka.consumers;

import com.wcreators.ersstrategy.dto.CupRateDTO;
import com.wcreators.ersstrategy.dto.NoRateActionDTO;
import com.wcreators.ersstrategy.dto.RateActionDTO;
import com.wcreators.ersstrategy.kafka.producers.ProducerService;
import com.wcreators.ersstrategy.mapper.CupRateMapper;
import com.wcreators.ersstrategy.model.CupRatePoint;
import com.wcreators.ersstrategy.model.StrategyResponse;
import com.wcreators.ersstrategy.service.strategy.ProcessRatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaCupParsedListener {

    private final ProducerService<RateActionDTO> rateActionProducerService;
    private final ProducerService<NoRateActionDTO> noRateActionProducerService;
    private final ProcessRatesService processRatesService;
    private final CupRateMapper mapper;

    @KafkaListener(topics = "parsed.CUP-EUR-USD", clientIdPrefix = "parsed-topic")
    public void receive(CupRateDTO dto) {
        CupRatePoint cupRatePoint = mapper.dtoToModel(dto);
        String name = cupRatePoint.getMajor() + "/" + cupRatePoint.getMinor();
        List<StrategyResponse> strategyResponses = processRatesService.addRate(cupRatePoint);
        strategyResponses.forEach(strategyResponse -> {
            if (strategyResponse.isAction()) {
                log.info("Send action {}: {}", name, strategyResponse.getData());
                RateActionDTO action = RateActionDTO.builder()
                        .major(cupRatePoint.getMajor())
                        .minor(cupRatePoint.getMinor())
                        .created(new Date())
                        .action(strategyResponse.getData())
                        .rate(cupRatePoint.getClose())
                        .strategy(processRatesService.getStrategy().name())
                        .build();
                rateActionProducerService.produce(action);
            } else {
                log.info("Nothing actions for rate {} | {}", cupRatePoint, strategyResponse.getData());
                NoRateActionDTO noAction = NoRateActionDTO.builder()
                        .major(cupRatePoint.getMajor())
                        .minor(cupRatePoint.getMinor())
                        .created(new Date())
                        .stopAction(strategyResponse.getData())
                        .rate(cupRatePoint.getClose())
                        .strategy(processRatesService.getStrategy().name())
                        .build();
                noRateActionProducerService.produce(noAction);
            }
        });

    }
}
