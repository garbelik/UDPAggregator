package jsonDAO;

public class ProductEntity {
    public ProductEntity(String productId, Grouppeddata data) {
        this.productId = productId;
        this.data = data;
    }

    private String productId;
    private Grouppeddata data;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Grouppeddata getData() {
        return data;
    }

    public void setData(Grouppeddata data) {
        this.data = data;
    }
}
