import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
public final class BruteCollinearPoints
{
    private final Point[] points;
    private LineSegment[] segments;
    private int numberOfSegments;
    private Stack<LineSegment> segmentstack;
    
    public BruteCollinearPoints(final Point[] formalpoints)
    {
        if(formalpoints == null)
            throw new IllegalArgumentException("Null array as argument");
        for(int i = 0; i < formalpoints.length; i++)
            if(formalpoints[i] == null)
                throw new IllegalArgumentException("Null point in argument");
        for(int i = 0; i < formalpoints.length-1; i++)
            for(int j = i+1; j < formalpoints.length; j++)
                if(formalpoints[i].slopeTo(formalpoints[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("Repeated point");
        points = formalpoints;
        segmentstack = new Stack<LineSegment>();
        for(int i = 0; i < points.length-3; i++)
            for(int j = i+1; j < points.length-2; j++)
                for(int k = j+1; k < points.length-1; k++)
                    for(int l = k+1; l < points.length; l++)
                        verifyLine(points[i], points[j], points[k], points[l]);
        numberOfSegments = segmentstack.size();
        segments = new LineSegment[numberOfSegments];
        int i = 0;
        while(!segmentstack.isEmpty())
        {
            segments[i++] = segmentstack.pop();
        }
    }
    
    public int numberOfSegments()
    {
        final int output = numberOfSegments;
        return output;
    }
    
    public LineSegment[] segments()
    {
        return segments.clone();
    }
    
    private void verifyLine(Point i, Point j, Point k, Point l)
    {
        if(!(i.slopeTo(j) == i.slopeTo(k) && i.slopeTo(j) == i.slopeTo(l)))
            return;
        Point[] linepoints = {i, j, k, l};
        Arrays.sort(linepoints);
        segmentstack.push(new LineSegment(linepoints[0], linepoints[3]));
    }
    
}

