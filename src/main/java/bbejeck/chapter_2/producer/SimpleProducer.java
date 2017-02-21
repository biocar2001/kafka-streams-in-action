package bbejeck.chapter_2.producer;

import bbejeck.chapter_2.partitioner.PurchaseKeyPartitioner;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * User: Bill Bejeck
 * Date: 11/20/16
 * Time: 7:36 PM
 */
public class SimpleProducer {



    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "1");
        properties.put("retries", "3");
        properties.put("compression.type", "snappy");
        properties.put("partitioner.class", PurchaseKeyPartitioner.class.getName());


        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String>  record = new ProducerRecord<>("some-topic", "key", "value");

        Callback callback = (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }
        };

        Future<RecordMetadata> sendFuture = producer.send(record, callback);

    }


}