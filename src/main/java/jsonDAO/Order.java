package jsonDAO;

public class Order {
    private String type;
    private int orderId;
    private String productId;
    private String side;
    private int price;
    private int quantity;

    public Order(String type, int orderId, String productId, String side, int price, int quantity) {
        this.type = type;
        this.orderId = orderId;
        this.productId = productId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getPrice() {return price;    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "type='" + type + '\'' +
                ", orderId=" + orderId +
                ", productId='" + productId + '\'' +
                ", side='" + side + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
