public class SimpleSemaphore {
    private int permits;  // Number of available permits
    private final boolean fair;  // Fairness flag to mimic Semaphore behavior

    public SimpleSemaphore(int permits, boolean fair) {
        this.permits = permits;
        this.fair = fair;  // The 'fair' parameter is unused in this simple implementation
    }

    // Try to acquire a permit without blocking
    public synchronized boolean tryAcquire() {
        if (permits > 0) {
            permits--;  // Take one permit if available
            return true;
        }
        return false;  // No permits available
    }

    // Acquire a permit, blocking until one is available
    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();  // Block until notified
        }
        permits--;  // Take one permit
    }

    // Release a permit and notify waiting threads
    public synchronized void release() {
        permits++;  // Add a permit back
        notify();  // Notify one waiting thread, if any
    }
}
