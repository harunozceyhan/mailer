package com.ozceyhan.mailer.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozceyhan.mailer.model.Mail;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class KafkaIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaIntegrationTest.class);

    private static String TOPIC_NAME = "test-mail";

    private KafkaMessageListenerContainer<String, Mail> container;

    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);

    @Before
    public void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("mail", "false",
                embeddedKafka.getEmbeddedKafka());

        DefaultKafkaConsumerFactory<String, Mail> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        // Create embedded Kafka Consumer and add consumed records to queue
        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) record -> {
            LOGGER.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });

        // Start consumer
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        container.stop();
    }

    @Test
    public void itShouldSendMail() throws Exception {
        Mail mail = new Mail("harunozceyhan@gmail.com", "Subject", "Content", "");
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka()));
        Producer<String, Mail> producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(),
                new JsonSerializer<Mail>()).createProducer();
        // Send created mail object to kafka
        producer.send(new ProducerRecord<>(TOPIC_NAME, null, mail));
        producer.flush();

        // Check if consumed object equals to created mail object
        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);
        String json = new ObjectMapper().writeValueAsString(mail);
        assertThat(received.value()).isEqualTo(json);
        assertThat(received.key()).isEqualTo(null);
    }
}