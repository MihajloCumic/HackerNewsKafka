package consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import writer.CsvWriter;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class CommentConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CommentConsumer.class.getSimpleName());
    private static final CsvWriter csvWriter = new CsvWriter("/home/cuma/Fakultet/veliki-podaci-ispit/KafkaServiceSuite/CommentConsumers/src/main/resources/comments.csv", new String[]{"by", "id", "parent", "text", "time"});

    public static void main(String[] args) throws JsonProcessingException {
        logger.info("Kafka comment consumer");
        String topic1 = "comments";
        String groupId = "comment-consumers";
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(List.of(topic1));

        while(true){
            logger.info("Polling...");
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String,String> record: consumerRecords){
               csvWriter.writeToCsv(record.value());
            }
        }
    }
}
