package KNN;

import java.util.List;

public class Point {
    List<Double> list;
    String type;
    double distance;

    public Point(List<Double> list, String type) {
        this.list = list;
        this.type = type;
    }

    public List<Double> getList() {
        return list;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "KNN2.Point{" +
                "list=" + list +
                ", type='" + type + '\'' +
                '}';
    }
}
