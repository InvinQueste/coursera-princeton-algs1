import edu.princeton.cs.algs4.StdIn;

public class Permutation
{
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> RQ = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            RQ.enqueue(StdIn.readString());
        for(int i = 1; i <= k; i++)
            System.out.println(RQ.dequeue());
    }
}
