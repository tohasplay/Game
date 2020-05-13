package game;

public class Participant implements Comparable<Participant> {
    double p;
    double s = 0;
    double c;
    double c5 = -1;
    final double m = 3.0;
    static double B = 0.0;

    void initC(double k) {
        c = 100 / ((100 / k) + m);
        if (c5 == -1)
            c5 = 100 / (50 + m);
    }

    void initP() {
        p = (s / B) * 100;
        s = 0;
    }

    void initC() {
        c = 100 / (p + m);
    }

    void initC5(boolean higher) {
        if (higher)
            c5 = 100 / (80 + m) - p / 100;
        else
            c5 = 100 / (50 + m) + p / 100;
    }

    void clearB() {
        B = 0;
    }

    public double getS() {
        return s;
    }

    public double getC() {
        return c;
    }

    public double getC5() {
        return c5;
    }

    public void addS(double stake) {
        s += stake;
        B += stake;
    }

    void round(){
        c = Math.round(c * 100.0) / 100.0;
        c5 = Math.round(c5 * 100.0) / 100.0;
    }

    @Override
    public int compareTo(Participant o) {
        return (int) (o.s - this.s);
    }

    @Override
    public String toString() {
        return "Participant{" +
                ", c win: " + c +
                ", c next stage: " + c5 +
                '}';
    }
}
