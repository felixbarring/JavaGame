package sound;

import java.util.List;
import java.util.ArrayList;

class ThreadPool{

	private boolean isAlive;
	private List<PooledThread> threads;

	public ThreadPool(int numThreads) {
		isAlive = true;
		threads = new ArrayList<PooledThread>();
		for (int i=0; i < numThreads; i++) {
			PooledThread t = new PooledThread();
			t.start();
			threads.add(t);
		}
	}

	public void addTask(Runnable task){
		if (!isAlive){
			throw new IllegalStateException();
		}
		if (task != null){
			for (PooledThread t : threads){
				if (!t.isBussy()){
					t.newTask(task);
					synchronized(t){
						t.notify();
					}
					break;
				}
			}
		}
	}

	// Not done
	public void join(){
		synchronized (this) {
			isAlive = false;
		}
	}

	private class PooledThread extends Thread {

		/*
		 *If the thread dosn't have any task, then it will wait until it get a new one.
		 */
		private final int SLEEPTIME = 100000;
		private boolean haveaTask = false;
		private Runnable task = null;

		public boolean isBussy(){
			return haveaTask;
		}

		public synchronized void newTask(Runnable t){
			task = t;
			haveaTask = true;
		}

		public void run(){
			while (isAlive){
				synchronized(this){
					if (haveaTask){
						try {
							task.run();
						}
						catch (Throwable t) {
						}
					}
					haveaTask = false;
					try {
						wait();
					} catch (InterruptedException e){
					}
				}
			}
		}
	}
}