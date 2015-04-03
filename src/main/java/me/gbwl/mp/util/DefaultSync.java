package me.gbwl.mp.util ;

public class DefaultSync implements Sync {
	protected boolean inuse = false;
    public void acquire()
            throws RuntimeException
        {
            if(Thread.interrupted())
                throw new RuntimeException();
            synchronized(this)
            {
                try
                {
                    while(inuse) 
                        wait();
                    inuse = true;
                }
                catch(InterruptedException ex)
                {
                    notifyAll();
                    throw new RuntimeException(ex.getCause());
                }
            }
        }
	
	public synchronized void release() {
		inuse = false; 
		notifyAll();
	}
	
	public synchronized boolean isUsed() {
		return inuse;
	}
	
	public boolean attempt(long msecs) throws InterruptedException {
		if (Thread.interrupted()) throw new InterruptedException();
		synchronized(this) {
			if (!inuse) {
				inuse = true;
				return true;
			} else if (msecs <= 0) {
				return false;
			} else {
				long waitTime = msecs; 
				long start = System.currentTimeMillis();
				try {
					for (;;) {
						wait(waitTime); 
						if (!inuse) {
							inuse = true;
							return true;
						} else {
							waitTime = msecs - (System.currentTimeMillis() - start);
							if (waitTime <= 0) 
							return false;
						}
					}
				} catch (InterruptedException ex) {
					notifyAll();
					throw ex; 
				}
			}
		}
	}

}