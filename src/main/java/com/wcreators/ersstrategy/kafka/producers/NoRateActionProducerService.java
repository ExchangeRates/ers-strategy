package com.wcreators.ersstrategy.kafka.producers;

import com.wcreators.ersstrategy.dto.NoRateActionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoRateActionProducerService implements ProducerService<NoRateActionDTO> {

    private final KafkaTemplate<String, NoRateActionDTO> template;

    @Override
    public String topicName(NoRateActionDTO dto) {
        return String.format("noAction.%s-%s", dto.getMajor(), dto.getMinor());
    }

    @Override
    public void produce(NoRateActionDTO dto) {
        template.send(topicName(dto), dto);
    }
}
