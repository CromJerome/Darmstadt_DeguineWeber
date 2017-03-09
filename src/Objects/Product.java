package Objects;

import java.util.Random;

/**
 * Created by jerome on 07/03/2017.
 */
public class Product {
    private String name;
    private int cost;
    private int stock;


    public Product(String name, int cost, int stock) {
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void decreaseStock(int amount){
        this.stock = stock - amount;
    }

    public void decreaseStockByRandom(){
        Random r = new Random();
        this.stock = this.stock - (r.nextInt(this.stock/2) +2);
    }
}
