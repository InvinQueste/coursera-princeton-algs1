import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;
public class KdTree
{
    private static class Node
    {
        Node left;
        Node right;
        Point2D point;
        boolean vertical;
        
        Node(Point2D p, Node l, Node r, boolean v)
        {
            point = p;
            left = l;
            right = r;
            vertical = v;
        }
    }
    
    private static final RectHV Board = new RectHV(0, 0, 1, 1);
    private Node root;
    private int size;
    private Point2D closest;
    
    public KdTree()
    {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public int size()
    {
        return size;
    }
    
    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if(root == null)
        {
            root = new Node(p, null, null, false);
            size++;
        }            
        insert(null, true, root, false, p);
    }
    
    private void insert(Node parent, boolean leftpath, Node current, boolean v, Point2D p)
    {
        if(current == null)
        {
            if(leftpath)
                parent.left = new Node(p, null, null, v);
            else
                parent.right = new Node(p, null, null, v);
            size++;
            return;
        }
        assert (current.vertical == v);
        if(current.point.equals(p))
            return;
        if(!v)
        {
            if(p.x() < current.point.x())
                insert(current, true, current.left, true, p);
            else
                insert(current, false, current.right, true, p);
        }
        else
        {
            if(p.y() < current.point.y())
                insert(current, true, current.left, false, p);
            else
                insert(current, false, current.right, true, p);
        }
    }
    
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        return search(root, p) != null;
    }
    
    private Node search(Node current, Point2D p)
    {
        if(current == null)
            return null;
        if(current.point.equals(p))
            return current;
        if(current.vertical?(p.y()<current.point.y()):(p.x()<current.point.x()))
            return search(current.left, p);
        else
            return search(current.right, p);
    }
    
    public void draw(){}
    
    public Iterable<Point2D> range(RectHV range)
    {
        if (range == null)
            throw new IllegalArgumentException();
        Stack<Point2D> pointStack = new Stack<Point2D>();
        rangesearch(pointStack, root, range);
        return pointStack;
    }
    
    private void rangesearch(Stack<Point2D> pointStack, Node current, RectHV range)
    {
        if (current == null)
            return;
        if (range.contains(current.point))
            pointStack.push(current.point);
        if (range.intersects(leftrec(current)))
            rangesearch(pointStack, current.left, range);
        if (range.intersects(rightrec(current)))
            rangesearch(pointStack, current.right, range);
    }
    
    private RectHV leftrec(Node current)
    {
        if (current.vertical)
            return new RectHV(0, 0, 1, current.point.y());
        else
            return new RectHV(0, 0, current.point.x(), 1);
    }
    
    private RectHV rightrec(Node current)
    {
        if (current.vertical)
        
            return new RectHV(0, current.point.y(), 1, 1);
        else
            return new RectHV(current.point.x(), 0, 1, 1);
    }
    
    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;
        closest = root.point;
        nearestsearch(root, p);
        return closest;
    }
    
    private void nearestsearch(Node current, Point2D p)
    {
        if (current == null)
            return;
        if (current.point.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
            closest = current.point;
        if (leftrec(current).contains(p))
        {
            if(leftrec(current).distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                nearestsearch(current.left, p);
            if(rightrec(current).distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                nearestsearch(current.right, p);
        }
        else
        {
            if(rightrec(current).distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                nearestsearch(current.right, p);
            if(leftrec(current).distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                nearestsearch(current.left, p);
        }
    }
}