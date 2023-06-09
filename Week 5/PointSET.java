import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;
public class PointSET
{
    private TreeSet<Point2D> Sett;
    
    public PointSET()
    {
        Sett = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty()
    {
        return Sett.isEmpty();
    }
    
    public int size()
    {
        return Sett.size();
    }
    
    public void insert(Point2D point)
    {
        if(point == null)
            throw new IllegalArgumentException();
        Sett.add(point);
    }
    
    public boolean contains(Point2D point)
    {
        if(point == null)
            throw new IllegalArgumentException();
        return Sett.contains(point);
    }
    
    public void draw()
    {
        for(Point2D p : Sett)
            StdDraw.point(p.x(), p.y());
    }
    
    public Iterable<Point2D> range(RectHV range)
    {
        if(range == null)
            throw new IllegalArgumentException();
        Stack<Point2D> rangeStack = new Stack<Point2D>();
        for(Point2D p : Sett)
            if(range.contains(p))
                rangeStack.push(p);
        return rangeStack;
    }
    
    public Point2D nearest(Point2D point)
    {
        if(point == null)
            throw new IllegalArgumentException();
        if(isEmpty())
            return null;
        Point2D nearest = Sett.first();
        for(Point2D p : Sett)
            if(p.distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
                nearest = p;
        return nearest;
    }
}