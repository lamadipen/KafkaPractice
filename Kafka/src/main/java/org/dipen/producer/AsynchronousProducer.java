package org.dipen.producer;


import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Created by dipen on 6/15/2017.
 */
public class AsynchronousProducer {

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

        producer.send(producerRecord,new MyProducerCallBak());
        producer.close();

        System.out.println("AsynchronousProducer call completed");
    }

}

 class MyProducerCallBak implements Callback{

    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if(e !=null)
        {
            if (e != null)
                System.out.println("AsynchronousProducer failed with an exception");
            else
                System.out.println("AsynchronousProducer call Success:");
        }
    }
}
