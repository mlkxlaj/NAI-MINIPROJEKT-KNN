package KNN;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    static HashSet<String> typesHash = new HashSet<>();
    static int k;
    static double trueTimes = 0;
    static double dlugosc = 0;
    public static void main(String[] args) throws IOException {

        int droga = 0;
        boolean whileBool = true;
        Scanner console = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("data/iris.data"));
        List<Point> points = new ArrayList<>();
        List<Point> testingPoints = new ArrayList<>();
        List<String> string = new ArrayList<>();
        fileToList(scanner, points);
        System.out.println("Podaj wartosc K");
        k = Integer.parseInt(console.nextLine());
        System.out.println("Wybierz 1 jezeli chcesz wpisac recznie dane lub 2 jezeli dane beda pobierane z pliku");
        String output = console.nextLine();


        while (whileBool) {
            if (output.equals("2") && droga == 0) {
                scanner = new Scanner(new File("data/iris.test.data"));
                fileToList(scanner, testingPoints);
                droga = 1;
            } else {
                System.out.println("Wpisuj linie danych po przecinku");
                output = console.nextLine();
                string.add(output);
                listToPointsList(testingPoints, string);

            }

            String[] typesString = new String[typesHash.size()];
            int[] typesCount;
            typesHash.toArray(typesString);

            for (int i = 0; i < testingPoints.size(); i++) {

                typesCount = new int[k];
                List<Distance> allDistances = new ArrayList<>();
                Distance[] topDistances = new Distance[k];
                for (int j = 0; j < points.size(); j++) {
                    allDistances.add(new Distance(points.get(j), calcDistance(points.get(j), testingPoints.get(i))));
                }
                bubbleSort(allDistances);
                for (int j = 0; j < topDistances.length; j++) {
                    topDistances[j] = allDistances.get(j);
                }
                for (int j = 0; j < topDistances.length; j++) {
                    for (int k = 0; k < typesString.length; k++) {
                        if (topDistances[j].getOriginal().getType().equals(typesString[k])) {
                            typesCount[k] += 1;
                        }
                    }
                }
                findDominatingCat(typesString, typesCount, testingPoints.get(i));
            }

            System.out.println((double) (trueTimes / dlugosc));



            if (droga == 1) {
                whileBool = false;
            } else {
                System.out.println("chcesz powtorzyc akcje 1 - tak 2 - nie ?");
                if (console.nextLine().equals("2")) {
                    whileBool = false;
                } else {
                    testingPoints.clear();
                    string.clear();

                }
            }
        }
    }

    private static void fileToList(Scanner scanner, List<Point> points) {
        for (int i = 0; scanner.hasNextLine(); i++) {
            String[] splited = scanner.nextLine().split(",");
            typesHash.add(splited[splited.length - 1]);
            List<Double> data = new ArrayList<>();
            for (int j = 0; j < splited.length - 1; j++) {
                data.add(Double.parseDouble(splited[j]));
            }
            points.add(new Point(data, splited[splited.length - 1]));
        }
    }

    private static void listToPointsList(List<Point> points, List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            String[] splited = strings.get(i).split(",");
            List<Double> data = new ArrayList<>();
            for (int j = 0; j < splited.length - 1; j++) {
                data.add(Double.parseDouble(splited[j]));
            }
            points.add(new Point(data, splited[splited.length - 1]));
        }
    }

    public static double calcDistance(Point point, Point testingPoint) {
        double tosqrt = 0;

        if (point.getList().size() == testingPoint.getList().size()) {
            for (int i = 0; i < point.getList().size(); i++) {
                tosqrt += Math.pow(point.getList().get(i) - testingPoint.getList().get(i), 2);
            }
            tosqrt = Math.sqrt(tosqrt);
        }
        return tosqrt;
    }

    public static void bubbleSort(List<Distance> distances) {
        int n = distances.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (distances.get(j).getData() > distances.get(j + 1).getData()) {
                    Distance tmp = distances.get(j);
                    distances.set(j, distances.get(j + 1));
                    distances.set(j + 1, tmp);
                }
    }

    public static void findDominatingCat(String[] strings, int[] ints, Point point) {
        int max = 0;
        String value = "";
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] > max) {
                max = ints[i];
                value = strings[i];
            }
        }
        dlugosc++;
        if (point.getType().equals(value)) {
            System.out.println(Arrays.toString(point.getList().toArray()) + " " + point.getType() + " " + "true");
            trueTimes++;
        } else {
            System.out.println(Arrays.toString(point.getList().toArray()) + " " + point.getType() + " " + "false");
        }
    }
}