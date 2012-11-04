package com.kmrowiec.puzzlegame;

import java.util.Set;

import android.util.Log;

public class ThreadUtils {

	public static void showThreadNames() { 
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		for(Thread t : threadArray) { 
			Log.d("THREAD", "Thread: " + t.getName());
		}
	}
	
	public static void showThreads() { 
		
		// Walk up all the way to the root thread group. Taken from the 
		// same StackOverflow article as listThreads. 
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parent;
        while ((parent = rootGroup.getParent()) != null) {
            rootGroup = parent;
        }

        listThreads(rootGroup, "");
	}
	
	/** 
     * List all threads and recursively list all subgroup. This method is 
     * taken from the StackOverflow article at: 
     * http://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
     * 
     * @param group
     * @param indent
     */
    private static void listThreads(ThreadGroup group, String indent) {
        System.out.println(indent + "Group[" + group.getName() + 
                        ":" + group.getClass()+"]");
        int nt = group.activeCount();
        Thread[] threads = new Thread[nt*2 + 10]; //nt is not accurate
        nt = group.enumerate(threads, false);

        // List every thread in the group
        for (int i=0; i<nt; i++) {
            Thread t = threads[i];
            
            Log.d("THREAD", indent + "  Thread[" + t.getName() 
                        + ":" + t.getClass() + "]");
        }

        // Recursively list all subgroups
        int ng = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[ng*2 + 10];
        ng = group.enumerate(groups, false);

        for (int i=0; i<ng; i++) {
            listThreads(groups[i], indent + "  ");
        }
    }
}