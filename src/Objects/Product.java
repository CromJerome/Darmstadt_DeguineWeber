package Objects;

import java.util.Random;

/**
 * Created by jerome on 07/03/2017.
 */
public class Product {
    private String name;
    private int cost;
    private String measureunit;


    public Product(String name, int cost, String measureunit) {
        this.name = name;
        this.cost = cost;
        this.measureunit = measureunit;
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


    public String getMeasureunit() {
        return measureunit;
    }

    public void setMeasureunit(String measureunit) {
        this.measureunit = measureunit;
    }



}
