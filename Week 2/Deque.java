
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node first;
    private Node last;
    private int size;
        
    private class Node
    {
        Item item;
        Node prev;
        Node next;
    }
    
    public Deque()
    {
        first = new Node();
        last = new Node();
        first.next = last;
        last.prev = first;
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

    public void addFirst(Item item)
    {
        if(item == null)
            throw new IllegalArgumentException();
        if(first.item == null)
        {
            first.item = item;
        }
        else
        {
            Node newNode = new Node();
            newNode.item = item;
            newNode.next = first;
            newNode.prev = null;
            first = newNode;
            first.next.prev = first;
        }
        size++;
    }
    
    public void addLast(Item item)
    {
        if(item == null)
            throw new IllegalArgumentException();
        if(last.prev.item == null)
        {
            last.prev.item = item;
        }
        else
        {
            last.item = item;
            Node newNode = new Node();
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }
        
    public Item removeFirst()
    {
        if(first.item == null)
            throw new NoSuchElementException();
        Item item = first.item;
        if(first.next == last)
            first.item = null;
        else
            first = first.next;
        first.prev = null;
        size--;
        return item;
    }
    
    public Item removeLast()
    {
        if(last.prev.item == null)
            throw new NoSuchElementException();
        Item item = last.prev.item;
        if (last.prev == first)
            first.item = null;
        else
            last = last.prev;
        last.item = null;
        last.next = null;
        size--;
        return item;
    }
    
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {
            return current.item != null;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args)
    {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println("Empty = "+deque.isEmpty());
        System.out.println("Size = "+deque.size());
        System.out.println("Adding 4 to start");
        deque.addFirst(4);
        System.out.println("Adding 3 to start");
        deque.addFirst(3);
        System.out.println("Adding 5 to end");
        deque.addLast(5);
        System.out.println("Empty = "+deque.isEmpty());
        System.out.println("Size = "+deque.size());
        System.out.println("Using Iterator to display items");
        for (Integer s : deque)
          System.out.print(s+" ");  
        System.out.println("\nPopping all from last:");
        while(!deque.isEmpty())
            System.out.print(deque.removeLast()+" ");
        System.out.println("\nEmpty = "+deque.isEmpty());
        System.out.println("Size = "+deque.size());
        System.out.println("Testing Completed.");
    }
}