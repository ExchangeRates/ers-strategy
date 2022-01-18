package com.wcreators.ersstrategy.kafka.producers;

import com.wcreators.ersstrategy.dto.RateActionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateActionProducerService implements ProducerService<RateActionDTO> {

    private final KafkaTemplate<String, RateActionDTO> template;

    @Override
    public String topicName(RateActionDTO dto) {
        return String.format("action.%s-%s", dto.getMajor(), dto.getMinor());
    }

    @Override
    public void produce(RateActionDTO dto) {
        template.send(topicName(dto), dto);
    }
}
