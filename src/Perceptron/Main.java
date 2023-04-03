package Perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static List<Vector> allVectors = new ArrayList<>();
    static List<Vector> allTestVectors = new ArrayList<>();
    static double lerningRate = 0.01;

    public static void main(String[] args) throws FileNotFoundException {

        fromFile("data/perceptron.data", allVectors);
        double[] libras = new double[allVectors.get(0).getNumbers().size()];
        fillLibras(libras);
        double bias = Math.random();
        int limit = 10_000;

        while (limit > 0) {
            for (int i = 0; i < allVectors.size(); i++) {
                double tmp = calcRatio(allVectors.get(i).getNumbers(), libras);
                int output = tmp < bias ? 0 : 1;
                if (output != allVectors.get(i).getExpected()) {
                    libras = newLibras(libras, allVectors.get(i), output);
                    bias = newBias(bias, allVectors.get(i), output);
                }
                limit--;
            }
        }

        Scanner console = new Scanner(System.in);
        System.out.println("Wybierz ktore dane testow: \n\t 1. Jezeli chcesz zczytac z plik\n\t 2. Jezeli chcesz wpisac vector recznie (max wymiar " + allVectors.get(1).getNumbers().size() +")");
        int repeat = 0;
        if(console.nextLine().equals("1")){
            fromFile("data/perceptron.test.data", allTestVectors);
            repeat = 1;
        } else {
            System.out.println("Wpisz wektor ktory chcesz sprawdzac ");
            stringToList(console.nextLine(), allTestVectors);
        }

        while (true) {
            double right = 0;
            for (int i = 0; i < allTestVectors.size(); i++) {
                double tmp = calcRatio(allTestVectors.get(i).getNumbers(), libras);
                int output = tmp < bias ? 0 : 1;
                boolean guess = output == allTestVectors.get(i).getExpected();
                System.out.println(allTestVectors.get(i).toString() + " " + guess);

                if (guess) {
                    right++;
                }
            }
            System.out.println("Percentege right " + right / allTestVectors.size() * 100 + "%");

            if(repeat == 1){
                break;
            } else {
                System.out.println("Chcesz jeszcze raz sprawdzic? 1-tak 2-nie");
                if(console.nextLine().equals("1")){
                    allTestVectors.clear();
                    System.out.println("Wpisz wektor ktory chcesz sprawdzac ");
                    stringToList(console.nextLine(), allTestVectors);
                } else {
                    break;
                }
            }
        }
    }

    private static void fromFile(String path, List<Vector> allVectors) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String[] str = scanner.nextLine().split(",");
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < str.length - 1; i++) {
                data.add(Double.parseDouble(str[i]));
            }
            allVectors.add(new Vector(data, str[str.length - 1]));
        }
    }

    private static double calcRatio(List<Double> vector, double[] libras) {

        double tmp = 0;

        for (int i = 0; i < vector.size(); i++) {
            tmp += vector.get(i) * libras[i];
        }
        return tmp;
    }

    private static void fillLibras(double[] libras) {
        for (int i = 0; i < libras.length; i++) {
            libras[i] = Math.random();
        }
    }

    private static double[] newLibras(double[] libras, Vector vector, int expectedReceived) {

        double[] tmp = new double[libras.length];

        for (int i = 0; i < libras.length; i++) {
            int abc = vector.getExpected();
            libras[i] = libras[i] + lerningRate * (abc - expectedReceived) * vector.getNumbers().get(i);
        }
        return libras;
    }

    private static double newBias(double bias, Vector vector, int expectedReceived) {

        bias = bias - lerningRate * (vector.getExpected() - expectedReceived);
        return bias;
    }

    private static void stringToList(String str, List<Vector> list){

        String[] tmp = str.split(",");
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < tmp.length - 1; i++) {
            data.add(Double.parseDouble(tmp[i]));
        }
        list.add(new Vector(data,tmp[tmp.length-1]));
    }
}
