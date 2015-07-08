//SyncQueue.java
//Brandan Haertel

public class SyncQueue
{
    private QueueNode[] queue;
    private int MAX;
    
    private final int ERROR = -1;
    private final int DEFAULT_TID = 0;
    private final int DEFAULT_SIZE = 10;
    
    // constructors
    public SyncQueue( ) { NewQueue( DEFAULT_SIZE ); }
    public SyncQueue( int size ) { NewQueue( size ); }
    
    public int enqueueAndSleep( int condition )
    {
        return (condition < queue.length && condition >= 0)? 
            queue[condition].sleep():ERROR;
    }
    
    public void dequeueAndWakeup( int condition )
    {
        dequeueAndWakeup(condition, DEFAULT_TID);
    }
    
    public void dequeueAndWakeup( int condition, int tid )
    {
        if (condition < queue.length && condition >= 0)
        {
            queue[condition].wakeup(tid);
        }
    }
    
    private void NewQueue(int size)
    {
        MAX = size;
        queue = new QueueNode[MAX];
        for (int i = 0; i < MAX; i++)
        {
            queue[i] = new QueueNode();
        }
    }
}