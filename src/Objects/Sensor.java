package Objects;

import java.util.Random;
import java.util.StringJoiner;

/**
 * Created by jerome on 06/03/2017.
 */
public class Sensor {
    private Product product;
    private int amount;

    public Sensor(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void decreaseAmountByRandom(){
        Random r = new Random();
        this.amount = amount - r.nextInt((this.amount/2 + 2));
        if(this.amount < 0 )
            this.amount = 0;
    }

    public String getMeasureunit(){
        return this.product.getMeasureunit();
    }

    public int getAmount(){
        return this.amount;
    }



}
