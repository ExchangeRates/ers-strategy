package com.wcreators.ersstrategy.kafka.producers;

public interface ProducerService<T> {
    String topicName(T model);

    void produce(T model);
}
