package KNN;

public class Distance {
    Point original;
    double data;

    public Distance(Point original, double data) {
        this.original = original;
        this.data = data;
    }

    public Point getOriginal() {
        return original;
    }

    public void setOriginal(Point original) {
        this.original = original;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
