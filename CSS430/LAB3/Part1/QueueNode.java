//QueueNode.java
//Brandan Haertel

import java.util.Vector;

public class QueueNode
{
    private Vector queue;
    private final int FIRST_POSITION = 0;
    
    public QueueNode ()
    {
        queue = new Vector();
    }
    
    public synchronized int sleep()
    {
        while (this.queue.size() == 0)
        {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        return (int) queue.remove(FIRST_POSITION);
    }
    
    public synchronized void wakeup(int tid)
    {
        queue.add(tid);
        notify();
    }
}