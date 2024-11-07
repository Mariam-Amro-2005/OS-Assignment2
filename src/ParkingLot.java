import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private static final int MAX_SPOTS = 4;
    private static final Semaphore parkingSpots = new Semaphore(MAX_SPOTS, true);
    private static final AtomicInteger currentCars = new AtomicInteger(0);
    private static final AtomicInteger totalCarsServed = new AtomicInteger(0);

    public static void enter(Car car) {

    }

    public static void exit(Car car) {

    }

    public static synchronized void reportStatus() {

    }
}
