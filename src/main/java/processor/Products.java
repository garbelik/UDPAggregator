package processor;


import java.util.LinkedList;
import java.util.TreeMap;

public class Products {
    public Products(TreeMap<Integer, Integer> forSell, TreeMap<Integer, Integer> forBuy) {
        this.forSell = forSell;
        this.forBuy = forBuy;
    }

    private TreeMap <Integer,Integer> forSell;
    private TreeMap<Integer,Integer> forBuy;

    public TreeMap<Integer, Integer> getForSell() {
        return forSell;
    }

    public void setForSell(TreeMap<Integer,Integer> forSell) {
        this.forSell = forSell;
    }

    public TreeMap<Integer,Integer> getForBuy() {
        return forBuy;
    }

    public void setForBuy(TreeMap<Integer,Integer> forBuy) {
        this.forBuy = forBuy;
    }
}
