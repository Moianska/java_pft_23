package Home_work_2;

public class Point {

    public double x;
    public double y;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double distance(Point that) {
        return Math.sqrt(Math.pow((this.x - that.x), 2) + Math.pow((this.y - that.y), 2));
    }
}
