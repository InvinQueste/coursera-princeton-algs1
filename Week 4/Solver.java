import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Solver
{
    private boolean isSolvable;
    private Node solnNode;
    private class Node implements Comparable<Object>
    {
        Node prev;
        Board current;
        int moves;
        int Priority;

        Node(Node fprev, Board fb, int fm)
        {
            prev = fprev;
            current = fb;
            moves = fm;
            Priority = current.manhattan() + moves;
        }

        public int compareTo(Object y)
        {
            if (!(y instanceof Node))
                return -1;
            Node that = (Node) y;
            return this.Priority - that.Priority;
        }
    }

    public Solver(Board initial)
    {
        if(initial == null)
            throw new IllegalArgumentException();
        MinPQ<Node> OGPQ = new MinPQ<Node>();
        OGPQ.insert(new Node(null, initial, 0));
        MinPQ<Node> KOPQ = new MinPQ<Node>();
        KOPQ.insert(new Node(null, initial.twin(), 0));
        Lockstep:
        while(true)
        {
            Node OGPQB = OGPQ.delMin();
            if(OGPQB.current.isGoal())
            {
                isSolvable = true;
                solnNode = OGPQB;
                return;
            }
            for (Board e : OGPQB.current.neighbors())
            {
                if (OGPQB.prev != null)
                    if(!(e.equals(OGPQB.prev.current)))
                        OGPQ.insert(new Node(OGPQB, e, OGPQB.moves + 1));
                if (OGPQB.prev == null)
                    OGPQ.insert(new Node(OGPQB, e, OGPQB.moves + 1));
            }

            Node KOPQB = KOPQ.delMin();
            if(KOPQB.current.isGoal())
            {
                isSolvable = false;
                solnNode = null;
                return;
            }
            for (Board e : KOPQB.current.neighbors())
            {
                if (KOPQB.prev != null)
                    if(!(e.equals(KOPQB.prev.current)))
                        KOPQ.insert(new Node(KOPQB, e, KOPQB.moves + 1));
                if (KOPQB.prev == null)
                    KOPQ.insert(new Node(KOPQB, e, KOPQB.moves + 1));
            }

        }

    }

    public boolean isSolvable(){return isSolvable;}

    public int moves()
    {
        if(!isSolvable)
            return -1;
        return solnNode.moves;
    }

    public Iterable<Board> solution()
    {
        if(!isSolvable)
            return null;
        return new SolutionIterable(solnNode);
    }

    private class SolutionIterable implements Iterable<Board>
    {
        Stack<Board> solnStack;

        SolutionIterable(Node solnNode)
        {
            solnStack = new Stack<Board>();
            Node traverserNode = solnNode;
            do
            {
                solnStack.push(traverserNode.current);
                traverserNode = traverserNode.prev;
            }while(traverserNode!=null);
        }

        public Iterator<Board> iterator()
        {
            return solnStack.iterator();
        }
    }
}