package processor;

import jsonDAO.Order;

import java.util.*;

public class OrderProcessor {

    private TreeMap<String,Products> aggregatedData;
    private Map<Integer,SearchOrderInfo> mappedById;

    public OrderProcessor() {
        mappedById = new HashMap<Integer,SearchOrderInfo>();
        aggregatedData= new TreeMap<String,Products>();

    }

    public synchronized TreeMap<String, Products> getAggregatedData() {
        return aggregatedData;
    }

    public synchronized void startProcessing(List<Order> orders){
        for (Order order: orders ){
            process(order);
    }

}


    private synchronized void process(Order order){
            switch (order.getType()){
                case "addOrder":
                    addOrder(order);
                    break;

                case "changeOrder":
                        changeOrder(order);
                        break;

                case "deleteOrder":
                    deleteOrder(order.getOrderId());
                    break;
            }

    }

    private synchronized void addOrder(Order order) {
        Products productsByName = null;
        TreeMap <Integer,Integer> forBuy ;
        TreeMap <Integer,Integer> forSell;

        int totalQnt=0;
        int currentQnt=0;

        //сущетсвует ли продукт с таким названием
        if (aggregatedData.containsKey(order.getProductId())) {
            productsByName = aggregatedData.get(order.getProductId());
            forBuy = productsByName.getForBuy();
            forSell = productsByName.getForSell();
        }

        else {
            forSell = new TreeMap<Integer, Integer>();
            forBuy = new TreeMap<Integer, Integer>(Collections.reverseOrder());
            productsByName = new Products(forSell, forBuy);
        }

        switch (order.getSide()){
            case "buy":
                if (productsByName.getForBuy().containsKey(order.getPrice())){
                    currentQnt = productsByName.getForBuy().get(order.getPrice());
                    totalQnt = currentQnt + order.getQuantity();
                    productsByName.getForBuy().put(order.getPrice(),totalQnt);
                }else{
                    totalQnt = order.getQuantity();
                    productsByName.getForBuy().put(order.getPrice(),totalQnt);
                }

                aggregatedData.put(order.getProductId(),productsByName);
                break;

            case "sell":
                if (productsByName.getForSell().containsKey(order.getPrice())){
                    currentQnt = productsByName.getForSell().get(order.getPrice());
                    totalQnt = currentQnt + order.getQuantity();

                    productsByName.getForSell().put(order.getPrice(),totalQnt);
                }else{
                    totalQnt = order.getQuantity();
                    productsByName.getForSell().put(order.getPrice(),totalQnt);
                }

                aggregatedData.put(order.getProductId(),productsByName);
                break;
        }


        SearchOrderInfo searchInfo = new SearchOrderInfo(order.getProductId(),order.getSide(),order.getPrice(),order.getQuantity());
        mappedById.put(order.getOrderId(),searchInfo);

    }


    private synchronized void changeOrder(Order order){
        int totalQnt = 0;
        SearchOrderInfo searchData = mappedById.get(order.getOrderId());

        Products currentPrd = aggregatedData.get(searchData.getProdId());

        switch (searchData.getSide()){
            case "buy":
                totalQnt =  currentPrd.getForBuy().get(searchData.getPrice());
                totalQnt = order.getQuantity();
                if (totalQnt <=0){
                    //delete price category if no more products exist
                    currentPrd.getForBuy().remove(searchData.getPrice());
                }
                else{
                    currentPrd.getForBuy().put(searchData.getPrice(),totalQnt);
                }
                break;
            case "sell":
                totalQnt =  currentPrd.getForSell().get(searchData.getPrice());
                totalQnt = order.getQuantity();
                if (totalQnt <=0){
                    //delete price category if no more products exist
                    currentPrd.getForSell().remove(searchData.getPrice());
                }
                else{
                    currentPrd.getForSell().put(searchData.getPrice(),totalQnt);
                }
                break;
        }

    }


    private synchronized void deleteOrder(int orderId){
        int totalQnt = 0;
        SearchOrderInfo searchData = mappedById.get(orderId);
        Products currentPrd = aggregatedData.get(searchData.getProdId());

         switch (searchData.getSide()){
             case "buy":
                 totalQnt =  currentPrd.getForBuy().get(searchData.getPrice());
                 totalQnt = totalQnt - searchData.getQuantity();
                 if (totalQnt <=0){
                     //delete price category if no more products exist
                     currentPrd.getForBuy().remove(searchData.getPrice());
                 }
                 else{
                     currentPrd.getForBuy().put(searchData.getPrice(),totalQnt);
                 }
                 break;
             case "sell":
                 totalQnt =  currentPrd.getForSell().get(searchData.getPrice());
                 totalQnt = totalQnt - searchData.getQuantity();
                 if (totalQnt <=0){
                     //delete price category if no more products exist
                     currentPrd.getForSell().remove(searchData.getPrice());
                 }
                 else{
                     currentPrd.getForSell().put(searchData.getPrice(),totalQnt);
                 }
                 break;
         }

         //delete product category if content is empty
         if((currentPrd.getForBuy().size()==0)&(currentPrd.getForSell().size()==0)){
             aggregatedData.remove(searchData.getProdId());
         }

    }

    @Override
    public String toString() {
        for (Map.Entry<String, Products> entry : aggregatedData.entrySet()) {
            System.out.println("ProductId : " + entry.getKey());

            System.out.println("buyLevels : ");
            for (Map.Entry<Integer, Integer> prodsb : entry.getValue().getForBuy().entrySet()) {
                System.out.println("price " + prodsb.getKey() + " quantity: " + prodsb.getValue());
            }
            System.out.println("sellLevels : ");
            for (Map.Entry<Integer, Integer> prodss : entry.getValue().getForSell().entrySet()) {
                System.out.println("price " + prodss.getKey() + " quantity: " + prodss.getValue());
            }
        }

        return "";
    }
}
