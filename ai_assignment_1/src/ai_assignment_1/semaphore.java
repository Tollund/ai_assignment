package ai_assignment_1;

public class semaphore {
	 
    private int s = 0;
 
    public semaphore(int s0) {
        if (s0 >= 0) 
            s = s0;
        else
            throw new Error("Semaphore initialized to negative value: " + s0);
    }
 
    public synchronized void P() 
    throws InterruptedException {
        while (s == 0) wait();
        s--;
    }
 
    public synchronized void V() {
        s++;
        notify();
    }
 
    public String toString() { // Give semaphore value (for debugging only)
        return ""+s;
    }
 
}
