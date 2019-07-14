package org.dipen.customSerilizer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by dipen on 6/16/2017.
 */
public class SupplierConsumerPropertyFile {
    public static void main(String[] args) throws Exception{

        String topicName = "SupplierTopic";
        String groupName = "SupplierTopicGroup";

        Properties props = new Properties();
        InputStream inputStream = null;
        KafkaConsumer<String, Supplier> consumer = null;
        consumer.subscribe(Arrays.asList(topicName));

        inputStream = new FileInputStream("SupplierConsumer.properties");
        props.load(inputStream);

        while (true){
            ConsumerRecords<String, Supplier> records = consumer.poll(100);
            for (ConsumerRecord<String, Supplier> record : records){
                System.out.println("Supplier id= " + String.valueOf(record.value().getID()) + " Supplier  Name = " + record.value().getName() + " Supplier Start Date = " + record.value().getStartDate().toString());
            }
        }

    }
}
