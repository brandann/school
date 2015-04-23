import java.util.*;

public class Scheduler extends Thread
{
    private Vector[] queue;
    private static final int Q_TOP = 0, Q_MID = 1, Q_BOT = 2, QUEUE_NUMBER = 2;
    private int timeSlice;
    private static final int DEFAULT_TIME_SLICE = 1000;
    private int[] queueTimes;

    // New data added to p161 
    private boolean[] tids; // Indicate which ids have been used
    private static final int DEFAULT_MAX_THREADS = 10000;

    // A new feature added to p161 
    // Allocate an ID array, each element indicating if that id has been used
    private int nextId = 0;
    private void initTid( int maxThreads ) 
    {
		tids = new boolean[maxThreads];
		for ( int i = 0; i < maxThreads; i++ )
		    tids[i] = false;
    }

    // A new feature added to p161 
    // Search an available thread ID and provide a new thread with this ID
    private int getNewTid( ) 
    {
		for ( int i = 0; i < tids.length; i++ ) 
		{
		    int tentative = ( nextId + i ) % tids.length;
		    if ( tids[tentative] == false ) 
		    {
				tids[tentative] = true;
				nextId = ( tentative + 1 ) % tids.length;
				return tentative;
		    }
		}
		return -1;
    }

    // A new feature added to p161 
    // Return the thread ID and set the corresponding tids element to be unused
    private boolean returnTid( int tid ) 
    {
		if ( tid >= 0 && tid < tids.length && tids[tid] == true ) 
		{
		    tids[tid] = false;
		    return true;
		}
		return false;
    }

    // A new feature added to p161 
    // Retrieve the current thread's TCB from the queue
    public TCB getMyTcb( ) 
    {
		Thread myThread = Thread.currentThread( ); // Get my thread object
		synchronized( queue ) {
		    for ( int i = 0; i < queue[Q_TOP].size( ); i++ ) 
		    {
				TCB tcb = ( TCB )queue[Q_TOP].elementAt( i );
				Thread thread = tcb.getThread( );
				if ( thread == myThread ) // if this is my TCB, return it
				    return tcb;
		    }
		}
		return null;
    }

    // A new feature added to p161 
    // Return the maximal number of threads to be spawned in the system
    public int getMaxThreads( ) 
    {
		return tids.length;
    }

    public Scheduler( ) 
    {
		timeSlice = DEFAULT_TIME_SLICE;
		queueTimes = new int[3];
		queueTimes[Q_TOP] = resetQueueTime(Q_TOP);
		queueTimes[Q_MID] = resetQueueTime(Q_MID);
		queueTimes[Q_BOT] = resetQueueTime(Q_BOT);
		queue = new Vector[3];
		for(int i = 0; i < 3; i++) 
		{
			queue[i] = new Vector(); 
		}
		initTid( DEFAULT_MAX_THREADS );
    }

    public Scheduler( int quantum ) 
    {
		timeSlice = quantum;
		queueTimes = new int[3];
		queueTimes[Q_TOP] = resetQueueTime(Q_TOP);
		queueTimes[Q_MID] = resetQueueTime(Q_MID);
		queueTimes[Q_BOT] = resetQueueTime(Q_BOT);
		queue = new Vector[3];
		for(int i = 0; i < 3; i++) 
		{ 
			queue[i] = new Vector(); 
		}
		initTid( DEFAULT_MAX_THREADS );
    }

    // A new feature added to p161 
    // A constructor to receive the max number of threads to be spawned
    public Scheduler( int quantum, int maxThreads ) 
    {
		timeSlice = quantum;
		queueTimes = new int[3];
		queueTimes[Q_TOP] = resetQueueTime(Q_TOP);
		queueTimes[Q_MID] = resetQueueTime(Q_MID);
		queueTimes[Q_BOT] = resetQueueTime(Q_BOT);
		queue = new Vector[3];
		for(int i = 0; i < 3; i++) 
		{ 
			queue[i] = new Vector(); 
		}
		initTid( maxThreads );
    }
    
    private void schedulerSleep(int time)
    {
    	try {
		    Thread.sleep( time );
		} catch ( InterruptedException e ) {}
    }

    // A modified addThread of p161 example
    public TCB addThread( Thread t) 
    {
		TCB parentTcb = getMyTcb( ); // get my TCB and find my TID
		int pid = ( parentTcb != null ) ? parentTcb.getTid( ) : -1;
		int tid = getNewTid( ); // get a new TID
		if ( tid == -1)
		    return null;
		TCB tcb = new TCB( t, tid, pid ); // create a new TCB
		queue[Q_TOP].add( tcb );
		return tcb;
    }

    // A new feature added to p161
    // Removing the TCB of a terminating thread
    public boolean deleteThread( ) 
    {
		TCB tcb = getMyTcb( ); 
		if ( tcb!= null )
		    return tcb.setTerminated( );
		else
		    return false;
    }

    public void sleepThread( int milliseconds ) 
    {
		try {
		    sleep( milliseconds );
		} catch ( InterruptedException e ) { }
    }
    
    private int resetQueueTime(int index)
    {
    	return (index == 0)? timeSlice/2:timeSlice*index;
    }
    
    // A modified run of p161
    public void run( ) 
    {
		Thread current = null;
		int currentQueue = -1;

		while ( true ) 
		{
		    try 
		    {
				if(!queue[0].isEmpty())
					currentQueue = 0;
				else if(!queue[1].isEmpty())
					currentQueue = 1;
				else if(!queue[2].isEmpty())
					currentQueue = 2;
				else
					continue;
					
				if(queue[currentQueue].isEmpty())
				{
					continue;
				}
				
				// current queue is now at the highest available
				TCB currentTCB = (TCB)queue[currentQueue].firstElement( );
				if ( currentTCB.getTerminated( ) == true ) 
				{
				    queue[currentQueue].remove(currentTCB);
				    returnTid( currentTCB.getTid( ) );
				    continue;
				}
				
				//--------------------------------------------------------------
				current = currentTCB.getThread( );
				if ( current != null ) 
				{
				    if ( current.isAlive( ) )
						current.resume();
				    else 
				    {
						// Spawn must be controlled by Scheduler
						// Scheduler must start a new thread
						current.start( ); 
				    }
				}
				
				while(true)
				{
					schedulerSleep( timeSlice/2 );
					queueTimes[currentQueue] -= timeSlice/2;
					// System.out.println("* * * Context Switch * * * ");
					if((queueTimes[currentQueue] <= 0) || (!queue[0].isEmpty() ) )
					{
						break;
					}
				}
				
	
				synchronized ( queue[currentQueue] )
				{
				    if ( current != null && current.isAlive( ) )
    					current.suspend( );
				    
				    if(queueTimes[currentQueue] <= 0)
				 	{
				    	// rotate this TCB to the end
					    if(currentQueue == 2)
					    {
					    	// if task is in lowest queue already then
					    	// move task to back of queue
					    	queue[currentQueue].remove( currentTCB );
					    	queue[2].add(currentTCB);
					    }
					    else
					    {
					    	// if lower queue is available then move task down
					    	// to that lower queue
					    	queue[currentQueue].remove( currentTCB );
					    	queue[currentQueue + 1].add(currentTCB);
					    }
					    
					    queueTimes[currentQueue] = resetQueueTime(currentQueue);
				    }
				}
			} catch ( NullPointerException e3 ) { };
		}
    }
}
