package jsonDAO;


import processor.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OutMessage {

    public OutMessage(int outSequenceNumber, TreeMap<String, Products> aggregatedData) {
        this.outSequenceNumber = outSequenceNumber;
        this.aggregatedData= aggregatedData;
        this.construct();
    }

    private int outSequenceNumber;
    private List<ProductEntity> products = new ArrayList<>() ;
    private transient TreeMap<String, Products> aggregatedData;
    private transient ProductEntity entity ;


    private void construct() {

        for (Map.Entry<String, Products> entry : aggregatedData.entrySet()) {
            List<Pricequantity> buyLevels = new ArrayList<>();
            List<Pricequantity> sellLevels = new ArrayList<>();

            for (Map.Entry<Integer, Integer> prodsb : entry.getValue().getForBuy().entrySet()) {
               buyLevels.add( new Pricequantity(prodsb.getKey(),prodsb.getValue()));
            }
            for (Map.Entry<Integer, Integer> prodss : entry.getValue().getForSell().entrySet()) {
                sellLevels.add( new Pricequantity(prodss.getKey(),prodss.getValue()));
            }
            Grouppeddata grouppeddata = new Grouppeddata(buyLevels,sellLevels);
            entity= new ProductEntity(entry.getKey(), grouppeddata);
            products.add(entity);
        }
    }



    public int getOutSequenceNumber() {
        return outSequenceNumber;
    }

    public void setOutSequenceNumber(int outSequenceNumber) {
        this.outSequenceNumber = outSequenceNumber;
    }


}
