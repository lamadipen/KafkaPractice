package org.dipen.customSerilizer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by dipen on 6/16/2017.
 */
public class SupplierDeserializer implements Deserializer<Supplier> {
    private String encoding = "UTF8";
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public Supplier deserialize(String topic, byte[] data) {


        try {
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }

            ByteBuffer buffer = ByteBuffer.wrap(data);

            int id= buffer.getInt();

            int sizeOfName = buffer.getInt();
            byte[] nameBytes = new byte[sizeOfName];
            buffer.get(nameBytes);
            String deserializedName = new String(nameBytes,encoding);

            int sizeOfDate = buffer.getInt();
            byte[] dateBytes = new byte[sizeOfDate];
            buffer.get(dateBytes);
            String dateString = new String(dateBytes,encoding);

            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

            return new Supplier(id,deserializedName,df.parse(dateString));

        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("Error when deserializing byte[] to Supplier");
        } catch (ParseException e) {
            return  null;
        }

    }

    public void close() {

    }
}
