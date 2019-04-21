package util;

import model.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

/**
 * Class that generates random unique points
 */
public class PointGenerator {

    /**
     * Generate list of random unique points from limits
     * @param   numGeneratedPoints number of points to generate
     * @param   X_MIN minimum x value
     * @param   X_MAX maximum x value
     * @param   Y_MIN minimum y value
     * @param   Y_MAX maximum y value
     * @return  list of unique Point objects
     */
    public static List<Point> generate(int numGeneratedPoints,
        final int X_MIN, final int X_MAX, final int Y_MIN, final int Y_MAX) {

        // Use set to avoid duplicate points
        Set<Point> mPoints = new HashSet<>();

        int deltaX = X_MAX - X_MIN;
        int deltaY = Y_MAX - Y_MIN;

        while (mPoints.size() < numGeneratedPoints) {

            // Create random point within bounds
            int x = (int) Math.floor((deltaX + 1) * Math.random()) + X_MIN;
            int y = (int) Math.floor((deltaY + 1) * Math.random()) + Y_MIN;

            mPoints.add(new Point(x, y, mPoints.size() + 1));
        }

        // Convert set to list
        List<Point> mPointList = new ArrayList<>(mPoints);

        // Sort by index
        mPointList.sort(Comparator.comparing(Point::getIndex));
        return mPointList;
    }

    /**
     * Generate list of random unique points from array of boundary
     * @param  numGeneratedPoints number of points to generate
     * @param  BOUND array of bounds [X_MIN, X_MAX, Y_MIN, Y_MAX]
     * @return list of unique Point objects
     */
    public static List<Point> generate(int numGeneratedPoints, final int[] BOUND) {
        return generate(numGeneratedPoints, BOUND[0], BOUND[1], BOUND[2], BOUND[3]);
    }
}
