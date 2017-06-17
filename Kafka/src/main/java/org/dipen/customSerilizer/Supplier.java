package org.dipen.customSerilizer;

import java.util.Date;

/**
 * Created by dipen on 6/16/2017.
 */
public class Supplier {
    private int supplierId;
    private String supplierName;
    private Date supplierStartDate;

    public Supplier(int id, String name, Date dt){
        this.supplierId = id;
        this.supplierName = name;
        this.supplierStartDate = dt;
    }

    public int getID(){
        return supplierId;
    }

    public String getName(){
        return supplierName;
    }

    public Date getStartDate(){
        return supplierStartDate;
    }
}
