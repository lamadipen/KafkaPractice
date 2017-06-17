package org.dipen.org.dipen.customPartationer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dipen on 6/15/2017.
 */
public class CustomPartationProducer {

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

        producer.send(producerRecord);
        producer.close();

        System.out.println("SimpleProducer Completed.");

    }
}

class SensorPartitioner implements Partitioner{
    private String speedSensorName;

    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int sp = (int)Math.abs(numPartitions*0.3);
        int p=0;

        if ( (keyBytes == null) || (!(key instanceof String)) )
            throw new InvalidRecordException("All messages must have sensor name as key");
        if ( ((String)key).equals(speedSensorName) )
            p = Utils.toPositive(Utils.murmur2(valueBytes)) % sp;
        else
            p = Utils.toPositive(Utils.murmur2(keyBytes)) % (numPartitions-sp) + sp ;

        System.out.println("Key = " + (String)key + " Partition = " + p );
        return p;

    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {
        speedSensorName = configs.get("speed.sensor.name").toString();
    }
}
