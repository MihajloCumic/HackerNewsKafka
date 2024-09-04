package producer;

import api.HackerNewsApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class.getSimpleName());
    private static final HackerNewsApi hackerNewsApi;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        try {
            hackerNewsApi = new HackerNewsApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        logger.info("Kafka producer");
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        String storyTopic = "stories";;
        String commentTopic = "comments";
        String jobTopic = "jobs";

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for(int i = 0; i < 100; i++){
            try {
                String res = hackerNewsApi.getItem();
                JsonNode jsonNode = mapper.readTree(res);
                String itemType = String.valueOf(jsonNode.get("type").asText());
                System.out.println(itemType);
                if(itemType == null || itemType.isBlank()) continue;
                if(itemType.equals("story")){
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(storyTopic, "storyKey", res);
                    producer.send(producerRecord);
                }
                if (itemType.equals("comment")){
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(commentTopic, "commentKey", res);
                    producer.send(producerRecord);
                }
                if (itemType.equals("job")){
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(jobTopic, "jobKey", res);
                    producer.send(producerRecord);
                }
                System.out.println("here");
                producer.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            producer.flush();
        }

        producer.close();

    }
}
