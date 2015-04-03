package me.gbwl.mp.util ;

public interface Sync {
	void acquire() throws InterruptedException; 

	boolean attempt(long msecs) throws InterruptedException;

	void release();	
	
	 boolean isUsed() ;
}