package tools;

import java.util.ArrayList;

public class Staker<T extends Number, F extends Number> {
    ArrayList<T> t = new ArrayList<>();
    ArrayList<F> f = new ArrayList<>();

    public void put(T stake, F coeff){
        t.add(stake);
        f.add(coeff);
    }

    public double getWin(){
        double out = 0;
        for (int i = 0; i < t.size(); i++) {
            out += t.get(i).doubleValue() * f.get(i).doubleValue();
        }
        return out;
    }

    public void clear() {
        t.clear();
        f.clear();
    }
}
