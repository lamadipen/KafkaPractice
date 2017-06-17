package org.dipen.customSerilizer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Created by dipen on 6/16/2017.
 */
public class SupplierSerializer implements Serializer<Supplier> {
    private String encoding = "UTF8";

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public byte[] serialize(String topic, Supplier supplier) {
        int sizeOfName;
        int sizeOfDate;
        byte[] serializedName;
        byte[] serializedDate;



        try {
            if (supplier == null)
                return null;

            serializedName = supplier.getName().getBytes(encoding);
            sizeOfName = serializedName.length;
            serializedDate = supplier.getStartDate().toString().getBytes(encoding);
            sizeOfDate = serializedDate.length;

            ByteBuffer buffer = ByteBuffer.allocate(4+4+sizeOfName+4+sizeOfDate);

            buffer.putInt(supplier.getID());
            buffer.putInt(sizeOfName);
            buffer.put(serializedName);
            buffer.putInt(sizeOfDate);
            buffer.put(serializedDate);


            return buffer.array();

        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("Error when serializing Supplier to byte[]");
        }

    }

    public void close() {

    }
}
