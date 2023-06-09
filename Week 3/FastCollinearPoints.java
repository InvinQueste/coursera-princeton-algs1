import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
public class FastCollinearPoints
{
    private Point[] points;
    private LineSegment[] segments;
    private int numberOfSegments;
    private Stack<LineSegment> segmentstack;

    public FastCollinearPoints(Point[] formalpoints)
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
        points = formalpoints.clone();
        segmentstack = new Stack<LineSegment>();
        for(int lcv = 0; lcv <= points.length-3; lcv++)
        {    
            Arrays.sort(points, lcv+1, points.length, points[lcv].slopeOrder());
            for(int i = lcv+1; i < points.length; i++)
            {
                int k;
                for(k = i; k < points.length-1 && points[k+1].slopeTo(points[lcv]) == points[k].slopeTo(points[lcv]); k++);
                if (k - i >= 2)
                {
                    if(alreadyexists(lcv, i))
                    {
                        i = k;
                        continue;
                    }
                    LineSegment lineseg = createLineSegment(i, k, lcv);
                    segmentstack.push(lineseg);
                }
                i = k;
            }  
        }
        numberOfSegments = segmentstack.size();
        segments = new LineSegment[numberOfSegments];
        int i = 0;
        while(!segmentstack.isEmpty())
        {
            segments[i++] = segmentstack.pop();
        }
    }

    private void swap(int a, int b)
    {
        Point temp = points[a];
        points[a] = points[b];
        points[b] = temp;
    }

    private LineSegment createLineSegment(int i, int k, int lcv)
    {
        Point[] linePoints = new Point[(k - i) + 2];
        linePoints[0] = points[lcv];
        for(int j = 1; j < linePoints.length; j++)
            linePoints[j] = points[i + j - 1];
        Arrays.sort(linePoints);
        return new LineSegment(linePoints[0], linePoints[linePoints.length - 1]);
    }
    
    private boolean alreadyexists(int lcv, int i)
    {
        for(int j = 0; j < lcv; j++)
            if(points[j].slopeTo(points[lcv]) == points[i].slopeTo(points[lcv]))
                return true;
        return false;
    }
    
    public int numberOfSegments()
    {
        int output = numberOfSegments;
        return output;
    }

    public LineSegment[] segments()
    {
        return segments.clone();
    }
    
}
