package Home_work_2;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TwoPointsDistanceMeasurementTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(4,5);
        Assert.assertNotEquals(p1,p2);
        Assert.assertEquals(p1.distance(p2), 5);
    }
}
