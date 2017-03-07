import java.util.Random;
import java.util.StringJoiner;

/**
 * Created by jerome on 06/03/2017.
 */
public class Sensor {
    private int amount;
    private String measureunit;

    public Sensor(int amount, String measureunit){
        this.amount = amount;
        this.measureunit = measureunit;
    }

    public void decreaseAmountByRandom(){
        Random r = new Random();
        this.amount = amount - r.nextInt((this.amount/2 + 2));
        if(this.amount < 0 )
            this.amount = 0;
    }
    public String getMeasureunit(){
        return measureunit;
    }

    public int getAmount(){
        return this.amount;
    }
}
