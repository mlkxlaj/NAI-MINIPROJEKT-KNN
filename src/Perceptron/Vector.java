package Perceptron;

import java.util.List;
import java.util.Objects;

public class Vector {

    List<Double> numbers;
    String type;
    int expected;

    public List<Double> getNumbers() {
        return numbers;
    }

    public int getExpected() {
        return expected;
    }

    public Vector(List<Double> numbers, String type) {
        this.numbers = numbers;
        this.type = type;

        if (Objects.equals(type, "Iris-versicolor")) {
            this.expected = 1;
        } else {
            this.expected = 0;
        }
    }

    @Override
    public String toString() {
        return
                numbers + type;
    }
}
