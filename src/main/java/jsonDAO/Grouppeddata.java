package jsonDAO;


import java.util.List;

public class Grouppeddata {
    public Grouppeddata(List<Pricequantity> buyLevels, List<Pricequantity> sellLevels) {
        this.buyLevels = buyLevels;
        this.sellLevels = sellLevels;
    }

    private List<Pricequantity> buyLevels;
    private List<Pricequantity> sellLevels;

    public List<Pricequantity> getBuyLevels() {
        return buyLevels;
    }

    public void setBuyLevels(List<Pricequantity> buyLevels) {
        this.buyLevels = buyLevels;
    }

    public List<Pricequantity> getSellLevels() {
        return sellLevels;
    }

    public void setSellLevels(List<Pricequantity> sellLevels) {
        this.sellLevels = sellLevels;
    }
}
