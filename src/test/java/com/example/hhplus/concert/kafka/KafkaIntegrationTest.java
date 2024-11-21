package com.example.hhplus.concert.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.support.EventType;
import com.example.hhplus.concert.infra.kafka.KafkaProducer;
import com.example.hhplus.concert.interfaces.consumer.KafkaConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("카프카 통합 테스트")
class KafkaIntegrationTest {

  @Autowired
  private KafkaProducer producer;

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @Test
  void test() throws Exception {
    // given
    String topic = EventType.TEST_TOPIC;
    String message = "Hello Kafka";

    // when
    producer.publish(topic, message);
    Thread.sleep(4000);

    // then
    assertThat(kafkaConsumer.getMessage()).isEqualTo(message);
  }

}
