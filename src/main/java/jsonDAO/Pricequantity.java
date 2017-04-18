package jsonDAO;


public class Pricequantity {
    public Pricequantity(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    private int price;
    private int quantity;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
