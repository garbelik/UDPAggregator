package processor;


public class SearchOrderInfo {

    public SearchOrderInfo(String prodId,String side, int price, int quantity) {
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.prodId = prodId;
    }
    String prodId;
    String side;
    int price;
    int quantity;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
