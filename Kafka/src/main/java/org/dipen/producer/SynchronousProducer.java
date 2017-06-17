package org.dipen.producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by dipen on 6/15/2017.
 */
public class SynchronousProducer {

    public static void main(String [] args)
    {
        String topicName = "SimpleProducerTopic";
        String key = "Key1";
        String value = "Value-1";

        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092,localhost:9093");
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(prop);

        ProducerRecord<String,String> producerRecord = new ProducerRecord<String, String>(topicName,key,value);

        try {
            RecordMetadata metadata = producer.send(producerRecord).get();

            System.out.println("Message is sent to Partition no " + metadata.partition() + " and offset " + metadata.offset());
            System.out.println("SynchronousProducer Completed with success.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("SynchronousProducer failed with an exception");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("SynchronousProducer failed with an exception");
        }finally {
            producer.close();
        }

    }



}
