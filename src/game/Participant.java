package game;


import javafx.scene.paint.Color;

public class Participant implements Comparable<Participant> {
    double p;
    double s = 0;
    double c;
    double c5 = -1;
    final double m = 3.0;
    final Color color = new Color(Math.random() , Math.random() , Math.random() , 0.89);
    static double B = 0.0;

    public Color getColor() {
        return color;
    }

    void initC(double k) {
        c = 100 / ((100 / k) + m);
        if (c5 == -1)
            c5 = 100 / (50 + m);
    }

    void initP() {
        p = (s / B) * 100 * 2;
    }


    void initC(int id) {
        c = 100 / (40 + m) + (double) id / 3;
    }

    void initC5(boolean higher) {
        if (higher)
            c5 = 100 / (80 + m) - p / 100;
        else
            c5 = 100 / (50 + m) + p / 100;
    }

    void initC5() {
        if (p >= 50) {
            c5 = 100 / (50 + m) - p / 250;
        } else {
            c5 = 100 / (50 + m) - p / 100;
        }
    }

    void clearS() {
        s = 0;
    }

    void clearB() {
        B = 0;
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

    void round() {
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
                " c win: " + c +
                ", c next stage: " + c5 +
                '}';
    }

    public String toStringOne() {
        return "Participant{" +
                " c next win: " + c5 +
                '}';
    }

    public String toStringFull() {
        return "Participant{" +
                "p=" + p +
                ", s=" + s +
                ", c=" + c +
                ", c5=" + c5 +
                ", m=" + m +
                '}';
    }


}
