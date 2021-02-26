package Home_work_2;

public class CalculateDistance {

    public static void main(String[] args) {

        Point p1 = new Point(1,1);
        Point p2 = new Point(4,5);

        print(distance1(1,1,4,5), "simple function");
        print(distance(p1, p2), "function and class Point");
        print(p1.distance(p2), "Method");
    }

    public static double distance1(double cordX1, double cordY1,double cordX2, double cordY2) {
        return Math.sqrt(Math.pow((cordX1 - cordX2), 2) + Math.pow((cordY1 - cordY2), 2));
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    public static void print(Double printConsoleInfo, String method) {
        System.out.println("Calculation using "+ method + ": distance is equal to " + printConsoleInfo);
    }
}
