import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] Stack;
    private int size = 0;
    private int index = 0;
    private int capacity = 0;

    public RandomizedQueue()
    {
        Stack = (Item[]) new Object[2];
        size = 0;
        index = 0;
        capacity = 2;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        if(index == capacity)
        {
            Stack = resize(size + 1 > capacity ? capacity * 2 : capacity);
            if (size + 1 > capacity)
                capacity*=2;
        }
        Stack[index++] = item;
        size++;
    }

    public Item dequeue()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        int removalIndex = StdRandom.uniformInt(0, index);
        swap(removalIndex, index-1);
        Item item = Stack[--index];
        Stack[index] = null;
        size--;
        if (size < capacity / 4 && size!= 0)
        {
            Stack = resize(capacity / 2);
            capacity/=2;
        }
        return item;
    }
    
    public Item sample()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        while(true)
        {
            Item item;
            if((item = Stack[StdRandom.uniformInt(0, index!=capacity?index+1:index)]) != null)
                 return item;
        }
    }
    
    private Item[] resize(int newSize)
    {
        Item[] newStack = (Item[]) new Object[newSize];
        int newIndex = 0;
        if(index == Stack.length)
            index = Stack.length - 1;
        for (int i = 0; i <= index; i++)
            if(Stack[i] != null)
                newStack[newIndex++] = Stack[i];
        index = newIndex;
        return newStack;
    }
    
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }
    
    private void swap(int a, int b)
    {
        Item temp = Stack[a];
        Stack[a] = Stack[b];
        Stack[b] = temp;
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private Item[] array;
        private int arrayindex;
        
        ArrayIterator()
        {
            array = (Item[]) new Object[size];
            for(int i = 0; i < array.length; i++)
                array[i] = dequeue();
            for(int i = 0; i < array.length; i++)
                enqueue(array[i]);
            index = 0;
        }
        
        public boolean hasNext()
        {
            return (arrayindex < array.length);
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if(arrayindex == array.length)
                throw new NoSuchElementException();
            return array[arrayindex++];
        }
    }
    
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> RQ = new RandomizedQueue<Integer>();
        System.out.println("Initiated new RandomizedQueue");
        System.out.println("Empty = "+RQ.isEmpty());
        System.out.println("Size = "+RQ.size());
        System.out.println();
        System.out.println("Enqueueing numbers of dice");
        for(int i = 1; i <= 6; i++)
            RQ.enqueue(i);
        System.out.println("Empty = "+RQ.isEmpty());
        System.out.println("Size = "+RQ.size());
        System.out.println();
        System.out.println("Dice roll by dequeueing then putting back, ten times");
        for(int i = 1; i <= 10; i++)
        {
            int roll = RQ.dequeue();
            System.out.println(roll);
            RQ.enqueue(roll);
        }
        System.out.println();
        System.out.println("Dice roll by simply sampling, ten times:");
        for(int i = 1; i <= 10; i++)
            System.out.println(RQ.sample());
        System.out.println();
        System.out.println("Iterator test:");
        for (Integer s : RQ)
          System.out.print(s+" ");
        System.out.println("\nTesting complete.");
    }
}
